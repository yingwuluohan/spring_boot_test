package com.unisound.iot.service.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Created by yingwuluohan on 2018/12/17.
 * @Company fn
 */
@Service
public class RedisServiceImpl {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, Object value) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    public void setValue(String key, Object value, long timeout) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value, timeout, TimeUnit.SECONDS);
    }

    public Object getValue(String key) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        Object obj = vo.get(key);
        return obj;
    }

    public void delValue(String key) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.getOperations().delete(key);
    }
}
