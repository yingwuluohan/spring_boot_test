package com.unisound.iot.controller.Decorator_zhuangshi;

public class ConcreteDecorator2 extends Decorator {
    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething(){
        super.doSomething();
        this.doAnotherthing();
    }

    public void doAnotherthing(){
        System.out.println( "ConcreteDecorator2功能B " );
    }




}
