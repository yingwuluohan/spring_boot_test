package com.unisound.iot.controller.Decorator_zhuangshi;

public class ConcreteDecorator extends Decorator {


    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void doSomething(){
        super.doSomething();
        this.doAnotherThing();
    }

    public void doAnotherThing(){

        System.out.println( "子类的B功能--" );
    }
}
