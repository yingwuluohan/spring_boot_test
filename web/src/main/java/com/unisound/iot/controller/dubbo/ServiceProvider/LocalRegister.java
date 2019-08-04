package com.unisound.iot.controller.dubbo.ServiceProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company 北京云知声技术有限公司
 */
public class LocalRegister {

    private static Map< String ,Class > map = new HashMap<>();


    //本地注册方法存放映射关系,接口对应的实现类
    public static void regist( String interfaceName , Class implClass ){
        System.out.println( "本地注册方法存放映射关系");
        map.put( interfaceName , implClass );
    }

    public static Class get( String interfaceName){
        System.out.println( "" );
        return map.get( interfaceName );
    }





}
