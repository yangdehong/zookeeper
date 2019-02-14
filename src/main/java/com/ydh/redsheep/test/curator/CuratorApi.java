package com.ydh.redsheep.test.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/30.
 */
public class CuratorApi {

    private static final String connectString = "192.168.2.144:2181";

    public static void main(String[] args) throws Exception{

        // 重试连接最大3次，每次时间间隔变大，第一次是1s
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 最大重试5次，每次1s
//        RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
        // 最大重拾时间10s，每次间隔1s
        RetryPolicy retryPolicy = new RetryUntilElapsed(100000, 1000);


        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectString,
                5000, 5000, retryPolicy);

//        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(connectString)
//                .sessionTimeoutMs(5000).connectionTimeoutMs(5000)
//                .retryPolicy(retryPolicy).build();

        curatorFramework.start();

        createNode(curatorFramework);

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void createNode(CuratorFramework curatorFramework) throws Exception{
        String path = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/curator_java_1", "123".getBytes());
        System.out.println(path);
//        // 异步操作
//        curatorFramework.create().inBackground((CuratorFramework client, CuratorEvent event) -> {
////            CuratorEventType.CREATE;
//
//        });
    }

    // 监听
    private static void nodeChange(CuratorFramework curatorFramework) throws Exception{
        final NodeCache cache = new NodeCache(curatorFramework, "/");
        cache.start();
        cache.getListenable().addListener(() -> {
            byte[] ret = cache.getCurrentData().getData();
            System.out.println(new String(ret)+"=======");
        });
    }

}
