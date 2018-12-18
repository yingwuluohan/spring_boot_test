package com.unisound.iot.common.exception;

/**
 *
 * Created by Admin on 2017/11/15.
 */
public class RpcApiException extends Exception {
    private static final long serialVersionUID = 1L;

    public RpcApiException() {
    }

    public RpcApiException(String s) {
        super(s);
    }

    public RpcApiException(Throwable cause) {
        super(cause);
    }

    public RpcApiException(String s, Throwable cause) {
        super(s, cause);
    }
}
