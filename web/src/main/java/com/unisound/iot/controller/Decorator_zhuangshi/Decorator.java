package com.unisound.iot.controller.Decorator_zhuangshi;

public class Decorator implements Component {


    /**
     * 装饰对象和真实对象有相同的接口，
     * 这样客户端就可以和真实对象相同的方式和装饰对象交互。
     */
    //TODO 重点：装饰对象包含一个真实对象的引用
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
