package com.ydh.redsheep.api.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
* 提供了一些简单的API来构造ZNode路径、提供创建和删除节点
* @author : yangdehong
* @date : 2020-03-15 17:37
*/
public class MyPaths {

    private static final String connectString = "172.16.165.222:2181";
    private static int timeout = 30*1000;

    private static CuratorFramework curatorFramework;

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(10*1000, 1000);

        curatorFramework = CuratorFrameworkFactory.newClient(connectString, timeout, timeout, retryPolicy);

        curatorFramework.start();

        String path = "/node_1";

//        String sub = ZKPaths.fixForNamespace(path, "sub");
//        String sub1 = ZKPaths.makePath(path, "sub");
//        System.out.println(sub+"=="+sub1);
        // 获取节点名字
//        String nodeFromPath = ZKPaths.getNodeFromPath(path);
//        System.out.println(nodeFromPath);
        // 节点和路径
//        ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode(path);
//        System.out.println(pathAndNode.getNode()+"=="+pathAndNode.getPath());

        ZooKeeper zooKeeper = curatorFramework.getZookeeperClient().getZooKeeper();
        ZKPaths.mkdirs(zooKeeper, path+"/child1");
        ZKPaths.mkdirs(zooKeeper, path+"/child2");
        List<String> sortedChildren = ZKPaths.getSortedChildren(zooKeeper, path);
        System.out.println(sortedChildren);
//        ZKPaths.deleteChildren(zooKeeper, path, false);

        Thread.sleep(60*1000);
    }

}
