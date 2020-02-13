package com.unisound.iot.controller.jdk.lambad.lambda_function;




/**
 * 用于传递某个代码块，此代码块可能会抛出异常
 * 与{@link  }一起使用
 *
 * @author gaofubo
 */
@FunctionalInterface
public interface SupplierWithThrowable<R> {

    /**
     * 消费
     *
     * @return 返回值
     * @throws Throwable 异常信息
     */
    R get() throws Throwable;
}