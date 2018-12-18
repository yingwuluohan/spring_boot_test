package com.unisound.iot.common.exception;

import java.util.Arrays;


public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static String format(String format, Object... args) {
        format = format.replaceAll("\\{\\}", "%s");
        if (isLastOneThrowable(args)) {
            return String.format(format, Arrays.copyOf(args, args.length - 1));
        } else {
            return String.format(format, args);
        }
    }

    private static boolean isLastOneThrowable(Object... args) {
        return args.length > 0 && args[args.length - 1] instanceof Throwable;
    }

    /**
     * 生成用户侧错误提示
     * @param message 必须是用户可读的错误信息
     */
    public BusinessException(String message, Object... args) {
        super(format(message, args));
        if (isLastOneThrowable(args)) {
            super.initCause((Throwable) args[args.length - 1]);
        }
    }
}
