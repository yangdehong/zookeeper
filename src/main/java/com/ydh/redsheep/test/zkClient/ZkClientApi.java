package com.ydh.redsheep.test.zkClient;

import com.ydh.redsheep.test.zkClient.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/30.
 */
public class ZkClientApi {

    private static final String connectString = "192.168.2.106:2181";

    public static void main(String[] args) {

        // BytesPushThroughSerializer不是对象的时候，使用byte序列
        ZkClient zkClient = new ZkClient(connectString, 10000, 10000,
                new SerializableSerializer());
        System.out.println(zkClient);
        System.out.println("conneted ok!");

//        createNode(zkClient);
//        getNode(zkClient);
        change(zkClient);

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 监听,节点修改的，还有其他的类是的
     * @param zkClient
     */
    private static void change(ZkClient zkClient){
        zkClient.subscribeChildChanges("/", (String s, List<String> list) -> {
            System.out.println("=====");
            System.out.println(list.toString());
            System.out.println("=====");
        });
    }

    private static void getNode(ZkClient zkClient){
        Stat stat = new Stat();
        User user = zkClient.readData("/node_zkClinet_1", stat);
        System.out.println(user.getId());
        System.out.println(stat);
    }

    private static void createNode(ZkClient zkClient){
        String path = zkClient.create("/node_zkClinet_1", new User(1,"volat"),
                CreateMode.PERSISTENT);
        System.out.println("path="+path);
    }
}
