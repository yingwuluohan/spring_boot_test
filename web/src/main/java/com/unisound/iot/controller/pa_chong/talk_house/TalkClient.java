package com.unisound.iot.controller.pa_chong.talk_house;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Created by yingwuluohan on 2019/5/20.
 * @Company 北京云知声技术有限公司
 */
public class TalkClient {

    public static void main(String[] args) {
        Socket socket= null;
        try {
            System.out.println( "聊天室客户端启动****" );
            Socket client = new Socket("127.0.0.1" , 9999 );


            DataInputStream date = new DataInputStream( client.getInputStream() );
            String msg = date.readUTF(   );
            System.out.println(  msg );
            System.out.println( "建立的了连接" );
            date.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
