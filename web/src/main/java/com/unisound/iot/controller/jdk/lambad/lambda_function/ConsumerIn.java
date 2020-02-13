package com.unisound.iot.controller.jdk.lambad.lambda_function;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class ConsumerIn {
    public static <R> Optional<R> getAndPrintLogIfException(SupplierWithThrowable<R> supplier,  Logger logger,
                                                            String errorMsgFormatterIfException,  Object... args) {
        Optional optional = Optional.ofNullable(
                getAndCatch( supplier,  e -> {
                            if (Objects.isNull(args)) {
                                System.out.println( "1111" );
                                logger.error(errorMsgFormatterIfException, e);
                            } else {
                                System.out.println( "222" );
                                logger.error(MessageFormatter.arrayFormat(errorMsgFormatterIfException, args).getMessage(), e);
                            }
                            return null;
                        }));
        return optional;
    }
    /**
     * 获取时如果有异常进行捕获
     * @param supplier                  代码块供应商
     * @param ex 异常代码块
     */
    public static <R> R getAndCatch(SupplierWithThrowable<R> supplier,  Function<Throwable, R> ex) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            return ex.apply(e);
        }
    }

    public static void main(String[] args) {
        Optional<Object> statusValue =  getAndPrintLogIfException( () -> null , log, "Redis get value error!");
    }















}
