package com.unisound.iot.controller.dubbo.ServiceProvider;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company 北京云知声技术有限公司
 */

public class HellowServiceImpl implements HellowService {



    @Override
    public String sayHellow(String userName) {

        System.out.println( "HellowServiceImpl实现类：" + userName);
        return userName;
    }












}
