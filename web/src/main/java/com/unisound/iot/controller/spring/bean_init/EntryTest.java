package com.unisound.iot.controller.spring.bean_init;

import com.unisound.iot.controller.spring.mybatis.Appconfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Created by yingwuluohan on 2019/7/28.
 * @Company 北京云知声技术有限公司
 */
@EnableAspectJAutoProxy
public class EntryTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext( Appconfig.class);
        ac.getBean( "testServiceBean" );
        ac.getBean( "&testServiceBean" );
        //以上得到的两个bean是不同的




    }

}
