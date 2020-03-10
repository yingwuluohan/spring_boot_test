package com.unisound.iot.controller.bulong_filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by yingwuluohan on 2020/3/6.
 * Google的布隆过滤器
 *
 * 缺点：A。基于单机版的JVM内存运行，无法解决分布式环境
 *   B。服务重启后数据被清空
 */
public class BuLongFilter {

    private static int size = 100000;

    //TODO 末尾的参数表示 布隆过滤器过滤错误系数
    private static BloomFilter< Integer > bloomFilter = BloomFilter.create(Funnels.integerFunnel() , size ,0.01 );

    public static void main(String[] args) {

        for ( int i = 0 ;i <= size ;i++ ){
            bloomFilter.put( i );
        }

        List< Integer > list = new ArrayList<>( 100000);

        //获取1000个不在过滤器里的值，看看有多少个会被认为在过滤器里面
        for( int i = size + 1000 ; i < size + 2000; i++){
            if( bloomFilter.mightContain( i )){
                list.add( i );

            }
        }

        System.out.println( "google误判的数量是:" + list.size() );

    }


}
