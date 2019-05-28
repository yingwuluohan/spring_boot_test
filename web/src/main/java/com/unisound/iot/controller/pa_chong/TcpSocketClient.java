package com.unisound.iot.controller.pa_chong;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Created by yingwuluohan on 2019/5/19.
 * @Company 北京云知声技术有限公司
 * 使用socket创建客户端
 * 需要指定服务端的IP和端口
 *
 *
 */
public class TcpSocketClient {

    public static void main(String[] args) {
        try {
            System.out.println( "client 连接*********" );
            Socket client = new Socket("127.0.0.1" , 9999 );
            BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
            String content = br.readLine();
            DataOutputStream date = new DataOutputStream( client.getOutputStream() );
            date.writeUTF( "sdfksdfu" );
            date.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
