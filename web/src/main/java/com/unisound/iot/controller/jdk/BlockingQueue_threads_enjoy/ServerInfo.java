package com.unisound.iot.controller.jdk.BlockingQueue_threads_enjoy;

import java.util.Map;
import java.util.Set;

public interface ServerInfo {
    Object itemValue(String var1);

    Set<String> listItems();

    Map<String, Object> getAllInfos();

    void addAllInfo(Map<String, Object> var1);

    void addItem(String var1, Object var2);
}
