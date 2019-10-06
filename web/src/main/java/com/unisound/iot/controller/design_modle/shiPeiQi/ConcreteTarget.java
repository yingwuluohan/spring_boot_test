package com.unisound.iot.controller.design_modle.shiPeiQi;

/**
 * Created by Admin on 2017/10/24.
 */
// 具体目标类，只提供普通功能
class ConcreteTarget implements TargatInterface {
    public void request() {
        System.out.println("实现类调用 --普通类 具有 普通功能...");
    }
}
