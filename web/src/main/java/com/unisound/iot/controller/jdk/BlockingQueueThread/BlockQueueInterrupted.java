package com.unisound.iot.controller.jdk.BlockingQueueThread;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueInterrupted {

    public final static BlockingQueue<String> outQueue = new ArrayBlockingQueue< String>( 11 );
    private static OutputStream ostream;


    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
         ostream = new BufferedOutputStream( new FileOutputStream( new File( "D://test.txt" ) ));
        WebsocketWriteThread thread = new WebsocketWriteThread( ostream );
        Thread envent = new Thread( thread );
        outQueue.put( "asfas" );
        outQueue.put( "sdfasd" );
        outQueue.put( "asdsdf" );
        outQueue.put( "sdfsd" );
        outQueue.put( "sdf" );
        outQueue.put( "sdfsd" );
        envent.start();
    }






    private static class WebsocketWriteThread implements Runnable {
        private  OutputStream ostream;

        private WebsocketWriteThread( OutputStream ostream ) {
            this.ostream = ostream;
        }

        public void run() {
            Thread.currentThread().setName("WebSocketWriteThread-" + Thread.currentThread().getId());

            try {
                try {
                    System.out.println( "" + Thread.currentThread().getName() );
                    while(!Thread.interrupted()) {
                        String buffer = (String) outQueue.take();
                         ostream.write(buffer.getBytes(), 0, buffer.length());
                         ostream.flush();
                    }
                } catch (InterruptedException var8) {
                    Iterator var2 =  outQueue.iterator();

                    while(var2.hasNext()) {
                        ByteBuffer bufferx = (ByteBuffer)var2.next();
                         ostream.write(bufferx.array(), 0, bufferx.limit());
                         ostream.flush();
                    }
                }
            } catch ( Exception var9) {

            }

        }
    }





}
