package com.unisound.iot.controller.bulong_filter;

import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Created by yingwuluohan on 2020/3/6.
 *
 * redis 布隆过滤器
 */
@Data
//@ConfigurationProperties( "bloom.filter" )
public class RedisBloomFilter {


    //预计插入量
    private long expextedInster = 1000;

    //可接受的错误率
    private double fpp = 0.001f;

    //bit数组长度
    private long numBits;

    //hash函数 数量
    private int numHashFunctions;

    private RedisTemplate redisTemplate;


    @PostConstruct
    public void init(){
        this.numBits = optimaNumOfBits( expextedInster , fpp );
        this.numHashFunctions = optimaNumOfHashFunctions( expextedInster ,numBits);
    }
    //计算hash函数个数
    private int optimaNumOfHashFunctions(long expextedInster , long numBits){
        return Math.max( 1 , ( int )Math.round( (double)numBits/expextedInster * Math.log( 2 )));
    }
    
    //计算bit数组长度
    private long optimaNumOfBits( long n , Double p){
        if( p == 0 ){
            p = Double.MIN_VALUE;
        }
        return (long)( -n * Math.log( p ) / ( Math.log( 2 ) * Math.log( 2 )));
    }
    //判断keys 是否存在于集合，是则返回true ，否则false
    
    public boolean isExist( String key ){
        long[] indexs = getIndex( key );
        List list = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for( long index : indexs ){
                    redisConnection.getBit( "bf:taibai".getBytes(),index  );
                }
                redisConnection.close();
                return null;
            }





        });
        return !list.contains( false );
    }
    
    
    //根据key 获取bigmap 下标
    public long[] getIndex( String key ){
        long hash1 = 1L;//hash( key );
        long hash2 = hash1 >>> 16;
        long[] result = new long[ numHashFunctions ];
        for ( int i = 0; i < numHashFunctions;i++){
            long combinedHash = hash1 + i * hash2;
            if( combinedHash < 0 ){
                combinedHash = combinedHash;
            }

            result[ i ] = combinedHash % numBits;
        }
        return result;

    }


























}
