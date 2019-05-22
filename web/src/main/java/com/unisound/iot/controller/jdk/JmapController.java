package com.unisound.iot.controller.jdk;

import com.unisound.iot.controller.jdk.io.AudioTransferRead;
import com.unisound.iot.service.report.ReportService;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ProtocolException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by yingwuluohan on 2019/4/2.
 * @Company 北京云知声技术有限公司
 */

@RestController
@RequestMapping("map/")
public class JmapController {

    Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private ReportService reportService;
    private int stackLength = 1;

    private String memory = "public String getMsgHistory(HttpServletRequest request, @PathVariable(name=\"id\") long id)";

    // map/InputStream
    @RequestMapping(value="InputStream",method = {RequestMethod.GET })
    @ResponseBody
    public String getReadInfo(HttpServletRequest request ) throws Exception {
        File file = new File( "/application/iotest.txt" );
        InputStream inputStream = new FileInputStream( file );
        String fileName = "/application/iotest22.txt";
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        long audioLen = 0;
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[2048];
            int read = inputStream.read(buffer);
            while (read > -1) {
                audioLen += read;
                // 计算MD5,顺便写到文件
                digest.update(buffer, 0, read);
                outputStream.write(buffer, 0, read);

                read = inputStream.read(buffer);
            }
        } catch (Exception e) {
            throw new Exception(""+e.getMessage());

        } finally {

            try {
                outputStream.close();
            } catch (Exception e) {
            }
        }
        if (audioLen == 0) {
            throw new ProtocolException(  "audio is empty");
        }
        byte[] bytes = digest.digest();
        System.out.println( Hex.encodeHexString( bytes ) );
        return Hex.encodeHexString( bytes );


    }

    public static void main(String[] args) {
        AudioTransferRead audio = new AudioTransferRead();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //map/test/12
    @SuppressWarnings("unchecked")
    @RequestMapping(value="test/{id}",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getMsgHistory(HttpServletRequest request, @PathVariable(name="id") long id){


        try{

            while(true){
                for( int i = 0 ; i < 1000000000; i ++ ){
                    List< String > list = new ArrayList<>();
                    list.add(i +"" );
                    ThreadTest thread = new ThreadTest( list , i + "test" );
                    Thread threadGo = new Thread( thread );
                    threadGo.start();
                    System.out.println( "for循环：" + i );
                }
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }



        return "ok";
    }

    /**
     * 堆栈溢出
     * map/test/stack
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="test/stack",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void makeStackOverflowError(){
        stackLength++;
        makeStackOverflowError();
    }

    //  /map/test/out
    @SuppressWarnings("unchecked")
    @RequestMapping(value="test/out",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void makeOutofMemoryError(){

        try{
            for( int i = 0 ;i < 6 ;i++ ){
                List<String > list = new ArrayList<>();
                ThreadTest test = new ThreadTest( list , memory );
                Thread thread = new Thread( test );
                thread.start();
            }
        }catch ( Exception e ){
            System.out.println( "抛异常："  +e.getMessage()  );
            e.printStackTrace();
        }


    }

    class ThreadTest implements Runnable{

        public ThreadTest(List<String> list, String content ){
            this.list = list;
            this.content = content;
        }

        private List<String> list;
        private String content;


        public void run(){
//          List<String > list = new ArrayList<>();
            try{
                System.out.println(  "当前线程：" + Thread.currentThread().getName() );
                while( true ){
                    log.info( "当前线程：" +Thread.currentThread().getName() + "list size:" + list.size() );
                    list.add(  memory+"dddddd" + Math.random() );

                    System.out.println( Thread.currentThread().getName()+": " + list.size() );
                }
            }catch ( Exception e ){
                System.out.println( "抛异常：" + list.size() );
                e.printStackTrace();
            }
        }


    }



}
