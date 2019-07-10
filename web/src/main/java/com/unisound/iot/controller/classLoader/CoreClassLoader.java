package com.unisound.iot.controller.classLoader;

/**
 * @Created by yingwuluohan on 2019/6/24.
 * @Company 北京云知声技术有限公司
 */
public class CoreClassLoader {


    private static String loadername;

    static {

        System.out.println( "===========================" );
    }

    public static String init(){
        return  "class_loader";
    }

    public static String getLoadername() {
        return loadername;
    }

    public static void setLoadername(String loadername) {
        CoreClassLoader.loadername = loadername;
    }
}
