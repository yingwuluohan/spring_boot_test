package com.unisound.iot.controller.design_modle;

import com.unisound.iot.controller.design_modle.proxy.Count;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProxyTest {

    public static void main(String[] args) throws IOException {
        ProxyTest t = new ProxyTest();
        t.buildProxyClass();
    }


    public void buildProxyClass() throws IOException {
        byte[] bytes = ProxyGenerator.generateProxyClass( "Count$proxy" , new Class[]{Count.class});
        String fileName = System.getProperty("user.dir")+"/target/Count$proxy.class";
        File file = new File( fileName );
        FileOutputStream fos = new FileOutputStream( file );
        fos.write( bytes );
        fos.flush();
        fos.close();

    }















}
