package com.unisound.iot.controller.event_call_back;

import java.util.EventListener;

public interface DoorEventListener extends EventListener {

    /**
     *
     * @param e
     */
    public void doSomething(DoorEvent e);
}
