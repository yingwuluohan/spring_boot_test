package com.unisound.iot.controller.jdk.OutOfMemery;

import java.nio.ByteBuffer;

/**
 * @Created by yingwuluohan on 2019/6/10.
 *
 */
public class DirectBufferMemoryOutof {




    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            ByteBuffer.allocateDirect(1024);
        }
        System.out.println("Done");

    }

}
