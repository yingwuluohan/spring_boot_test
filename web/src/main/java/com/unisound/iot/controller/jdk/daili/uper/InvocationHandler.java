package com.unisound.iot.controller.jdk.daili.uper;

import java.lang.reflect.Method;

public interface InvocationHandler {

    void invoke(Object proxy, Method method, Object[] args);
}
