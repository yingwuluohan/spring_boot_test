package com.unisound.iot.controller.rocketMq;

import org.apache.rocketmq.common.message.Message;
import java.util.Properties;

public class test {

//    public static void main(String[] args) {
//        System.out.println( "time:"+System.currentTimeMillis() );
//    }

    //67b287cf2e832c5edae174328a25dc93

    //email=1@test.cn&api_token=67b287cf2e832c5edae174328a25dc93&timestamp=1578452634&sign=

    public static void main(String[] args) {
        Properties properties = new Properties();
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put( "AccessKey","XXX");
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put( "SecretKey", "XXX");
        //设置发送超时时间，单位毫秒
        properties.setProperty("SendMsgTimeoutMillis", "3000");
        //  设置 TCP 接入域名，进入 MQ 控制台的生产者管理页面，在右侧操作栏单击获取接入点获取（此处以公共云生产环境为例）
        properties.put( "ONSAddr",
                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");

//        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
//        producer.start();

        //循环发送消息
        for (int i = 0; i < 100; i++) {
            Message msg = new Message( //
                    // Message 所属的 Topic
                    "smile_1",
                    // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                    "TagA",
                    // Message Body 可以是任何二进制形式的数据， MQ 不做任何干预，
                    // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                    "Hello MQ".getBytes());
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
            // 注意：不设置也不会影响消息正常收发
        }
    }
}
