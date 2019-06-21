package com.unisound.iot.controller.websocket;

/**
 * @Created by yingwuluohan on 2019/4/26.
 * @Company 北京云知声技术有限公司
 */

import com.unisound.iot.controller.websocket.javax_websocket.HttpSessionConfigurator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

//TODO 这个注解用来标记一个类是 WebSocket 的处理器。

//TODO 如果需要configurator 这个参数做onopen 获取session ，则需要继承Configurator 重新
@ServerEndpoint(value = "/websocket/{info}" , configurator = HttpSessionConfigurator.class)
@Component
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("info") String info , Session session ,EndpointConfig config ) throws InterruptedException {
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println( httpSession.getAttribute( "name" ));
        System.out.println( "当前建立连接的线程名称:" + Thread.currentThread().getName() );
        Thread.sleep( 3000L );
        System.out.println( "info:" + info );
        InetSocketAddress address = WebsocketUtil.getRemoteAddress(session);
        if( null != address ){
            String hostAddress = address.getAddress().getHostAddress();
            System.out.println( "客户端地址：" + hostAddress );
        }
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }



    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws InterruptedException {
        System.out.println("来自客户端的消息:" + message);
        String scheme = session.getRequestURI().getScheme();
        System.out.println( "scheme :" + scheme );
        System.out.println( "当前发送内容的线程名称:" + Thread.currentThread().getName() );
        Thread.sleep( 3000L );
        //群发消息
        for(MyWebSocket item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnMessage
    public void onMessage(byte[] data ){

        if( null != data ){
            long length = data.length;
            System.out.println( "数据长度:" + length );
            System.out.println( "OnMessage:" + new String( data ));
        }


    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        byte[] data = message.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        this.session.getBasicRemote().sendText(message);
        this.session.getBasicRemote().sendBinary( buffer );
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount.getAndDecrement();
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount.decrementAndGet();
    }


    private static byte[] int2Byte(int a) {
        return new byte[] {
                (byte) (a & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 24) & 0xFF)
        };
    }


    public static void main(String[] args) {
        System.out.println( ":" + int2Byte( 100111 ));

        Map<String , Integer > map = new TreeMap<>();
        map.put( "a" , 3 );
        map.put( "a" , 31 );
        map.put( "b" , 40 );
        map.put( "c" , 50 );

    }


}
