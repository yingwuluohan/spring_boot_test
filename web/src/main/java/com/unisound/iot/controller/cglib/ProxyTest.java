package com.unisound.iot.controller.cglib;

import javax.security.auth.message.MessagePolicy;

/**
 * @Created by yingwuluohan on 2020/4/1.
 */
public class ProxyTest {

    public static void main(String[] args) {
        System.setProperty( "sun.misc.ProxyGenerator.saveGeneratedFiles" ,"true");
        TargetProxy targetProxy = new TargetProxy();

    }

}
