package com.unisound.iot.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import scala.Long;

import javax.annotation.Resource;
import java.util.List;
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
    @Autowired
    private StringRedisTemplate geo;

    /**
     * 添加经纬度信息
     */
    public void geoAdd(String key , double centerX ,double centerY  , Long primaryKey){
        redisTemplate.opsForGeo() .add( key , new Point( centerX, centerY),primaryKey );
    }

    /**
     * 获取经纬度信息
     * @param key
     */
    public void geoGet(String key){
        List<Point> position = redisTemplate.opsForGeo().position( key , "北京", "上海");
        position.forEach(System.err::println);
    }


    /**
     * 根据给定的经纬度，返回半径不超过指定距离的元素
     * @param centerX  :经度
     * @param centerY  :维度
     */
    public void geoNearByXY( String key ,double centerX ,double centerY  ){
        Circle circle = new Circle(centerX, centerY , Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands
                .GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(3);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo() .radius(key , circle, args);
    }

    public void geoNearByPlace(String key ){
        Distance distance = new Distance(2,Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().
                includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo()
                .radius(key,"北京",distance,args);
        System.err.println(results);
    }
    /**
     * 两个地点之间的距离
     */
    public void geoDist(){
        Distance distance =  redisTemplate.opsForGeo() .distance("key", "北京", "上海", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.err.println(distance);
    }

    public void operZset(){
       ZSetOperations<String, Object> set =redisTemplate.opsForZSet();
//       set.add( "set1" , "1" ,121 );
//       set.add( "set2" , "2" ,122 );
//       set.add( "set3" , "3" ,123 );

    }


    public void operSet(){

        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add("set1","22");
        set.add("set1","33");
        set.add("set1","44");
        Set<Object> resultSet =redisTemplate.opsForSet().members("set1");
        System.out.println( resultSet.size() );
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
