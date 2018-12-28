package com.unisound.iot.service.aio.handler;

import com.unisound.iot.service.aio.protcol.AioSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.CompletionHandler;

public class WriteCompletionHandler<T> implements CompletionHandler<Integer, AioSession<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteCompletionHandler.class);

    @Override
    public void completed(Integer result, AioSession<T> attachment) {

    }

    @Override
    public void failed(Throwable exc, AioSession<T> attachment) {

    }
}
