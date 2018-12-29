package com.unisound.iot.core.message;

import com.unisound.iot.common.modle.message.Message;

public interface ProductMessage<T> {

    void productMessage( Message<T> message );

}
