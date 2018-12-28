package com.unisound.iot.service.aio;

import com.unisound.iot.service.aio.protcol.AioSession;

import java.nio.ByteBuffer;

public interface Protocol <T> {
    /**
     * 对于从Socket流中获取到的数据采用当前Protocol的实现类协议进行解析。
     * 实现的解码方法要尽可能读取readBuffer已有的数据。
     * 若不及时解析并最终导致readBuffer无可用空间（即readBuffer.remaining==0），
     *  socket会反复触发decode方法形成类似死循环的效果。
     *
     * @param readBuffer 待处理的读buffer
     * @param session    本次需要解码的session
     * @return 本次解码成功后封装的业务消息对象, 返回null则表示解码未完成
     */
    T decode(final ByteBuffer readBuffer, AioSession<T> session);
}
