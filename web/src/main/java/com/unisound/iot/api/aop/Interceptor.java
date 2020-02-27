package com.unisound.iot.api.aop;

/**
 * @Created by yingwuluohan on 2019/3/9.
 * @Company fn
 */
public interface Interceptor {

    <T> T doBefor( T t );

    <T> T afterDo();

}
