package com.unisound.iot.common.exception;

public class ProtocolException extends Exception {
    private static final long serialVersionUID = 1L;
    private int errorCode = 0;

    public ProtocolException(int errorCode) {
        super("errorCode: " + errorCode);
        this.errorCode = errorCode;
    }

    public ProtocolException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
