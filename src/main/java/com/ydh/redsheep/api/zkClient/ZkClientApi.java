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

    private static final String connectString = "172.16.165.222:2181";
    private static int timeout = 30*1000;

    private static ZkClient zkClient;

    public static void main(String[] args) throws Exception {

        // BytesPushThroughSerializer不是对象的时候，使用byte序列
        zkClient = new ZkClient(connectString, timeout, timeout, new SerializableSerializer());
        System.out.println(zkClient);
        List<String> strings = zkClient.subscribeChildChanges("/node_zkClinet_1", new ZkClientChildListener());
        System.out.println(strings);
        zkClient.subscribeStateChanges(new ZkClientStateListener());
        zkClient.subscribeDataChanges("/node_zkClinet_1", new ZkClientDataListener());


//        createNode();
        updateData();
//        deleteNode();
//        getNode();
//        getChildren();
//        change();


        Thread.sleep(10000);

    }

    private static void updateData(){
        zkClient.writeData("/node_zkClinet_1", new User(2,"yangdehong"));
    }

    private static void getChildren(){
        List<String> children = zkClient.getChildren("/");
        System.out.println(children);
    }

    private static void getNode(){
        Stat stat = new Stat();
        User user = zkClient.readData("/node_zkClinet_1", stat);
        System.out.println(user);
    }

    private static void deleteNode(){
        // 其他的creatxxx都是类似的
        zkClient.delete("/node_zkClinet_1");
    }

    private static void createNode(){
        // 其他的creatxxx都是类似的
        zkClient.createPersistentSequential("/node_zkClinet_1/children", new User(1,"ydh"));
    }
}
