package com.ydh.redsheep.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @description: 节点增删改查
 * @author: yangdehong
 * @version: 2017/8/29.
 */
public class NodeApi {

    private static ZooKeeper zooKeeper;

    private static String connectString = "172.16.165.222:2181";
    private static int timeout = 30*1000;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(connectString, timeout, new MyWatcher(), false);

        System.out.println(zooKeeper.getState());

//        createNode();
        createNodeASync();
//        getChildrenNodes();
//        getChildrenNodesASync();
//        getNodeData();
//        getNodeDataASync();
//        deleteNode();
//        deleteNodeASync();
//        isExistsNode();
//        isExistsNodeASync();
//        updateNode();
//        updateNodeASync();

        Thread.sleep(10000);
    }

    private static void updateNodeASync(){
        zooKeeper.setData("/node_6", "234".getBytes(), -1, new IStatCallback(),"修改");
    }
    private static void updateNode(){
        try {
            Stat stat = zooKeeper.setData("/node_1", "123".getBytes(), -1);
            System.out.println("stat:"+stat);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步节点是否存在
     */
    private static void isExistsNodeASync(){
        zooKeeper.exists("/node_1", true, new IStateCallback(), "异步节点是否存在");
    }

    /**
     * 节点是否存在
     */
    private static void isExistsNode(){
        Stat stat = null;
        try {
            stat = zooKeeper.exists("/node_1", true);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println(stat);
    }

    /**
     * 异步删除节点
     */
    private static void deleteNodeASync(){

        zooKeeper.delete("/node_test_1", -1, new IVoidCallback(), "删除");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除节点
     */
    private static void deleteNode(){
        try {
            zooKeeper.delete("/node_test_1", -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步取节点数据
     */
    private static void getNodeDataASync(){
        zooKeeper.getData("/node_1", false, new IDataCallback(), "获取异步节点数据");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取节点数据
     */
    private static void getNodeData(){
        try {
            Stat stat = new Stat();
            byte[] bytes = zooKeeper.getData("/node_1", true, stat);
            System.out.println(new String(bytes,"UTF-8"));

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 异步获取子节点列表
     */
    private static void getChildrenNodesASync(){
        zooKeeper.getChildren("/", true, new IChildren2Callback(), "异步获取子节点列表");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取子节点列表
     */
    private static void getChildrenNodes(){
        try {
            // 第二个参数 是否对列表变化 进行观察
            List<String> children = zooKeeper.getChildren("/", true);
            System.out.println(children.toString());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步创建节点
     */
    private static void createNodeASync() {
        zooKeeper.create("/node_sync_1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT, new IStringCallback(), "创建");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建节点
     */
    private static void createNode(){
        /**
         * PERSISTENT(0, false, false), 持久节点
         * PERSISTENT_SEQUENTIAL(2, false, true), 持久、顺序节点
         * EPHEMERAL(1, true, false), 临时节点
         * EPHEMERAL_SEQUENTIAL(3, true, true); 临时、顺序节点
         */
        try {
            String path = zooKeeper.create("/node_1", "123".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(path);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
