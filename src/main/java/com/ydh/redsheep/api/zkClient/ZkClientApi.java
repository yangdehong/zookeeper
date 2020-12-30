package com.ydh.redsheep.api.zkClient;

import com.ydh.redsheep.api.zkClient.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/30.
 */
public class ZkClientApi {

    private static final String connectString = "172.16.131.6:2181";
    private static int timeout = 30*1000;

    private static ZkClient zkClient;

    public static void main(String[] args) throws Exception {

        // BytesPushThroughSerializer不是对象的时候，使用byte序列
        zkClient = new ZkClient(connectString, timeout, timeout, new SerializableSerializer());
        System.out.println(zkClient);
        // 监听子节点变化
        List<String> strings = zkClient.subscribeChildChanges("/node_zkClient_1", new ZkClientChildListener());
        System.out.println(strings);
        // 监听状态变化
        zkClient.subscribeStateChanges(new ZkClientStateListener());
        // 监听节点数据变化
        zkClient.subscribeDataChanges("/node_zkClient_1", new ZkClientDataListener());

        createNode();
//        updateData();
//        deleteNode();
//        getNode();
//        getChildren();
//        change();


        Thread.sleep(10000);

    }

    private static void updateData(){
        zkClient.writeData("/node_zkClient_1", new User(2,"yangdehong"));
    }

    private static void getChildren(){
        List<String> children = zkClient.getChildren("/");
        System.out.println(children);
    }

    private static void getNode(){
        Stat stat = new Stat();
        User user = zkClient.readData("/node_zkClient_1", stat);
        System.out.println(user);
    }

    private static void deleteNode(){
        // 其他的creatxxx都是类似的
        zkClient.delete("/node_zkClient_1");
        // 递归删除，删除指定节点和所有子节点
//        zkClient.deleteRecursive()
    }

    private static void createNode(){
        // 其他的creatxxx都是类似的，true是递归创建，如果要递归删除只能这样
        zkClient.createPersistent("/node_zkClient_1/children2", true);
//        zkClient.createPersistent("/node_zkClient_2", new User(1,"ydh"));
    }
}
