package com.unisound.iot.controller.jdk.daili;

public class BirdThirdProxy implements Flyable {

    private Flyable flyable;

    public BirdThirdProxy(Flyable flyable){
        this.flyable = flyable;
    }

    @Override
    public void fly() {
        flyable.fly();
        System.out.println( "第三层继承" );
    }
}
