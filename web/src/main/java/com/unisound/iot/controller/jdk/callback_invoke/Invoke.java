package com.unisound.iot.controller.jdk.callback_invoke;

import com.unisound.iot.controller.jdk.lambad.Modle.Modle;

/**
 * @Created by yingwuluohan on 2019/7/16.
 * @Company 北京云知声技术有限公司
 */
public class Invoke {


    public static void lambadInnerClass(InvokeListenerInterface lisener  ){
        System.out.println( "无参 " );


    }
    public static void lambadInnerClass2(int num , InvokeListenerInterface lisener  ){
        System.out.println( );


    }

    public static void main(String[] args) {
        lambadInnerClass( info -> {

            System.out.println( "函数式编程"+info.getName() );
        } );

        lambadInnerClass2( 1 , test->{

        });

        InvokeListenerInterface inter = new InvokeListenerInterface() {
//            @Override
//            public void onEvent(int num) {
//                System.out.println( "事件：" + num );
//            }
//
//            @Override
//            public String callBackInfo(String info) {
//
//                System.out.println( "回调信息："+ info );
//                return info;
//            }

            public void find( Modle id ){

            }
        };

        inter.find(  new Modle());





    }




}
