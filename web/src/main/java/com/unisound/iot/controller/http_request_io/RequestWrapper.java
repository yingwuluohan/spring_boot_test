package com.unisound.iot.controller.http_request_io;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 步骤1：
 * HttpServletRequest的输入流只能读一次，当我们调用getInputStream()方法获取输入流时得到的是一个InputStream对象，
 * 而实际类型是ServletInputStream，它继承于InputStream。
 * InputStream的read()方法内部有一个postion，标志当前流被读取到的位置，
 * 每读取一次，该标志就会移动一次，如果读到最后，read()会返回-1，表示已经读取完了。如果想要重新读取则需要调用reset()方法，
 * position就会移动到上次调用mark的位置，mark默认是0，所以就能从头再读了。调用reset()方法的前提是已经重写了reset()方法，
 * 当然能否reset也是有条件的，它取决于markSupported()方法是否返回true。
 *
 * 步骤2：
 * HttpServletRequestWrapper类并没有真正去实现HttpServletRequest的方法，
 * 而只是在方法内又去调用HttpServletRequest的方法，
 * 所以可以通过继承该类并实现想要重新定义的方法以达到包装原生HttpServletRequest对象的目的
 * 步骤3：
 * 除了要写一个包装器外，我们还需要在过滤器里将原生的HttpServletRequest对象替换成RequestWrapper对象
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String sessionStream = getBodyString(request);
        body = sessionStream.getBytes(Charset.forName("UTF-8"));
    }
    public String getBodyString(){
        return new String(body, Charset.forName("UTF-8"));
    }

    public void setBody(byte[] body){
        this.body = body;
    }

    /**
     * 获取请求Body
     */
    public String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {log.error( "IO流关闭异常" );}
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {log.error( "IO读流关闭异常" );}
            }
        }
        return sb.toString();
    }
    /**
     * Description: 复制输入流</br>
     *
     * 为了对同一个InputStream对象使用多次。比如客户端从服务器获取数据 ，
     * 利用HttpURLConnection的getInputStream()方法获得Stream对象，
     * 这时既要把数据显示到前台（第一次读取），又想把数据写进文件缓存到本地（第二次读取）。
     * 但第一次读取InputStream对象后，第二次再读取时可能已经到Stream的结尾了（EOFException）或者Stream已经close掉了。
     * 而InputStream对象本身不能复制，因为它没有实现Cloneable接口。
     * 此时，可以先把InputStream转化成ByteArrayOutputStream，后面要使用InputStream对象时，
     * 再从ByteArrayOutputStream转化回来就好了。
     */
    public InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
        //克隆
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

}
