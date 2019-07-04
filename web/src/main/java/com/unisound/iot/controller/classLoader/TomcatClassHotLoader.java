package com.unisound.iot.controller.classLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

/**
 * @Created by yingwuluohan on 2019/6/24.
 * @Company 北京云知声技术有限公司
 */
public class TomcatClassHotLoader {



    public static void main(String[] args) {
        //Tomcat 热加载,判断class是否更新过
        long lastModify = 0L;
        while( true ){
            File file = new File( "" );
            if( file.lastModified() > lastModify ){
                lastModify = file.lastModified();
                try {

                    HotFix.classLoader();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }


        }



    }









}
