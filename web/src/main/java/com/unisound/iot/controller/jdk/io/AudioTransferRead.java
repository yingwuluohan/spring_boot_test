package com.unisound.iot.controller.jdk.io;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;


@RestController
@RequestMapping("io/")
public class AudioTransferRead {





    @RequestMapping(value="test"  ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getMsgHistory(HttpServletRequest request ){
        MessageDigest digest = null;
//        try {
//            digest = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        String filePath = "D://test_io"+ Math.random() +".jpg";
        try {


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


}
