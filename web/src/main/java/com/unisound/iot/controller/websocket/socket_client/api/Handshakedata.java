package com.unisound.iot.controller.websocket.socket_client.api;

import java.util.Iterator;

public interface Handshakedata {
    Iterator<String> iterateHttpFields();

    String getFieldValue(String var1);

    boolean hasFieldValue(String var1);

    byte[] getContent();
}
