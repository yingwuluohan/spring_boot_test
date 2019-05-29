package com.unisound.iot.controller.jdk.OutOfMemery;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 栈溢出测试
 *
 * @Created by yingwuluohan on 2019/5/22.
 * @Company 北京云知声技术有限公司
 */
@RestController
@RequestMapping("stack/")
public class StackOutOfController {

    private int num=0;

    //memory/v1
    @ResponseBody
    @RequestMapping(value = "v1" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String findAlbumList( ){

        System.out.println( "********** stack begin *********" );
        while(true ){
            num++;
            if(num < 0 ){
                break;
            }
        }
        return "ok";
    }
}
