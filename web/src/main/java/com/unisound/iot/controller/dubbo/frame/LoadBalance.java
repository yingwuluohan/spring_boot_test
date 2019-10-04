package com.unisound.iot.controller.dubbo.frame;

import java.util.List;
import java.util.Random;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 * 负载均衡算法
 *
 */
public class LoadBalance {


    //模拟随机的负载均衡算法
    public static Url random(List< Url > list ){
        Random random = new Random();
        int n = random.nextInt( list.size() );
        return list.get( n );

    }














}
