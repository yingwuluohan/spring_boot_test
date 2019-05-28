package com.unisound.iot.controller.pa_chong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Created by yingwuluohan on 2019/5/19.
 * @Company 北京云知声技术有限公司
 */
public class BroswerPaChong {

    public static void main(String[] args) {
        try {
            URL url = new URL( "https://www.dianping.com");
            //下载资源
            //模拟浏览器
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod( "GET" );
            connection.setRequestProperty( "User-Agent" , "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36" );
            //分析
            BufferedReader br = new BufferedReader( new InputStreamReader( connection.getInputStream(),"UTF-8" ));
            String str = null;
            while ( null != (str = br.readLine() )){
                System.out.println( str );
            }

            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
