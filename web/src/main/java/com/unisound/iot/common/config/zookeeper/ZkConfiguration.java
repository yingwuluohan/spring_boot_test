package com.unisound.iot.common.config.zookeeper;

/**
 * @Created by yingwuluohan on 2019/4/9.
 * @Company 北京云知声技术有限公司
 */

//@Configuration
//public class ZkConfiguration {
//    @Value("${zookeeper.server}")
//    private String zookeeperServer;
//    @Value(("${zookeeper.sessionTimeoutMs}"))
//    private int sessionTimeoutMs;
//    @Value("${zookeeper.connectionTimeoutMs}")
//    private int connectionTimeoutMs;
//    @Value("${zookeeper.maxRetries}")
//    private int maxRetries;
//    @Value("${zookeeper.baseSleepTimeMs}")
//    private int baseSleepTimeMs;
//
//    @Bean(initMethod = "init", destroyMethod = "stop")
//    public ZkClient zkClient() {
//        ZkClient zkClient = new ZkClient();
//        zkClient.setZookeeperServer(zookeeperServer);
//        zkClient.setSessionTimeoutMs(sessionTimeoutMs);
//        zkClient.setConnectionTimeoutMs(connectionTimeoutMs);
//        zkClient.setMaxRetries(maxRetries);
//        zkClient.setBaseSleepTimeMs(baseSleepTimeMs);
//        return zkClient;
//    }
//
//}
