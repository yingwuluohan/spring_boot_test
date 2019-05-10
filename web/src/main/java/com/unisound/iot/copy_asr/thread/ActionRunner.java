package com.unisound.iot.copy_asr.thread;

import com.unisound.iot.copy_asr.entity.Actions;

public class ActionRunner implements Runnable {
    private Actions actions = null;
    private long waitTime = 2000L;
    private long requestTimeOut = 8000L;
    private int waitInterval = 300;
    private Object runLock = null;

    public ActionRunner(Actions actions, long waitTime) {
        this.actions = actions;
        this.runLock = actions.getRunLock();
        this.setWaitTime(waitTime);
    }

    public void run() {
        System.out.println( "this.actions.get(0): " + actions.get(0));
        synchronized(this.runLock) {
            System.out.println("[" + this.actions.getId() + "]start action is null, wait max 10s");
            if (this.actions.get(0) == null) {
                try {
                    System.out.println( "ActionRunner 同步块 释放锁" );

                    this.runLock.wait(10000L);

                    System.out.println( "ActionRunner 同步块获得锁 继续执行******************");
                    System.out.println("runlock -- wait: 继续执行******************");
                    System.out.println("runlock -- wait: 继续执行******************");
                    System.out.println("runlock -- wait: 继续执行******************");
                    System.out.println("runlock -- wait: 继续执行******************");
                    System.out.println("runlock -- wait: 继续执行******************");
                } catch (InterruptedException var3) {
                    var3.printStackTrace();
                }
            }
        }

        this.proccess();
    }

    private void proccess() {
        System.out.println( "ActionRunner 执行 *****************" );

    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getRequestTimeOut() {
        return requestTimeOut;
    }

    public void setRequestTimeOut(long requestTimeOut) {
        this.requestTimeOut = requestTimeOut;
    }

    public int getWaitInterval() {
        return waitInterval;
    }

    public void setWaitInterval(int waitInterval) {
        this.waitInterval = waitInterval;
    }

    public Object getRunLock() {
        return runLock;
    }

    public void setRunLock(Object runLock) {
        this.runLock = runLock;
    }
}
