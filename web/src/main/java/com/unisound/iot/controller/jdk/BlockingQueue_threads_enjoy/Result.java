package com.unisound.iot.controller.jdk.BlockingQueue_threads_enjoy;

import java.io.Serializable;
import java.util.Map;

public interface Result extends Serializable {
    String getType();

    void setType(String var1);

    int getStatusCode();

    void setStatusCode(int var1);

    byte[] getData();

    void setData(byte[] var1);

    Map<String, Object> getItems();

    Object getItem(String var1);

    void setItem(String var1, Object var2);
}
