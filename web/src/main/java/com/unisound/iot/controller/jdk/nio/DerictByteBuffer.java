package com.unisound.iot.controller.jdk.nio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/bytes")
@Controller
public class DerictByteBuffer {


    private static List<ByteBuffer> list = new ArrayList<>();

    /**
     * /bytes/init
     */
    @ResponseBody
    @RequestMapping( value="/init" ,method= RequestMethod.GET )
    public void initHttpChat(){
        try {
            //MAC
//            AudioTransfer audioTransfer = new AudioTransfer( 4 , "/Users/yingwuluohan/Documents/soft/redis-3.2.9.tar" );
            //TODO linux
            AudioTransfer audioTransfer = new AudioTransfer( 4 , "/opt/springboot.hprof" );
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    try {
//                        audioTransfer.transfer( "D:\\my.txt" );
//                        System.out.println( "*******************" );
//                    } catch (ProtocolException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    new Transcribe(audioTransfer).run();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args) throws Exception {
        DerictByteBuffer d = new DerictByteBuffer();
        d.initHttpChat();



//        for( int  i = 0 ; i < 10 ;i++ ){
//
//
//
//             buffer = ByteBuffer.allocateDirect(1024 * 1024 * 100);
//            System.out.println("start");
//
//            list.add( buffer );
//            System.out.println("end" + i);
//            sleep(1000);
//        }
        System.out.println( "DerictByteBuffer全部分配完毕" );
//        clean( buffer );
    }

    public static void clean(final ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            ((DirectBuffer)byteBuffer).cleaner().clean();
        }
    }
















}
