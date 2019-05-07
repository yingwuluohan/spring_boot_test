package com.unisound.iot.api.chart;

import java.io.IOException;

/**
 * Created by fn on 2017/5/22.
 */
public interface ChatRoomServerClientService {

    void initClientService() throws IOException;

    void writeContent(String content);
}
