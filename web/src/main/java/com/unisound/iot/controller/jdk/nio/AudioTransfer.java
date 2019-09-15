package com.unisound.iot.controller.jdk.nio;

import com.unisound.iot.common.exception.ProtocolException;
import com.unisound.iot.common.util.SystemUtils;
import com.unisound.iot.controller.jdk.nio.transfer_mp3_test.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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
    private static int VAD_CHECK_SPLIT_NUM = 120 * 16000 * 2 / PER_READ_TIME;

    //默认最多 5min 一个片段
    private static int MAX_VAD_CHECK_SPLIT_NUM = 300 * 16000 * 2 / PER_READ_TIME;

    public static final int ONE_MS_LENGTH = 32;


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
    //转写结果
    private TranscribeResult transferResult = null;
    //转写文件的名称
    private RequestParam param;

    private static  FileInfo file = null;

    List<Object> finalChunks = null;

    public AudioTransfer(  int threadNum ,String fileNameUrl) {
//        this.executor =  Executors.newFixedThreadPool(threadNum);
        this.executor = new ThreadPoolExecutor( 2 , 4 ,30 , TimeUnit.SECONDS ,
                new LinkedBlockingDeque<>( 10 )   );
        this.fileNameUrl = fileNameUrl;
        this.threadNum = threadNum;
        MAX_VAD_CHECK_SPLIT_NUM = 300 * 16000 * 2 / PER_READ_TIME;
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
                } catch (Exception e) {
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

    public TranscribeResult transfer(String url ,String s)throws ProtocolException, IOException   {
        ByteBuffer buffer = null;
        InputStream inputStream =  new FileInputStream(new File(url ));
        byte[] bytes = new byte[ 10240 ];
        int length = 0 ;
        int audioSplitLen = 0;
        OutputStream outputStream = new FileOutputStream( new File( "/opt/"+ Thread.currentThread().getName()+".txt" ));
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




    public AudioTransfer(RequestParam param, int threadNum, FileInfo file) throws ProtocolException{
//        this.file = file;
        transferResult = new TranscribeResult(param.getTransId());
        startTimes = new TreeMap<>();
        endTimes = new TreeMap<>();
        finalChunks = new ArrayList<Object>();

        transferResult.setStartTime(System.currentTimeMillis());
        transferResult.setAppkey(param.getAppKey());
        transferResult.setUserid(param.getUserId());
        transferResult.setStatus( "running" );
        transferResult.setParam(param);
        transferResult.setCallBackUrl(param.getCallBackUrl());

//        TranscribeResultManager.initProgress(transferResult, param.getRetry());

        this.param = param;
        executor = new ThreadPoolExecutor( threadNum, threadNum, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<>() );

        this.threadNum = threadNum;
        results = new TreeMap<>();
    }


    public static void main(String[] args) {
        RequestParam param = new RequestParam();
        param.setAppKey("appkey_123");
        param.setUserId( "userId" );
        param.setCallBackUrl( "http://www.sss.com" );
        param.setTransId( "transid12345" );
        try {
            AudioTransfer audioTransfer = new AudioTransfer( param , 4 ,null );
            audioTransfer.transfer( "D://huawei_rt0.6_quiet_caijing.wav" );
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }



    private TranscribeResult transfer(String fileName) throws ProtocolException{
        //填充数据
        FileInputStream fin = null;
        VADCheck check = null;//new VADCheck();
        try {
            fin = new FileInputStream(fileName);

            int len = 0;

            //每次读取 20ms 的数据
            byte[] buff = new byte[PER_READ_TIME];
            /** 读取转写文件次数 */
            int readCount = 0;
            /** 用于最后合并转写文件的结果的顺序序号 , 转写文件次数 */
            int audioNum = 0;
            /** 文件当前读取的总长度 */
            long audioLength = 0;
            /** */
            int audioSplitLen = 0;

            long stime = System.currentTimeMillis();
            long updateTime = System.currentTimeMillis();
            while((len = fin.read(buff)) > 0){
                System.out.println( "读取文件到buff:" + new String( buff ));
                /**读取次数如果大于  */
                while (count.get() > (threadNum + threadNum/2)) {
                    //超过20秒更新缓存
                    if ((System.currentTimeMillis() - updateTime) > TASK_HEART_BEAT) {
                        TranscribeResultManager.updateTaskHeartBeatToRedis(transferResult.getTransferId());
                        updateTime = System.currentTimeMillis();
                    }
                    SystemUtils.sleep(100);
                }
                audioLength += len;

                readCount++;
                try{
                    System.arraycopy(buff, 0, audioData, audioSplitLen, len);
                }catch (Exception e){
                    System.out.println( "audioData长度："+ audioData.length );
                    System.out.println( "audioSplitLen长度："+ audioSplitLen  );
                    System.out.println( "len长度："+ len );
                }
                System.out.println( "system拷贝到audioData："+ readCount + "次," + new String( audioData ));
                audioSplitLen += len;

                //获取的语音片段的次数达到 VAD 检查之后，开始检测 vad
                if(readCount > VAD_CHECK_SPLIT_NUM || readCount > 50 ){
                    /**如果两分钟内有静音片段或者 遍历文件的长度已经超过两分钟文件字节的长度则强制进行转写  */
//                    if(check.check(buff, len) || readCount > MAX_VAD_CHECK_SPLIT_NUM){
                    if(  readCount > MAX_VAD_CHECK_SPLIT_NUM || readCount > 50 ){
                        // 计算片段起始时间  一毫秒的片段长32 byte
                        long startTime = (audioLength - audioSplitLen) / 32;
                        // 计算片段结束时间
                        long endTime = audioLength / ONE_MS_LENGTH;
                        this.addAudio(audioNum, audioSplitLen, startTime, endTime);
                        audioNum++;
                        readCount = 0;
                        /** 当前片段转写完毕后归零 */
                        audioSplitLen = 0;
                    }
                }
            }
            spliteFinish = true;
            long etime = System.currentTimeMillis();
            logger.debug("split time: " + (etime - stime) + "ms");
            // 计算片段起始时间
            long startTime = (audioLength - audioSplitLen) / ONE_MS_LENGTH;
            // 计算片段结束时间
            long endTime = audioLength / ONE_MS_LENGTH;
            if(audioSplitLen > 0){
                this.addAudio(audioNum, audioSplitLen, startTime, endTime);
            }

            audioData = null;

            //计算语音时长
            transferResult.setAudioDration(audioLength / ONE_MS_LENGTH);
            System.out.println( "输出：" + audioLength / ONE_MS_LENGTH);

            System.out.println("["   + "]fileName: " + fileName + ", duration: " + transferResult.getAudioDration()
                    + "ms, split: " + audioNum);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                }catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
//            check.release();
        }

        TranscribeResult result =  null;//getResult();
        return result;
    }



    private void addAudio(int audioNum, int audioL, long startTime, long endTime) {
        if(audioData == null){
            return;
        }
        Audio audio = new Audio();
        audio.setTansferFile(param.getFileName());
        audio.setAudioNum(audioNum);
        audio.setStartTime(startTime);
        audio.setEndTime(endTime);
        byte[] data = new byte[audioL];
        System.arraycopy(audioData, 0, data, 0, audioL);
        audio.setData(data);
        audioNum++;
        data = null;
        this.addAudio(audio);
    }
    private void addAudio(final Audio audio){
        count.getAndIncrement();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                transferAudio(audio);
            }

        });
    }
    /**
     * 对一个语音片段进行转写
     * @param audio
     */
    private void transferAudio(Audio audio){
        int tryTime = 5;

        boolean success = false;

        Exception error = null;

        while(tryTime > 0){
            try {
                long stime = System.currentTimeMillis();
                List chunkList = AudioRecognize.recognize(audio.getData(), param);
                long etime = System.currentTimeMillis();
                logger.debug("[" + param.getTransId() + "]transfer split (" + (etime - stime) +"ms):" + audio);
                synchronized (results) {
                    results.put(audio.getAudioNum(), chunkList);
                    startTimes.put(audio.getAudioNum(),audio.getStartTime());
                    endTimes.put(audio.getAudioNum(),audio.getEndTime());
                }
                success = true;
                break;
            } catch (Exception e) {
                logger.error("[" + param.getTransId() + "]" + e.getMessage() + ", transfer split error:" + audio, e);
                error = e;
                SystemUtils.sleep(2000);
            }

            tryTime--;
        }

        if(!success && null != error){

        }

        synchronized (results) {
            if(!results.containsKey(audio.getAudioNum())){
                results.put(audio.getAudioNum(), null);
                startTimes.put(audio.getAudioNum(),audio.getStartTime());
                endTimes.put(audio.getAudioNum(),audio.getEndTime());
            }
        }

        transferResult.setProgress(transferResult.getProgress() + audio.getDuration());

        count.getAndDecrement();
    }








    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public TreeMap<Integer, List> getResults() {
        return results;
    }

    public void setResults(TreeMap<Integer, List> results) {
        this.results = results;
    }

    public TreeMap<Integer, Long> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(TreeMap<Integer, Long> startTimes) {
        this.startTimes = startTimes;
    }

    public TreeMap<Integer, Long> getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(TreeMap<Integer, Long> endTimes) {
        this.endTimes = endTimes;
    }

    public TranscribeResult getTransferResult() {
        return transferResult;
    }

    public void setTransferResult(TranscribeResult transferResult) {
        this.transferResult = transferResult;
    }

    public RequestParam getParam() {
        return param;
    }

    public void setParam(RequestParam param) {
        this.param = param;
    }

    public static FileInfo getFile() {
        return file;
    }

    public static void setFile(FileInfo file) {
        AudioTransfer.file = file;
    }


}
