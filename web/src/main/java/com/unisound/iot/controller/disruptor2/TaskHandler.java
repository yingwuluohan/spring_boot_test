package com.unisound.iot.controller.disruptor2;

import com.lmax.disruptor.EventHandler;

public class TaskHandler implements EventHandler< Task>  {
    @Override
    public void onEvent(Task task, long l, boolean b) throws Exception {

        System.out.println( "*************event" );
    }
}
