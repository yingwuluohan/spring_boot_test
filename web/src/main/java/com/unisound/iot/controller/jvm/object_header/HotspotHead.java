package com.unisound.iot.controller.jvm.object_header;

import com.unisound.iot.common.mongo.User;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class HotspotHead {

    static HotspotHead h = new HotspotHead();

    private String name = new String("test");
    private int i ;

    public static void main(String[] args) {
        System.out.println( "t" );
         h.hashCode();

        //二进制到16进制的转换
        System.out.println("16进制:"+ Integer.toHexString( h.hashCode() ));
        //TODO 打印对象头信息
        System.out.println("Obj:"+ClassLayout.parseInstance( h ).toPrintable() );

        /**
         *  (loss due to the next object alignment) ：表示对齐填充
         */
        // h 是什么样的锁
        // sync锁住的是对象 ，就是修改h对象的头信息
        synchronized ( h ){
            System.out.println( "lock ------" );
            System.out.println(ClassLayout.parseInstance( h ).toPrintable() );

        }

        //TODO 查看对象外部信息：包括引用的对象： GraphLayout.parseInstance(obj).toPrintable()

        System.out.println( "外部引用："+  GraphLayout.parseInstance( h ).toPrintable() );

        //TODO 查看对象占用空间总大小：GraphLayout.parseInstance(obj).totalSize()

        System.out.println( GraphLayout.parseInstance( h ).totalSize() );

        //判断一个空对象占用多少字节？
        User user = new User();

        System.out.println("空对象：" + ClassLayout.parseInstance( user ).toPrintable() );




    }













}
