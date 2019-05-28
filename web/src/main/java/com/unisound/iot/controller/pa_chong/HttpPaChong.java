package com.unisound.iot.controller.pa_chong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Created by yingwuluohan on 2019/5/19.
 * @Company 北京云知声技术有限公司
 */
public class HttpPaChong {

    public static void main(String[] args) {
        try {
            URL url = new URL( "https://www.jd.com");
            //下载资源
            InputStream inputSteam = url.openStream();
            System.out.println( "---" );
            //分析
            BufferedReader br = new BufferedReader( new InputStreamReader( inputSteam,"UTF-8" ));
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
