package com.unisound.iot.service.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Created by yingwuluohan on 2018/12/17.
 * @Company fn
 */
@Service
public class RedisServiceImpl {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public void operSet(){

        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add("set1","22");
        set.add("set1","33");
        set.add("set1","44");
        Set<Object> resultSet =redisTemplate.opsForSet().members("set1");
    }
    public void operMap(){

        Map<Object, Object> resultMap= redisTemplate.opsForHash().entries("map1");
    }





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
