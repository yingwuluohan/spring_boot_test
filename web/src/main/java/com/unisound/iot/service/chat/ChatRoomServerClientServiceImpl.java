package com.unisound.iot.service.chat;


import com.unisound.iot.api.chart.ChatRoomServerClientService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by fn on 2017/5/22.
 */
@Service
public class ChatRoomServerClientServiceImpl implements ChatRoomServerClientService {



    private Selector selector = null;
    static final int port = 9999;
    private Charset charset = Charset.forName("UTF-8");
    public static SocketChannel sc = null;
    private String name = "";
    private static String USER_EXIST = "system message: user exist, please change a name";
    private static String USER_CONTENT_SPILIT = "#@#";

    public void initClientService() throws IOException
    {
        selector = Selector.open();
        //连接远程主机的IP和端口
        sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",port));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        //开辟一个新线程来读取从服务器端的数据
        new Thread(new ClientThread()).start();
        //在主线程中 从键盘读取数据输入到服务器端
        Scanner scan = new Scanner(System.in);
        System.out.println( "客户端初始化完成*******************" );

    }

    public void writeContent(String content ){
        if("".equals(content)) {
            return;
        }
        if("".equals(name)) {
            name = content;
            content = name+USER_CONTENT_SPILIT;
        } else {
            content = name+USER_CONTENT_SPILIT+content;
        }
        try {
            sc.write(charset.encode(content));//sc既能写也能读，这边是写
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientThread implements Runnable
    {
        public void run()
        {
            try
            {
                boolean result = true;
                while(result) {
                    int readyChannels = selector.select();
                    if(readyChannels == 0) continue;
                    Set selectedKeys = selector.selectedKeys();  //可以通过这个方法，知道可用通道的集合
                    Iterator keyIterator = selectedKeys.iterator();
                    while(keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                    result = false;
                }
            }
            catch (IOException io)
            {}
        }

        private void dealWithSelectionKey(SelectionKey sk) throws IOException {
            if(sk.isReadable())
            {
                //使用 NIO 读取 Channel中的数据，这个和全局变量sc是一样的，因为只注册了一个SocketChannel
                //sc既能写也能读，这边是读
                SocketChannel sc = (SocketChannel)sk.channel();

                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while(sc.read(buff) > 0)
                {
                    buff.flip();
                    content += charset.decode(buff);
                }
                //若系统发送通知名字已经存在，则需要换个昵称
                if(USER_EXIST.equals(content)) {
                    name = "";
                }
                System.out.println(content);
                sk.interestOps(SelectionKey.OP_READ);
            }
        }
    }
}
