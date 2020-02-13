package com.unisound.iot.controller.elasticSearchUtil.condition.Operator;

import org.elasticsearch.client.Client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * es通过org.elasticsearch.node.NodeBuilder的build()或者node()方法实例化节点，build()创建节点而不启动，
 * 而node()方法等价于build().start()，即创建并启动。
 *
 * 首先实例化NodeBuilder，有两种方式，第一种是new，如下：
    NodeBuilder nodeBuilder = new NodeBuilder();
    第二种是使用NodeBuilder的工厂方法，NodeBuilder中有以下代码：
 */

public class Source {

    // 服务器地址
    private static String host = "127.0.0.1";
    // 端口
    private static int port = 9300;

    static TransportClient transportClient;
    static Settings settings = Settings.builder().put("cluster.name", "cluster_es").build();
    //测试方法
    public static void main(String[] args) throws UnknownHostException {
        try {


            transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(Source. host), Source.port));
            System.out.println("collection elasticsearch client:" + transportClient.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }finally {
            transportClient.close();
        }
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(Source.host), Source.port));

        System.out.println(client);

        client.close();
    }



}
