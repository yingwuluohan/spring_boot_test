package com.unisound.iot.controller.spring.constract;

/**
 * @Created by yingwuluohan on 2019/7/15.
 * @Company 北京云知声技术有限公司
 */
//@Service
public class SelfService {

    private String name ;
    private String value ;

    /** 构造1 */
    public SelfService(){

    }

    /** 构造2 */
    public SelfService(String name ,String value ){
        this.name = name;
        this.value = value;
    }
    /** 构造3 */
    public SelfService(String name ){
        this.name = name;
    }










}
