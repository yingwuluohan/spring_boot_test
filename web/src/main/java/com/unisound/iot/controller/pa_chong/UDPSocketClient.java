package com.unisound.iot.controller.pa_chong;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @Created by yingwuluohan on 2019/5/19.
 * @Company 北京云知声技术有限公司
 *
 * 用DataGramPacket 指定端口，创建发送端
 * 把发送的数据转成字节数组
 *  再封装成DataGramPacket 并指定目的地
 *  发送包 send
 */
public class UDPSocketClient {

    public static void main(String[] args) {
        System.out.println( "启动发送方***********" );
        try {
            DatagramSocket datagramSocket = new DatagramSocket( 8888 );
            //准备数据
            String data = "的水电费健康的说法";
            byte[] bytes = data.getBytes();
            DatagramPacket datagram = new DatagramPacket( bytes , 0 , data.length(),
                     new InetSocketAddress( "127.0.0.1" , 9999 ));
            try {
                datagramSocket.send( datagram );
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

}
