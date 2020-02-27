package com.unisound.iot.controller.zookeeper;

import com.unisound.iot.common.zookeeper.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Created by yingwuluohan on 2019/4/9.
 * @Company fn
 */
@RestController
@RequestMapping("zk/")
public class ZkClientController {


//    @Autowired
    private ZkClient zkClient;

    //zk/add
    @SuppressWarnings("unchecked")
    @RequestMapping(value="add",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String createZkInfo(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param){
        CuratorFramework client =  zkClient.getClient();

        // 创建节点
        String nodePath = "/node_1/boot_node";  // 节点路径
        String nodePath1 = "/node_1/boot_node1";  // 节点路径
        byte[] data = "this is a test data".getBytes();  // 节点数据
        byte[] data1 = "secend node is  data".getBytes();  // 节点数据
        try {
            String result = client.create().creatingParentsIfNeeded()  // 创建父节点，也就是会递归创建
                    .withMode(CreateMode.PERSISTENT)  // 节点类型
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE) // 节点的acl权限
                    .forPath(nodePath, data);

            client.create().creatingParentsIfNeeded()  // 创建父节点，也就是会递归创建
                    .withMode(CreateMode.PERSISTENT)  // 节点类型
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE) // 节点的acl权限
                    .forPath(nodePath1, data1);
            System.out.println(result + "节点，创建成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( "参数:" + param );
        //itemService.findItemDetail( 232L );
        return "1111";

    }

    /**
     * 查询节点的列表信息
     * @param request
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="find_list",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List findListZkInfo(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {
        List<String > info = null;
        CuratorFramework client = zkClient.getClient();
        // 节点路径
        String nodePath = "/node_1";

        // 获取子节点列表
        List<String> childNodes = null;
        try {
            childNodes = client.getChildren().forPath(nodePath);
            System.out.println(nodePath + " 节点下的子节点列表：");
            for (String childNode : childNodes) {
                System.out.println(childNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return info;
    }

        /**
         * 查询节点
         * @param request
         * @param param
         * @return
         */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="find",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String findZkInfo(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {
        String info=null;
        CuratorFramework client =  zkClient.getClient();
        // 节点路径
        String nodePath = "/node_1/boot_node";
        // 读取节点数据
        Stat stat = new Stat();
        byte[] nodeData = new byte[0];
        try {
            nodeData = client.getData().storingStatIn(stat).forPath(nodePath);
            System.out.println("节点 " + nodePath + " 的数据为：" + new String(nodeData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 查询节点是否存在
     * @param request
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="exit",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String findExitZkNode(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {
        String info = null;
        CuratorFramework client = zkClient.getClient();
        // 节点路径
        String nodePath = "/node_1/boot_node";
        // 查询某个节点是否存在，存在就会返回该节点的状态信息，如果不存在的话则返回空
        Stat statExist = null;
        try {
            statExist = client.checkExists().forPath(nodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(statExist == null) {
            System.out.println(nodePath + " 节点不存在");
        } else {
            System.out.println(nodePath + " 节点存在");
        }
        return null;
    }

        /**
         * 更新节点
         * @param request
         * @param param
         * @return
         */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="update",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateZkInfo(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {
        String info = null;
        CuratorFramework client = zkClient.getClient();
        // 节点路径
        String nodePath = "/node_1/boot_node";
        // 更新节点数据
        byte[] newData = "this is a new data".getBytes();
        Stat resultStat = null;  // 需要修改的节点路径以及新数据
        try {
            resultStat = client.setData().withVersion(0)  // 指定数据版本
                    .forPath(nodePath, newData);
            System.out.println("更新节点数据成功，新的数据版本为：" + resultStat.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    /**
     * 删除节点
     * @param request
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="del",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String deleteZkInfo(HttpServletRequest request, @RequestParam(value = "param", required = false , defaultValue = "0")String param) {
        String info = null;
        CuratorFramework client = zkClient.getClient();
        // 节点路径
        String nodePath = "/node_1/boot_node";
        // 删除节点数据
        Stat resultStat = new Stat();  // 需要修改的节点路径以及新数据
        try {
            client.delete()
            .guaranteed()  // 如果删除失败，那么在后端还是会继续删除，直到成功
            .deletingChildrenIfNeeded()  // 子节点也一并删除，也就是会递归删除
//            .withVersion(resultStat.getVersion()) // 如果版本不一致会导致删除失败
            .forPath(nodePath);
            System.out.println("更新节点数据成功，新的数据版本为：" + resultStat.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }




}
