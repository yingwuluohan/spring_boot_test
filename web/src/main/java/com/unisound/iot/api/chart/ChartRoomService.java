package com.unisound.iot.api.chart;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by fn on 2017/5/19.
 */
public interface ChartRoomService {

    public void init(int port);
    public String  readMsg(SelectionKey key) throws IOException;
    public void writeMsg(SelectionKey key) throws IOException;


}
