package com.unisound.iot.controller.dubbo.frame;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 *
 * 为了抽象http 和 dubbo 两个协议，因此抽象出该接口
 *
 */
public interface Protocol {

    void start(Url url);


    String send(Url url, Invocation invocation);




}
