package com.unisound.iot.copy_asr.service;


import com.unisound.iot.common.exception.ProtocolException;
import com.unisound.iot.copy_asr.api.ActionInterface;
import com.unisound.iot.copy_asr.factory.NormalEndAction;
import com.unisound.iot.copy_asr.factory.ProbeAction;
import com.unisound.iot.copy_asr.factory.RequestAction;

import java.util.Iterator;
import java.util.Map;

public class ActionBuilder {


    public ActionBuilder() {
    }
    public static ActionInterface buildAction(  String id ) {
        if ("1".equals(id)) {
            return new RequestAction(id);
        } else if ("2".equals(id)) {
            return new ProbeAction(id);
        } else {
            return new NormalEndAction(id) ;
        }
    }
    public static ActionInterface build(Map<String, String> headers, byte[] data) throws ProtocolException {
        String requestId = (String)headers.get("requestID");
        String pnStr = (String)headers.get("probeNum");
        ActionInterface action = buildAction(  requestId);
        if (action == null) {
            throw new ProtocolException(617);
        } else {
            Iterator var6 = headers.keySet().iterator();

            while(var6.hasNext()) {
                String key = (String)var6.next();
                action.setAttribute(key, headers.get(key));
            }
            Integer pn = 0;
            try {
                pn = Integer.parseInt(pnStr);
            } catch (NumberFormatException var7) {
                throw new ProtocolException(610);
            }
            action.setAttribute("probeNum", pn);
            action.setInput(data);
            return action;
        }
    }


}
