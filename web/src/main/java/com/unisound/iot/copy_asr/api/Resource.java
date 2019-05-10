package com.unisound.iot.copy_asr.api;



public interface Resource {
    void relateEngine(Context var1) throws Exception;

    void startSession(Context var1) throws Exception;

    byte[] probeSession(Context var1) throws Exception;

    byte[] stopSession(Context var1) throws Exception;

    void releaseEngine() throws Exception;

    void personalEngine(Context var1) throws Exception;

    String getId();
}
