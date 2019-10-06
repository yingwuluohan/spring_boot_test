package com.unisound.iot.controller.design_modle.shiPeiQi;

/**
 * Created by Admin on 2017/10/24.
 */
// 测试类
public class Client {
    public static void main(String[] args) {
        // 使用普通功能类
        TargatInterface concreteTarget = new ConcreteTarget();
        concreteTarget.request();

        // 使用特殊功能类，即适配类，
        // 需要先创建一个被适配类的对象作为参数
        TargatInterface adapter = new Adapter(new Base());
        adapter.request();
    }
}