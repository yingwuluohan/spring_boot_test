package com.unisound.iot.copy_asr.entity;

import com.unisound.iot.common.exception.ProtocolException;
import com.unisound.iot.copy_asr.api.ActionInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Actions extends CommonContext {
    public Actions(String id) {
        super(id);
    }

    public Object runLock = new Object();
    public AtomicInteger currNum = new AtomicInteger(0);
    private Map<Integer, ActionInterface> actions = new ConcurrentHashMap();
    private ActionEnd normalEndAction = null;
    private boolean isEnd = false;
    private int endNum = 100;
    private int status = 200;

    /**
     *
     * @param i
     * @return
     */
    public ActionInterface get(int i) {
        return (ActionInterface)this.actions.get(i);
    }


    public int addAction(ActionInterface action) throws ProtocolException {

        this.actions.put( 1 , action);
        Object var3 = this.runLock;
        synchronized(this.runLock) {
            this.runLock.notifyAll();
        }
        return 1;
    }


    public Object getRunLock() {
        return runLock;
    }

    public void setRunLock(Object runLock) {
        this.runLock = runLock;
    }

    public AtomicInteger getCurrNum() {
        return currNum;
    }

    public void setCurrNum(AtomicInteger currNum) {
        this.currNum = currNum;
    }

    public Map<Integer, ActionInterface> getActions() {
        return actions;
    }

    public void setActions(Map<Integer, ActionInterface> actions) {
        this.actions = actions;
    }

    public ActionEnd getNormalEndAction() {
        return normalEndAction;
    }

    public void setNormalEndAction(ActionEnd normalEndAction) {
        this.normalEndAction = normalEndAction;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
