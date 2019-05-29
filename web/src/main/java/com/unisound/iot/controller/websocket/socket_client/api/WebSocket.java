package com.unisound.iot.controller.websocket.socket_client.api;

import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.Collection;

public interface WebSocket {
    int DEFAULT_PORT = 80;
    int DEFAULT_WSS_PORT = 443;

    void close(int var1, String var2);

    void close(int var1);

    void close();

    void closeConnection(int var1, String var2);

    void send(String var1) throws NotYetConnectedException;

    void send(ByteBuffer var1) throws IllegalArgumentException, NotYetConnectedException;

    void send(byte[] var1) throws IllegalArgumentException, NotYetConnectedException;

    void sendFrame(Framedata var1);

    void sendFrame(Collection<Framedata> var1);

    void sendPing() throws NotYetConnectedException;

    void sendFragmentedFrame(Framedata.Opcode var1, ByteBuffer var2, boolean var3);

    boolean hasBufferedData();

    InetSocketAddress getRemoteSocketAddress();

    InetSocketAddress getLocalSocketAddress();

    boolean isConnecting();

    boolean isOpen();

    boolean isClosing();

    boolean isFlushAndClose();

    boolean isClosed();

    Draft getDraft();

    org.java_websocket.WebSocket.READYSTATE getReadyState();

    String getResourceDescriptor();

    <T> void setAttachment(T var1);

    <T> T getAttachment();

    public static enum READYSTATE {
        NOT_YET_CONNECTED,
        CONNECTING,
        OPEN,
        CLOSING,
        CLOSED;

        private READYSTATE() {
        }
    }

    public static enum Role {
        CLIENT,
        SERVER;

        private Role() {
        }
    }
}
