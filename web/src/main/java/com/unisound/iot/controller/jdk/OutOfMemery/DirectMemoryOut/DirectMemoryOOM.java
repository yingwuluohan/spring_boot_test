package com.unisound.iot.controller.jdk.OutOfMemery.DirectMemoryOut;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * VM Args：-Xmx20M -XX:MaxDirectMemorySize=10M
 * 本机直接内存溢出

 DirectMemory容量可通过 -XX: MaxDirectMemorySize指定，若不指定则默认与Java堆最大值（-Xmx指定）相同。
 以下代码越过了DerictByteBuffer类，直接通过反射获取Unsafe 实例进行内存分配。
 虽然使用DerictByteBuffer分配内存也会抛出内存溢出异常，但它抛出异常时并没有真正向操作系统申请分配，
 而是通过计算得知内存无法分配，于是手动抛出异常，真正申请分配内存的方法是unsafe.allocateMemory()。

 由DirectMemory导致的内存溢出，明显的特征就是在 Heap Dump文件中不会看见明显的异常，
 如果发现OOM异常后的Dump文件很小，而程序中又直接或间接使用了NIO

 Exception in thread "main" java.lang.OutOfMemoryError
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
