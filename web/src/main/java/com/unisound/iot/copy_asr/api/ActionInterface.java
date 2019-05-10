package com.unisound.iot.copy_asr.api;


import com.unisound.iot.common.exception.ProtocolException;

public interface ActionInterface extends Context {
    Result proccess() throws ProtocolException ;

    Object getActionLock();

    void getException() throws Exception;
}
