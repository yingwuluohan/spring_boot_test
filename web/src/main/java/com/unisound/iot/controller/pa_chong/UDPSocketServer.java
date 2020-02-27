package com.unisound.iot.controller.pa_chong;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @Created by yingwuluohan on 2019/5/19.
 * @Company fn
 *
 * 用DateGramPacket 监听端口
 * 准备数据 ， 再用容器字节数组 封装成DateGramPacket
 * 阻塞式接收包裹
 *
 *
 */
public class UDPSocketServer {

    public static void main(String[] args) {
        System.out.println( "服务端启动***********" );
        try {
            DatagramSocket server = new DatagramSocket( 9999 );
            //准备数据
            String data = "的水电费健康的说法";
            byte[] container = new byte[ 1024 ];
            DatagramPacket packge = new DatagramPacket( container , 0 , container.length );
            try {
                server.receive( packge );
                byte[] result =packge.getData();

                System.out.println( new String( result ));
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

}
