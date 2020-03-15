package com.ydh.redsheep.api.curator;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class Testing {
    private static final String connectString = "172.16.165.222:2181";
    private static int timeout = 30*1000;

    private static CuratorFramework curatorFramework;

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(10*1000, 1000);

        curatorFramework = CuratorFrameworkFactory.newClient(connectString, timeout, timeout, retryPolicy);

        curatorFramework.start();

        String path = "/node_1/child11";
        CuratorZookeeperClient zookeeperClient = curatorFramework.getZookeeperClient();

        // TODO

        Thread.sleep(60*1000);
    }

}
