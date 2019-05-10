package com.unisound.iot.copy_asr.factory;

import com.unisound.iot.common.exception.ProtocolException;

import com.unisound.iot.copy_asr.api.ActionInterface;
import com.unisound.iot.copy_asr.api.Resource;
import com.unisound.iot.copy_asr.api.Result;
import com.unisound.iot.copy_asr.entity.CommonContext;
import com.unisound.iot.copy_asr.thread.ResourceCenter;

public class ProbeAction extends CommonContext implements ActionInterface{

    private Object lock = new Object();
    private Exception e;

    public ProbeAction(String id) {
        super(id);
    }

    public Result proccess() throws ProtocolException {
        try {
            System.out.println("[" + this.getId() + "]probe action:" + this.getAttribute("probeNum"));
            Resource res = ResourceCenter.getResource(this.getId());
            if (res == null) {
                throw new ProtocolException(615);
            }

            byte[] ret = res.probeSession(this);
            this.setOuput(ret);
        } catch (Exception var3) {
            this.e = var3;
            if (var3 instanceof ProtocolException) {
                throw (ProtocolException)var3;
            }
        }

        return null;
    }

    public Object getActionLock() {
        return this.lock;
    }

    public void getException() throws Exception {
        if (this.e != null) {
            throw this.e;
        }
    }
}
