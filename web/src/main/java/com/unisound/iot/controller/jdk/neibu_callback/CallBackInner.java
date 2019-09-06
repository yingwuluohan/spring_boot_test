package com.unisound.iot.controller.jdk.neibu_callback;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CallBackInner {

    final Timer timer = new Timer();

    public void getTask(){
        final ConcurrentLinkedQueue<SingleServerThread> singleServerThreads = new ConcurrentLinkedQueue<>();
        AsrEndCallBack asrEndCallBack = new AsrEndCallBack() {
            @Override
            public void asrEnd() {
                if (timer != null) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //丢弃超时后的除了asr及现有服务的请求结果，停止其相应的阻塞
                            SingleServerThread singleServerThread;
                            while ((singleServerThread = singleServerThreads.poll()) != null) {
                                if (singleServerThread.isAlive() && !P.ASR.equals(singleServerThread.type)) {
                                    singleServerThread.latchCountDown(C.SERVER_INTERRUPT_ERROR);
                                }
                            }
                            System.out.println( "------内部类执行------" );

                        }
                    }, 2000 );
                }
            }
        };

    }







    private interface AsrEndCallBack {
        void asrEnd();
    }


    static class SingleServerThread extends Thread {
        private String type;


        public void latchCountDown(int errorCode) {
            System.out.println( "code:" + errorCode );
        }
    }
}
