package com.unisound.iot.service.aio;

import com.unisound.iot.service.aio.protcol.AioSession;

public interface NetMonitor<T> {

    /**
     * 监控触发本次读回调Session的已读数据字节数
     *
     * @param session  当前执行read的AioSession对象
     * @param readSize 已读数据长度
     */
    void readMonitor(AioSession<T> session, int readSize);

    /**
     * 监控触发本次写回调session的已写数据字节数
     *
     * @param session   本次执行write回调的AIOSession对象
     * @param writeSize 本次输出的数据长度
     */
    void writeMonitor(AioSession<T> session, int writeSize);
}
