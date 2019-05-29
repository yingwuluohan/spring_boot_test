package com.unisound.iot.controller.websocket.socket_client;


import org.java_websocket.AbstractWebSocket;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.HandshakeImpl1Client;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public abstract class WebSocketClient extends AbstractWebSocket implements Runnable, WebSocket {
    protected URI uri;
    private WebSocketImpl engine;
    private Socket socket;
    private OutputStream ostream;
    private Proxy proxy;
    private Thread writeThread;
    private Draft draft;
    private Map<String, String> headers;
    private CountDownLatch connectLatch;
    private CountDownLatch closeLatch;
    private int connectTimeout;

    public WebSocketClient(URI serverUri) {
        this(serverUri, (Draft)(new Draft_6455()));
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft) {
        this(serverUri, protocolDraft, (Map)null, 0);
    }

    public WebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        this(serverUri, new Draft_6455(), httpHeaders);
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        this(serverUri, protocolDraft, httpHeaders, 0);
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        this.uri = null;
        this.engine = null;
        this.socket = null;
        this.proxy = Proxy.NO_PROXY;
        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        this.connectTimeout = 0;
        if (serverUri == null) {
            throw new IllegalArgumentException();
        } else if (protocolDraft == null) {
            throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
        } else {
            this.uri = serverUri;
            this.draft = protocolDraft;
            this.headers = httpHeaders;
            this.connectTimeout = connectTimeout;
            this.setTcpNoDelay(false);
            this.setReuseAddr(false);
            this.engine = new WebSocketImpl(this, protocolDraft);
        }
    }

    public URI getURI() {
        return this.uri;
    }

    public Draft getDraft() {
        return this.draft;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void reconnect() {
        this.reset();
        this.connect();
    }

    public boolean reconnectBlocking() throws InterruptedException {
        this.reset();
        return this.connectBlocking();
    }

    private void reset() {
        try {
            this.closeBlocking();
            if (this.writeThread != null) {
                this.writeThread.interrupt();
                this.writeThread = null;
            }

            this.draft.reset();
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }
        } catch (Exception var2) {
            this.onError(var2);
            this.engine.closeConnection(1006, var2.getMessage());
            return;
        }

        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        this.engine = new WebSocketImpl(this, this.draft);
    }

    public void connect() {
        if (this.writeThread != null) {
            throw new IllegalStateException("WebSocketClient objects are not reuseable");
        } else {
            this.writeThread = new Thread(this);
            this.writeThread.setName("WebSocketConnectReadThread-" + this.writeThread.getId());
            this.writeThread.start();
        }
    }

    public boolean connectBlocking() throws InterruptedException {
        this.connect();
        this.connectLatch.await();
        return this.engine.isOpen();
    }

    public void close() {
        if (this.writeThread != null) {
            this.engine.close(1000);
        }

    }

    public void closeBlocking() throws InterruptedException {
        this.close();
        this.closeLatch.await();
    }

    public void send(String text) throws NotYetConnectedException {
        this.engine.send(text);
    }

    public void send(byte[] data) throws NotYetConnectedException {
        this.engine.send(data);
    }

    public <T> T getAttachment() {
        return this.engine.getAttachment();
    }

    public <T> void setAttachment(T attachment) {
        this.engine.setAttachment(attachment);
    }

    protected Collection<WebSocket> getConnections() {
        return Collections.singletonList(this.engine);
    }

    public void sendPing() throws NotYetConnectedException {
        this.engine.sendPing();
    }

    public void run() {
        InputStream istream;
        try {
            boolean isNewSocket = false;
            if (this.socket == null) {
                this.socket = new Socket(this.proxy);
                isNewSocket = true;
            } else if (this.socket.isClosed()) {
                throw new IOException();
            }

            this.socket.setTcpNoDelay(this.isTcpNoDelay());
            this.socket.setReuseAddress(this.isReuseAddr());
            if (!this.socket.isBound()) {
                this.socket.connect(new InetSocketAddress(this.uri.getHost(), this.getPort()), this.connectTimeout);
            }

            if (isNewSocket && "wss".equals(this.uri.getScheme())) {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init((KeyManager[])null, (TrustManager[])null, (SecureRandom)null);
                SSLSocketFactory factory = sslContext.getSocketFactory();
                this.socket = factory.createSocket(this.socket, this.uri.getHost(), this.getPort(), true);
            }

            istream = this.socket.getInputStream();
            this.ostream = this.socket.getOutputStream();
            this.sendHandshake();
        } catch (Exception var7) {
            this.onWebsocketError(this.engine, var7);
            this.engine.closeConnection(-1, var7.getMessage());
            return;
        }

        this.writeThread = new Thread(new WebSocketClient.WebsocketWriteThread());
        this.writeThread.start();
        byte[] rawbuffer = new byte[WebSocketImpl.RCVBUF];

        try {
            int readBytes;
            while(!this.isClosing() && !this.isClosed() && (readBytes = istream.read(rawbuffer)) != -1) {
                this.engine.decode(ByteBuffer.wrap(rawbuffer, 0, readBytes));
            }

            this.engine.eot();
        } catch (IOException var5) {
            this.handleIOException(var5);
        } catch (RuntimeException var6) {
            this.onError(var6);
            this.engine.closeConnection(1006, var6.getMessage());
        }

    }

    private int getPort() {
        int port = this.uri.getPort();
        if (port == -1) {
            String scheme = this.uri.getScheme();
            if ("wss".equals(scheme)) {
                return 443;
            } else if ("ws".equals(scheme)) {
                return 80;
            } else {
                throw new IllegalArgumentException("unknown scheme: " + scheme);
            }
        } else {
            return port;
        }
    }

    private void sendHandshake() throws InvalidHandshakeException {
        String part1 = this.uri.getRawPath();
        String part2 = this.uri.getRawQuery();
        String path;
        if (part1 != null && part1.length() != 0) {
            path = part1;
        } else {
            path = "/";
        }

        if (part2 != null) {
            path = path + '?' + part2;
        }

        int port = this.getPort();
        String host = this.uri.getHost() + (port != 80 ? ":" + port : "");
        HandshakeImpl1Client handshake = new HandshakeImpl1Client();
        handshake.setResourceDescriptor(path);
        handshake.put("Host", host);
        if (this.headers != null) {
            Iterator var7 = this.headers.entrySet().iterator();

            while(var7.hasNext()) {
                Map.Entry<String, String> kv = (Map.Entry)var7.next();
                handshake.put((String)kv.getKey(), (String)kv.getValue());
            }
        }

        this.engine.startHandshake(handshake);
    }

    public READYSTATE getReadyState() {
        return this.engine.getReadyState();
    }

    public final void onWebsocketMessage(WebSocket conn, String message) {
        this.onMessage(message);
    }

    public final void onWebsocketMessage(WebSocket conn, ByteBuffer blob) {
        this.onMessage(blob);
    }

    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        this.onFragment(frame);
    }

    public final void onWebsocketOpen(WebSocket conn, Handshakedata handshake) {
        this.startConnectionLostTimer();
        this.onOpen((ServerHandshake)handshake);
        this.connectLatch.countDown();
    }

    public final void onWebsocketClose(WebSocket conn, int code, String reason, boolean remote) {
        this.stopConnectionLostTimer();
        if (this.writeThread != null) {
            this.writeThread.interrupt();
        }

        this.onClose(code, reason, remote);
        this.connectLatch.countDown();
        this.closeLatch.countDown();
    }

    public final void onWebsocketError(WebSocket conn, Exception ex) {
        this.onError(ex);
    }

    public final void onWriteDemand(WebSocket conn) {
    }

    public void onWebsocketCloseInitiated(WebSocket conn, int code, String reason) {
        this.onCloseInitiated(code, reason);
    }

    public void onWebsocketClosing(WebSocket conn, int code, String reason, boolean remote) {
        this.onClosing(code, reason, remote);
    }

    public void onCloseInitiated(int code, String reason) {
    }

    public void onClosing(int code, String reason, boolean remote) {
    }

    public WebSocket getConnection() {
        return this.engine;
    }

    public InetSocketAddress getLocalSocketAddress(WebSocket conn) {
        return this.socket != null ? (InetSocketAddress)this.socket.getLocalSocketAddress() : null;
    }

    public InetSocketAddress getRemoteSocketAddress(WebSocket conn) {
        return this.socket != null ? (InetSocketAddress)this.socket.getRemoteSocketAddress() : null;
    }

    public abstract void onOpen(ServerHandshake var1);

    public abstract void onMessage(String var1);

    public abstract void onClose(int var1, String var2, boolean var3);

    public abstract void onError(Exception var1);

    public void onMessage(ByteBuffer bytes) {
    }

    /** @deprecated */
    @Deprecated
    public void onFragment(Framedata frame) {
    }

    private void closeSocket() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException var2) {
            this.onWebsocketError(this, var2);
        }

    }

    public void setProxy(Proxy proxy) {
        if (proxy == null) {
            throw new IllegalArgumentException();
        } else {
            this.proxy = proxy;
        }
    }

    public void setSocket(Socket socket) {
        if (this.socket != null) {
            throw new IllegalStateException("socket has already been set");
        } else {
            this.socket = socket;
        }
    }

    public void sendFragmentedFrame(Framedata.Opcode op, ByteBuffer buffer, boolean fin) {
        this.engine.sendFragmentedFrame(op, buffer, fin);
    }

    public boolean isOpen() {
        return this.engine.isOpen();
    }

    public boolean isFlushAndClose() {
        return this.engine.isFlushAndClose();
    }

    public boolean isClosed() {
        return this.engine.isClosed();
    }

    public boolean isClosing() {
        return this.engine.isClosing();
    }

    public boolean isConnecting() {
        return this.engine.isConnecting();
    }

    public boolean hasBufferedData() {
        return this.engine.hasBufferedData();
    }

    public void close(int code) {
        this.engine.close();
    }

    public void close(int code, String message) {
        this.engine.close(code, message);
    }

    public void closeConnection(int code, String message) {
        this.engine.closeConnection(code, message);
    }

    public void send(ByteBuffer bytes) throws IllegalArgumentException, NotYetConnectedException {
        this.engine.send(bytes);
    }

    public void sendFrame(Framedata framedata) {
        this.engine.sendFrame(framedata);
    }

    public void sendFrame(Collection<Framedata> frames) {
        this.engine.sendFrame(frames);
    }

    public InetSocketAddress getLocalSocketAddress() {
        return this.engine.getLocalSocketAddress();
    }

    public InetSocketAddress getRemoteSocketAddress() {
        return this.engine.getRemoteSocketAddress();
    }

    public String getResourceDescriptor() {
        return this.uri.getPath();
    }

    private void handleIOException(IOException e) {
        if (e instanceof SSLException) {
            this.onError(e);
        }

        this.engine.eot();
    }

    private class WebsocketWriteThread implements Runnable {
        private WebsocketWriteThread() {
        }

        public void run() {
            Thread.currentThread().setName("WebSocketWriteThread-" + Thread.currentThread().getId());

            try {
                try {
                    while(!Thread.interrupted()) {
                        ByteBuffer buffer = (ByteBuffer) WebSocketClient.this.engine.outQueue.take();
                        WebSocketClient.this.ostream.write(buffer.array(), 0, buffer.limit());
                        WebSocketClient.this.ostream.flush();
                    }
                } catch (InterruptedException var8) {
                    Iterator var2 = WebSocketClient.this.engine.outQueue.iterator();

                    while(var2.hasNext()) {
                        ByteBuffer bufferx = (ByteBuffer)var2.next();
                        WebSocketClient.this.ostream.write(bufferx.array(), 0, bufferx.limit());
                        WebSocketClient.this.ostream.flush();
                    }
                }
            } catch (IOException var9) {
                WebSocketClient.this.handleIOException(var9);
            } finally {
                WebSocketClient.this.closeSocket();
                WebSocketClient.this.writeThread = null;
            }

        }
    }
}
