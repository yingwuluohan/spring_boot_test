package com.unisound.iot.controller.design_modle.guanChaZhe;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yingwuluohan on 2017/9/4.
 */
public class SimpleObserver implements Observer
{
    public SimpleObserver(SimpleObservable simpleObservable){
        simpleObservable.addObserver(this );
    }

    public void update(Observable observable , Object data){  // data为任意对象，用于传递参数
        SimpleObservable s = ( SimpleObservable ) observable;
        System.out.println( "Data has changed to:" + s.getData());
    }
}
