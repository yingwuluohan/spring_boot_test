package com.unisound.iot.controller.jdk.io;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("io/")
public class AudioTransferRead {




    @SuppressWarnings("unchecked")
    @RequestMapping(value="test/",method = {RequestMethod.GET })
    @ResponseBody
    public String getMsgHistory(HttpServletRequest request ) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String filePath = "D://manage.rar";
        try {
            InputStream inputStream = request.getInputStream();
            FileOutputStream outputStream = new FileOutputStream( new File( filePath));

            long audioLen = 0;
            byte[] buffer = new byte[2048];
            int read = inputStream.read(buffer);
            while (read > -1) {
                audioLen += read;
                // 计算MD5,顺便写到文件
                digest.update(buffer, 0, read);
                outputStream.write(buffer, 0, read);

                read = inputStream.read(buffer);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
