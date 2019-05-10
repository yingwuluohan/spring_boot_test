package com.unisound.iot.copy_asr.factory;

import com.unisound.iot.common.exception.ProtocolException;
import com.unisound.iot.copy_asr.api.ActionInterface;
import com.unisound.iot.copy_asr.api.Result;
import com.unisound.iot.copy_asr.entity.CommonContext;

public class RequestAction extends CommonContext implements ActionInterface {

    private String id;
    private Object lock = new Object();

    public RequestAction( String id ) {
        super( id );
        this.id = id;
    }

    @Override
    public Result proccess() throws ProtocolException {
        System.out.println( "RequestAction -- proccess 执行 " );

        return null;
    }

    @Override
    public Object getActionLock() {
        return this.lock;
    }

    @Override
    public void getException() throws Exception {

    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
