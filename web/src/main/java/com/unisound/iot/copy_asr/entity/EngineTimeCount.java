package com.unisound.iot.copy_asr.entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class EngineTimeCount {
    public static final String ENGINE_START_TIME = "engineStartTime";
    public static final String ENGINE_ACTION_TIME = "engineActionTime";
    public static final String ENGINE_END_TIME = "engineEndTime";
    public static final String ENGINE_ALL_TIME = "allTime";
    public static final long zero = 0L;
    private long tmp = 0L;
    private Map<String, Long> counter = new LinkedHashMap();
    private boolean ERROR = false;

    public EngineTimeCount() {
        this.init();
    }

    public boolean hasError() {
        return this.ERROR;
    }

    private void init() {
        this.counter.put("allTime", 0L);
        this.counter.put("engineStartTime", 0L);
        this.counter.put("engineActionTime", 0L);
        this.counter.put("engineEndTime", 0L);
    }

    public synchronized void countStart(String item) {
        if (this.counter.size() < 1) {
            this.init();
        }

        if (this.tmp != 0L) {
            this.ERROR = true;
        }

        this.tmp = System.currentTimeMillis();
    }

    public synchronized void countEnd(String item) {
        if (this.counter.size() < 1) {
            this.init();
        }

        this.tmp = System.currentTimeMillis() - this.tmp;
        if (this.counter.get(item) == null) {
            throw new RuntimeException("item is not supported");
        } else {
            this.counter.put(item, this.tmp + (Long)this.counter.get(item));
            this.counter.put("allTime", this.tmp + (Long)this.counter.get("allTime"));
            this.tmp = 0L;
        }
    }

    public Long getResult(String item) {
        if (this.counter.get(item) == null) {
            throw new RuntimeException("item is not supported");
        } else {
            return (Long)this.counter.get(item);
        }
    }

    public Map<String, Long> getCountMap() {
        if (this.hasError()) {
            this.counter.clear();
            return this.counter;
        } else {
            return this.counter;
        }
    }

    public String toString() {
        return this.counter.toString();
    }
}
