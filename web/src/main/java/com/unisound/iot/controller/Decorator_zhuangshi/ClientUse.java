package com.unisound.iot.controller.Decorator_zhuangshi;

public class ClientUse {

    public static void main(String[] args) {
        Component component = new ConcreteDecorator2( new ConcreteDecorator( new ConcreteComponent() ) );
        Component component2 =   new ConcreteDecorator( new ConcreteComponent() ) ;

        component.doSomething();
        System.out.println( "------------------------");
        component2.doSomething();
    }
}
