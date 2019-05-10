package com.unisound.iot.copy_asr;

import com.unisound.iot.copy_asr.api.Result;
import com.unisound.iot.copy_asr.service.CoreService;
import com.unisound.iot.copy_asr.thread.ActionExcutor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


@WebServlet( urlPatterns = {"/audioService"},  loadOnStartup = 1)
public class InitServlet extends HttpServlet {



    public void init() {
        System.out.println("******servlet init OK******");
        System.out.println("******servlet init OK******");
        System.out.println("******servlet init OK******");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("one request: command is");
        Result engResult = null;
        String id = null;

        try {
            engResult = CoreService.service( getRequestHeaders(request), stream2Bytes(request.getInputStream()));
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }

    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap();
        headers.put("serviceReqCmd", request.getHeader("serviceReqCmd"));
        headers.put("requestID", request.getHeader("requestID"));
        headers.put("serviceType", request.getHeader("serviceType"));
        headers.put("probeNum", request.getHeader("probeNum"));
        headers.put("appKey", request.getHeader("appKey"));
        headers.put("userID", request.getHeader("userID"));
        String sp = request.getHeader("serviceParameter");
        if (sp != null) {
            try {
                sp = URLDecoder.decode(sp, "utf-8");
            } catch (UnsupportedEncodingException var4) {
                System.out.println( var4);
            }
        }

        headers.put("serviceParameter", sp);
        return headers;
    }
    public static byte[] stream2Bytes(InputStream in) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len;
            while((len = in.read(buffer)) != -1) {
                bufferStream.write(buffer, 0, len);
            }
            buffer = null;
            if (bufferStream.toByteArray().length > 0) {
                bytes = bufferStream.toByteArray();
            }

            bufferStream.close();
            bufferStream = null;
            in.close();
            return bytes;
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }
    public void destroy() {
        super.destroy();
        System.out.println("destroy called");
        ActionExcutor.shutdown();

    }





}
