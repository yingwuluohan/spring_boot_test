package com.unisound.iot.service.chat;

import com.unisound.iot.api.chart.ChatRoomServerInitService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by fn on 2017/5/19.
 */
@Service
public class ChatRoomServerInitServiceImpl implements ChatRoomServerInitService {
    private static Logger log = Logger.getLogger(ChatRoomServerInitServiceImpl.class);
    private Selector selector = null;
    static final int port = 9999;
    private Charset charset = Charset.forName("UTF-8");
    //用来记录在线人数，以及昵称
    private static HashSet<String> users = new HashSet<String>();

    private static String USER_EXIST = "system message: user exist, please change a name";
    //相当于自定义协议格式，与客户端协商好
    private static String USER_CONTENT_SPILIT = "#@#";

    private static boolean flag = false;

    public void init() throws IOException {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        //非阻塞的方式
        server.configureBlocking(false);
        //注册到选择器上，设置为监听状态
        server.register(selector, SelectionKey.OP_ACCEPT);
        log.info("Server is listening now...");
        boolean result = true;
        while(result) {
            int readyChannels = selector.select();
            if(readyChannels == 0) {
                continue;
            }
            log.info( "可以通过这个方法，知道可用通道的集合");
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) keyIterator.next();
                keyIterator.remove();
                dealWithSelectionKey(server,selectionKey);
            }

        }
    }

    public void dealWithSelectionKey(ServerSocketChannel server,SelectionKey selectionKey) throws IOException {
        //测试此键的通道是否已准备好接受新的套接字连接。
        if(selectionKey.isAcceptable()) {
            //接受到此通道套接字的连接。
            SocketChannel socketChannel = server.accept();
            //非阻塞模式
            socketChannel.configureBlocking(false);
            //注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到selector上，之后这个连接的数据，就由这个SocketChannel处理
            //selectionKey = sc.register(selector, SelectionKey.OP_READ);
            socketChannel.register(selector, SelectionKey.OP_READ);
            //将此对应的channel设置为准备接受其他客户端请求
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
            log.info( "" );
            System.out.println("Server is listening from client :" + socketChannel.getRemoteAddress().toString());
            log.info( "将字节序列从给定的缓冲区中写入此通道。" );
            socketChannel.write(charset.encode("Please input your name."));
        }
        //处理来自客户端的数据读取请求,测试此键的通道是否已准备好进行读取。
        if(selectionKey.isReadable()) {
            //返回该SelectionKey对应的 Channel，其中有数据需要读取 返回为之创建此键的通道。
            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer buff = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try {//将字节序列从此通道中读入给定的缓冲区
                while( socketChannel.read(buff) > 0) {
                    buff.flip();
                    content.append(charset.decode(buff));
                }
                System.out.println("Server is listening from client " + socketChannel.getRemoteAddress() + " data rev is: " + content);
                //将此对应的channel设置为准备下一次接受数据
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
            catch (IOException io) {
                selectionKey.cancel();
                if(selectionKey.channel() != null) {
                    selectionKey.channel().close();
                }
            }
            if(content.length() > 0){
                String[] arrayContent = content.toString().split(USER_CONTENT_SPILIT);
                //注册用户
                if(arrayContent != null && arrayContent.length ==1) {
                    String name = arrayContent[0];
                    if(users.contains(name)) {
                        socketChannel.write(charset.encode(USER_EXIST));
                    } else {
                        users.add(name);
                        int num = OnlineNum(selector);
                        String message = "welcome "+name+" to chat room! Online numbers:"+num;
                        BroadCast(selector, null, message);
                    }
                }else if(arrayContent != null && arrayContent.length >1){//注册完了，发送消息
                    String name = arrayContent[0];
                    String message = content.substring(name.length()+USER_CONTENT_SPILIT.length());
                    message = name + " say " + message;
                    if(users.contains(name)) {
                        //不回发给发送此内容的客户端
                        BroadCast(selector, socketChannel , message);
                    }
                }
            }
        }
    }

    public static int OnlineNum(Selector selector) {
        int res = 0;
        for(SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if(targetchannel instanceof SocketChannel) {
                res++;
            }
        }
        return res;
    }

    public void BroadCast(Selector selector, SocketChannel except, String content) throws IOException {
        //广播数据到所有的SocketChannel中
        for(SelectionKey key : selector.keys()){
            log.info( "" );
            Channel targetchannel = key.channel();
            //如果except不为空，不回发给发送此内容的客户端
            if(targetchannel instanceof SocketChannel && targetchannel!=except) {
                SocketChannel dest = (SocketChannel)targetchannel;
                dest.write(charset.encode(content));
            }
        }
    }
}
