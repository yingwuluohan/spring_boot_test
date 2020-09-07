package com.unisound.iot.controller.event_call_back;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EventSourceObject {

    private String name;
    private Set<DoorEventListener> listenerSet;
    public EventSourceObject() {
        this.listenerSet = new HashSet<>();
        this.name = "defaultName";
    }
    public void addSourceListener(DoorEventListener doorEventListener) {
        listenerSet.add(doorEventListener);
    }
    // 通知所有
    public void notifies() {
        DoorEventListener doorEventListener = null;
        Iterator<DoorEventListener> iterator= this.listenerSet.iterator();
        while (iterator.hasNext()){
            doorEventListener=iterator.next();
            doorEventListener.doSomething(new DoorEvent(this));
        }
    }
    public void setName(String name) {
        //当setName时触发
        this.notifies();
        this.name = name;
    }

    //        调用测试类
    public static void main(String[] args){
        EventSourceObject object=new EventSourceObject();
        object.addSourceListener(new DoorEventListener() {
            @Override
            public void doSomething(DoorEvent doorEvent) {
                //把source可以传过来也可以不传，大家根据自己的情况操作，传过来这样我们就可以对源事件进行操作
                doorEvent.getSource();
                System.out.println("测试**************" + doorEvent.getSource() );
            }
        });
        object.setName("小高");
    }
}


