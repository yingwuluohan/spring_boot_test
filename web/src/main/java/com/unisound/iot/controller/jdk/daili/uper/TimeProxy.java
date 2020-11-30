package com.unisound.iot.controller.jdk.daili.uper;

import com.unisound.iot.controller.jdk.daili.Flyable;

import java.lang.reflect.Method;

public class TimeProxy implements Flyable {
    private InvocationHandler handler;

    public TimeProxy(InvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void fly() {
        try {
            Method method = Flyable.class.getMethod("fly");
            this.handler.invoke(this, method, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
