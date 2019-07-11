package com.unisound.iot.controller.jdk.nio;

import com.unisound.iot.common.exception.ProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AudioTransfer  {

    private static final Logger logger = LoggerFactory.getLogger(AudioTransfer.class);


    //每次从文件中读取 20ms 的语音数据
    private static final int PER_READ_TIME = 640;

    //默认1s * 120 = 2min 后开始静音检查，检查到静音后切成一个片段
    private static int VAD_CHECK_SPLIT_NUM;

    //默认最多 5min 一个片段
    private static int MAX_VAD_CHECK_SPLIT_NUM;

    static final int ONE_MS_LENGTH = 32;

    // 切片结束标志
    private boolean spliteFinish = false;

    // 任务心跳时间间隔1min
    private static final int TASK_HEART_BEAT = 60 * 1000;



    private AtomicInteger count = new AtomicInteger(0);

    //固定大小的线程池
    private ExecutorService executor = null;

    //转写线程数
    private int threadNum = 6;

    //保存识别记过
    private TreeMap<Integer, List> results = null;

    //保存语音片段开始时间
    private TreeMap<Integer, Long> startTimes = null;

    //保存语音片段结束时间
    private TreeMap<Integer, Long> endTimes = null;

    public AudioTransfer(  int threadNum ,String fileNameUrl) {
//        this.executor =  Executors.newFixedThreadPool(threadNum);
        this.executor = new ThreadPoolExecutor( 2 , 4 ,30 , TimeUnit.SECONDS ,
                new LinkedBlockingDeque<>( 10 )   );
        this.fileNameUrl = fileNameUrl;
        this.threadNum = threadNum;
        MAX_VAD_CHECK_SPLIT_NUM = 10 * 16000 * 2 / PER_READ_TIME;
    }
    //转码之前的文件名称
    private String fileNameUrl = null;

    //转码之后的文件名称
    private String afterConvertFileName = null;

    private AtomicInteger resultIndex = new AtomicInteger(0);


    public TranscribeResult transfer( ) {


        addAudio( fileNameUrl );

        return null;
    }

    private void addAudio(final String url ){
        count.getAndIncrement();
        System.out.println( "*****************************************" );
        executor.execute( new Runnable() {
            @Override
            public void run() {
                try {
                    transfer( url );
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        System.out.println( "****************************************end");

//        CommonExecutor.execute(new Runnable() {
//
//            @Override
//            public void run() {
//                executor.execute(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        try {
//                            transfer( url );
//                        } catch (ProtocolException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                });
//            }
//        });

    }

    /**
     * 执行语音转写
     * @return
     * @throws ProtocolException
     */
    private byte[] audioData = new byte[100 * PER_READ_TIME + 1000];
    public TranscribeResult transfer(String url )throws ProtocolException, IOException   {
        ByteBuffer buffer = null;
        InputStream inputStream =  new FileInputStream(new File(url ));
        byte[] bytes = new byte[ 10240 ];
        int length = 0 ;
        int audioSplitLen = 0;
        OutputStream outputStream = new FileOutputStream( new File( "/Users/yingwuluohan/Documents/soft/"+ Thread.currentThread().getName()+".txt" ));
        while ( (  length = inputStream.read( bytes ) ) > 0 ){
            ByteArrayInputStream input = new ByteArrayInputStream(  bytes );

            int byteLength = 0 ;
            byte[] bytes1 = new byte[ 1024 ];
            try{
                while ( (byteLength = input.read(bytes1 )) > 0 ){
//                    System.arraycopy(bytes1, 0, audioData, audioSplitLen, byteLength);
                    audioSplitLen += byteLength;
                    ByteBuffer buffer1 = ByteBuffer.wrap( bytes1 , 0, byteLength);
                    System.out.println( buffer1.getLong() );
                    outputStream.write( buffer1.array() );
                }
            }catch ( Exception e ){
                System.out.println( " bytes1.length：" + bytes1.length );
                System.out.println( "audioData:"+audioData.length );
            }


        }
        return null;
    }


















}
