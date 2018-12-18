package com.unisound.iot.controller.redis_test;

import com.unisound.iot.service.cache.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Created by yingwuluohan on 2018/12/17.
 * @Company 北京云知声技术有限公司
 */

@RestController
@RequestMapping("redis/")
public class CacheTest {

    @Autowired
    private RedisServiceImpl redisUtil;
    //redis/v1/cache/12345
    @SuppressWarnings("unchecked")
    @RequestMapping(value="v1/cache/{id}",method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
    public String getMsgHistory(HttpServletRequest request, @PathVariable(name="id") long id){

        Object obj = redisUtil.getValue( "testsdf45" );
        if( null == obj ){
            redisUtil.setValue( "testsdf45" , id );
        }


        System.out.println( "参数:" + id );
        //itemService.findItemDetail( 232L );
        return "1111";

    }






















}

















