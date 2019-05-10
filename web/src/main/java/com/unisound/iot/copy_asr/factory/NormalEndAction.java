package com.unisound.iot.copy_asr.factory;

import com.unisound.iot.common.exception.ProtocolException;
import com.unisound.iot.copy_asr.api.ActionInterface;
import com.unisound.iot.copy_asr.api.Result;
import com.unisound.iot.copy_asr.entity.CommonContext;

public class NormalEndAction extends CommonContext implements ActionInterface {
    public NormalEndAction(String id) {
        super(id);
    }



    @Override
    public Result proccess() throws ProtocolException {
        return null;
    }

    @Override
    public Object getActionLock() {
        return null;
    }

    @Override
    public void getException() throws Exception {

    }
}
