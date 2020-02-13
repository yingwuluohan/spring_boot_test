package com.unisound.iot.controller.spring;

import org.apache.log4j.Logger;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@EnableRetry
@Service
public class Retry {

    Logger logger = Logger.getLogger(getClass());



    @Retryable(value = Exception.class)
    public String doDemo(String testStr) throws Exception {
        String a = "a";
        if(a.equals("a")){
            throw new Exception();
        }
        return a;
    }

    @Recover
    public void recover(Exception e) {
        logger.error("重试机制3次完成，结果依然异常..");
    }
}
