package com.unisound.iot.controller.jdk.serializable;

/**
 * @Created by yingwuluohan on 2020/1/22.
 */
public class JavaSeriali {


    public static void main(String[] args) {

    }


    public static byte[] intToByte( int value ){
        byte[] byteArray = new byte[ 4 ];
        //先写高位 ，即字节数组的末尾。向右移动3个字节
        byteArray [ 3 ] = ( byte)  ( (value & 0xFF000000)>> 3*8) ;

        return byteArray;

    }



}
