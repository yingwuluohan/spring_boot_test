package com.unisound.iot.controller.jdk.io;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


@RestController
@RequestMapping("nio/")
public class AudioTransferRead {





    @RequestMapping(value="test/",method = {RequestMethod.GET })
    @ResponseBody
    public String getIo(HttpServletRequest request ) {
//        MessageDigest digest = null;

//        try {
//            digest = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        String filePath = "D://test_io"+ Math.random() +".jpg";
        try {

            System.out.println( "执行线程名称:" + Thread.currentThread().getName() + "****************" );
            System.out.println( "接收参数sessionId：" + request.getSession().getId() );
            FileOutputStream outputStream = new FileOutputStream( new File( filePath));
            InputStream inputStream = request.getInputStream();
            System.out.println("request.getContentLength():"+request.getContentLength() );
            long audioLen = 0;
            byte[] buffer = new byte[2048];
            int len = 0 ;
            int read = inputStream.read(buffer);
            while (( len = read ) >  -1) {
                audioLen += read;
                // 计算MD5,顺便写到文件

                outputStream.write(buffer, 0, read);
                read = inputStream.read(buffer);
            }
            if( null != outputStream ){
                outputStream.close();
            }
            if( null != inputStream ){
                inputStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static byte[] audioData = new byte[ 1024 * 5 ];
    public static  void readFile() throws IOException {
        String fileName = "D:\\workspace.xml";
        InputStream fin = null;
        try {
            fin = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int len = 0;

        byte[] buff = new byte[1024];

        int count = 0;
        int audioNum = 0;
        long audioLength = 0;

        int audioSplitLen = 0;

        long stime = System.currentTimeMillis();
        while((len = fin.read(buff)) > 0) {
            audioLength += len;

            count++;

            System.arraycopy(buff, 0, audioData, audioSplitLen, len);
            System.out.println( "读取长度" + len );
            System.out.println( "audioData:" + new String(audioData ) );
            System.out.println( "buff:" +new String(buff) );
        }
    }

    public static void main(String[] args) {
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
