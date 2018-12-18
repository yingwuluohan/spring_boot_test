package com.unisound.iot.controller.base;

import com.unisound.iot.common.constants.CommonConstants;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2018/9/30.
 */
public abstract class BaseController<T> {

    private static Logger logger = Logger.getLogger(BaseController.class);

    public Map<String, Object> exchangeResult(T t, int code) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (200 == code) {
            map.put("status", CommonConstants.CODE_200);
            if( null != t ){
                map.put("result", t);
            }else{
                map.put("result", "操作成功");
            }
        } else {
            map.put("status", CommonConstants.CODE_400);
            map.put("msg", t);
        }
        return map;
    }


    public Map<String, Object> exchangeResult(String errorMessage) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", CommonConstants.CODE_400);
        map.put("msg", errorMessage);
        return map;
    }


}
