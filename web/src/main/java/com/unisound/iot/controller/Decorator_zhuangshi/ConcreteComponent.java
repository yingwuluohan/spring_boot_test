package com.unisound.iot.controller.Decorator_zhuangshi;

public class ConcreteComponent implements Component {

    //具体的构建角色

    @Override
    public void doSomething() {
        System.out.println( "实现类的功能A" );
    }
}
