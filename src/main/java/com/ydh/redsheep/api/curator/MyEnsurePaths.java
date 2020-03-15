package com.ydh.redsheep.api.curator;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.curator.utils.EnsurePath;

public class MyEnsurePaths {

    private static final String connectString = "172.16.165.222:2181";
    private static int timeout = 30*1000;

    private static CuratorFramework curatorFramework;

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(10*1000, 1000);

        curatorFramework = CuratorFrameworkFactory.newClient(connectString, timeout, timeout, retryPolicy);

        curatorFramework.start();

        String path = "/node_1/child11";
        CuratorZookeeperClient zookeeperClient = curatorFramework.getZookeeperClient();


        EnsurePath ensurePath = new EnsurePath(path);
        // 如果不存在就创建
        ensurePath.ensure(zookeeperClient);
        EnsurePath ensurePath1 = curatorFramework.newNamespaceAwareEnsurePath("/node_1/c1");
        ensurePath1.ensure(zookeeperClient);

        Thread.sleep(60*1000);
    }

}
