package com.unisound.iot.controller.design_modle.shiPeiQi;

/**
 * Created by Admin on 2017/10/24.
 */
// 适配器类，直接关联被适配类，同时实现标准接口
class Adapter implements TargatInterface{
    // 直接关联被适配类
    private Base base;

    // 可以通过构造函数传入具体需要适配的被适配类对象
    public Adapter (Base base) {
        this.base = base;
    }

    public void request() {
        // 这里是使用委托的方式完成特殊功能
        this.base.specificRequest();
    }
}
