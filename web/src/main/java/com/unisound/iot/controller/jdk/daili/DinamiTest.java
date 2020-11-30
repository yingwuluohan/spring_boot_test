package com.unisound.iot.controller.jdk.daili;

public class DinamiTest {

    public static void main(String[] args) {
        Bird bird = new Bird();
        BirdLogProxy p1 = new BirdLogProxy(bird);
        BirdTimeProxy p2 = new BirdTimeProxy(p1);
        BirdThirdProxy p3 = new BirdThirdProxy( p2 );
        p3.fly();
    }

}
