package com.unisound.iot.controller.netty.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *
 * ByteBuf共有三种模式:
 * 堆缓冲区模式(Heap Buffer):每次数据与I/O进行传输时，都需要将数据拷贝到直接缓冲区
 * 直接缓冲区模式(Direct Buffer):相对于堆缓冲区而言，Direct Buffer分配内存空间和释放更为昂贵
 * 复合缓冲区模式(Composite Buffer)
 *
 *
 */
public class ByteBufLearn {

    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /** 创建Java堆缓冲区*/
    private ByteBuf heapBuf = Unpooled.buffer();
    /** 直接缓冲区模式(Direct Buffer) */
    private ByteBuf directBuf = Unpooled.directBuffer();
    /**复合缓冲区模式(Composite Buffer) */
    private CompositeByteBuf messageBuf = Unpooled.compositeBuffer();




    public static void directBuffer() {
        ByteBuf directBuf = Unpooled.directBuffer();
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
        }
    }

    public static void heapBuffer() {
        // 创建Java堆缓冲区
        ByteBuf heapBuf = Unpooled.buffer();
        if (heapBuf.hasArray()) { // 是数组支撑
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
        }
    }









}
