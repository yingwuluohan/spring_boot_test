package com.unisound.iot.service.aio;



public interface Function<F, T> {
    T apply(F var);
}
