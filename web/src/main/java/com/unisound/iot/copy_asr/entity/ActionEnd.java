package com.unisound.iot.copy_asr.entity;

import com.unisound.iot.common.exception.ProtocolException;
import com.unisound.iot.copy_asr.api.ActionInterface;
import com.unisound.iot.copy_asr.api.Result;

public class ActionEnd extends CommonContext implements ActionInterface {
    public ActionEnd(String id) {
        super(id);
    }
    private Object lock = new Object();


    @Override
    public Result proccess() throws ProtocolException {
        System.out.println( "ActionEnd:" +this.getId() );
        return null;
    }

    @Override
    public Object getActionLock() {
        return this.lock;
    }

    @Override
    public void getException() throws Exception {

    }
}
