package com.unisound.iot.copy_asr.api;

import java.util.Set;

public interface Context {
    void setId(String var1);

    String getId();

    Object getAttribute(String var1);

    void setAttribute(String var1, Object var2);

    Set<String> attributeKeys();

    Object getInput();

    void setInput(Object var1);

    Object getOuput();

    void setOuput(Object var1);
}
