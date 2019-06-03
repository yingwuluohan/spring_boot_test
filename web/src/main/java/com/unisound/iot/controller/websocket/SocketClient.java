package com.unisound.iot.controller.websocket;


import org.apache.log4j.Logger;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class SocketClient   {

    private static Logger logger = org.apache.log4j.Logger.getLogger(SocketClient.class);
    private Session session = null;

    private int count = 0;

    @OnOpen
    public void onOpen(Session session){
        sendMessage("onOpen hello benny onOpen");
    }

    @OnClose
    public void onClose(){

    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("server message:"+message);
        if(count <10){
            sendMessage("onMessage hello benny "+(++count));
        }
    }

    @OnError
    public void onError(Throwable thr){

    }

    public SocketClient() {
        super();
    }

    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI( "ws://10.20.222.77:8480/websocket/parms" );
        SocketClient client = new SocketClient( uri );
        client.sendMessage( "test" );

    }

    public SocketClient(URI endpointURI) {
        super();
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();	// 获得WebSocketContainer
            this.session = container.connectToServer(SocketClient.class, endpointURI);	// 该方法会阻塞
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }


    public void sendMessage(String message){
        try {
            this.session.getBasicRemote().sendText(message);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                this.session.getBasicRemote().flushBatch();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    }
