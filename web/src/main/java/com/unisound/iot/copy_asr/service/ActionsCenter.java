package com.unisound.iot.copy_asr.service;


import com.unisound.iot.common.exception.ProtocolException;

import com.unisound.iot.copy_asr.entity.Actions;
import com.unisound.iot.copy_asr.thread.ActionExcutor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ActionsCenter {
    private static ConcurrentHashMap<String, Actions> actionCenter = new ConcurrentHashMap();
    private static Actions last = null;
    private static long lastTime = System.currentTimeMillis();

    public ActionsCenter() {
    }

    public static Actions getOrCreate(String id) {
        System.out.println("[" + id + "]get actions");
        Actions action = (Actions)actionCenter.get(id);
        if (action == null) {
            synchronized(id) {
                System.out.println("[" + id + "]create actions");
                action = new Actions(id);
                actionCenter.put(id, action);
                action.setAttribute("time", System.currentTimeMillis());
                ActionExcutor.excutor(action);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        action.setAttribute("time", System.currentTimeMillis());
        last = action;
        return action;
    }

    public static void removeActions(String id) throws ProtocolException {
        System.out.println("[" + id + "]remove action");
        actionCenter.remove(id);
    }

    public static int getActionNum() {
        return actionCenter.size();
    }

    public static void startCheck() {
        System.out.println( "startCheck :原定时任务 " );
    }

    public static void check() {
        System.out.println("check actions center: size=" + actionCenter.size());
        Map<String, Actions> copy = new HashMap(actionCenter);
        Set<String> rm = new HashSet();
        Iterator var3 = copy.keySet().iterator();

        String key;
        while(var3.hasNext()) {
            key = (String)var3.next();
            Actions tmp = (Actions)copy.get(key);
            if (tmp != null) {
                long time = (Long)tmp.getAttribute("time");
                if (System.currentTimeMillis() - time > 30000L) {
                    rm.add(key);
                }
            }
        }

        var3 = rm.iterator();

        while(var3.hasNext()) {
            key = (String)var3.next();
            actionCenter.remove(key);
        }

    }
}
