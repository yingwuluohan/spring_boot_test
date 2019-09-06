package com.unisound.iot.controller.jdk.BlockingQueue_threads_enjoy;

import java.util.List;
import java.util.Set;

public interface Server extends Proccess, Comparable<Server>{
//资源状态
public final static int STATUS_STOP 	= 0;
public final static int STATUS_NORMAL 	= 1;
public final static int STATUS_CRASH 	= 2;
public final static int STATUS_ERROR	= 3;
//20161213: 增加STOPPING状态，使得服务状态更准确
public final static int STATUS_STOPPING = 4;
//20170509：thrift状态的服务不直接参与分配
public final static int STATUS_THRIFT	= 5;

        /**
         * 获取服务器信息
         * @return
         */
        ServerInfo serverInfo();

        /**
         *
         * @param info
         */
        void setServer(ServerInfo info);

        /**
         * 20170508: 获取子服务
         * @return
         */
        Set<Server> getChildServer();

        /**
         * 设置子服务
         * @param child
         */
        void setChildServer(Set<Server> child);

        List<String> getParent();

        void setParent(List<String> parent);

        /**
         *
         * @param status
         */
        void setStatus(int status);

        /**
         *
         * @return
         */
        int getStatus();

        /**
         *
         * @return
         */
        long getUpdate();

        /**
         *
         */
        void update();

        double getLoad();

        int getAbsoluteLoad();

        int incLoad();

        int decLoad();

        void close();

        /**
         * 20161212：设置服务优先级
         * @param priority
         */
        void setPriority(int priority);

        /**
         * 20161212：获取服务优先级
         * @return
         */
        int getPriority();

        /**
         * 20170522: 设置资源数
         * @param resNum
         */
        void setResNum(int resNum);

        /**
         * 获取资源数
         * @return
         */
        int getResuNum();
}


