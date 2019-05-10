package com.unisound.iot.copy_asr.service;

import com.unisound.iot.common.exception.ProtocolException;
import com.unisound.iot.copy_asr.api.ActionInterface;
import com.unisound.iot.copy_asr.api.Result;
import com.unisound.iot.copy_asr.entity.Actions;
import com.unisound.iot.copy_asr.entity.ProcessResult;

import java.util.Map;

public class CoreService {


    public static Result service(Map<String, String> headers, byte[] data) {
        ProcessResult result = new ProcessResult();
        try {
            ActionInterface action = ActionBuilder.build(headers, data);
            String id = action.getId();
            Actions actions = ActionsCenter.getOrCreate(id);


            int pn = actions.addAction(action);
            Object lock = actions.runLock;
            synchronized(actions.runLock) {
                actions.runLock.notifyAll();
            }

            if (actions.currNum.get() <= pn) {
                lock = action.getActionLock();
                System.out.println("[" + id + "]action probeNum=" + pn + ", currProbeNum=" + actions.currNum);
                long stime = System.currentTimeMillis();
                long etime = System.currentTimeMillis();

                while(actions.currNum.get() <= pn) {
                    synchronized(lock) {
                        try {
                            lock.wait(1000L);
                        } catch (InterruptedException var15) {
                        }
                    }

                    etime = System.currentTimeMillis();
                    if (etime - stime > 29000L) {
                        System.out.println( "etime - stime > 29000L 超时 *******" );
                        break;
                    }
                }
            }

            action.getException();
            result.setData((byte[])action.getOuput());

            try {
                lock = action.getAttribute("RequestStatus");
            } catch (Exception var14) {
                lock = "END";
            }

            result.setItem("RequestStatus", lock);
            result.setItem("requestID", action.getId());
            result.setItem("CoreServerLog", action.getAttribute("CoreServerLog"));
            result.setItem("ContentType", action.getAttribute("ContentType"));
            result.setItem("http_return_code", 200);
            result.setItem("STA", action.getAttribute("STA"));
        } catch (ProtocolException var18) {
        } catch (Exception var19) {
            if (var19 instanceof NullPointerException) {
            }
        }
        return result;
    }











}
