package com.ydh.redsheep.api.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/30.
 */
public class CuratorApi {

    private static final String connectString = "172.16.165.222:2181";
    private static int timeout = 30*1000;

    private static CuratorFramework curatorFramework;

    public static void main(String[] args) throws Exception{

        // 重试连接最大3次，每次时间间隔变大，第一次是1s
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 最大重试5次，每次1s
//        RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
        // 最大重拾时间10s，每次间隔1s
        RetryPolicy retryPolicy = new RetryUntilElapsed(10*1000, 1000);

        curatorFramework = CuratorFrameworkFactory.newClient(connectString, timeout, timeout, retryPolicy);

//        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(connectString)
//                .sessionTimeoutMs(timeout).connectionTimeoutMs(timeout)
//                .retryPolicy(retryPolicy).build();

        curatorFramework.start();

//        createNode();
//        deleteNode();
//        getNode();
//        setData();
//        sync();
        listener();

        Thread.sleep(60*1000);
    }

    private static void listener() throws Exception{
        final NodeCache nodeCache = new NodeCache(curatorFramework, "/curator_1");
        nodeCache.start(true);
        // 其他的listener类似的
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("listener监听："+new String(nodeCache.getCurrentData().getData()));
            }
        });
        setData();
        Thread.sleep(60*1000);
    }

    private static void sync() throws Exception{
        // 异步操作
        curatorFramework.create().inBackground((CuratorFramework client, CuratorEvent event) -> {
            switch (event.getType()) {
                case CREATE:
                    break;
                case DELETE:
                    break;
                case GET_DATA:
                    break;
                case SET_DATA:
                    break;
                case GET_ACL:
                    break;
                case SET_ACL:
                    break;
                case EXISTS:
                    break;
                case CLOSING:
                    break;
                case CHILDREN:
                    break;
                case WATCHED:
                    break;
                case SYNC:
                    break;
            }
        }).forPath("/curator_sync_1", "init".getBytes());

        Thread.sleep(10*1000);
    }

    private static void setData() throws Exception{
        curatorFramework.setData().forPath("/curator_1", "init".getBytes());
    }
    private static void getNode() throws Exception{
        byte[] bytes = curatorFramework.getData().forPath("/curator_1");
        System.out.println(new String(bytes));
    }

    private static void deleteNode() throws Exception{
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator_1");
    }

    private static void createNode() throws Exception{
        String path = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/curator_1", "123".getBytes());
        System.out.println(path);
    }

    // 监听
    private static void nodeChange() throws Exception{
        final NodeCache cache = new NodeCache(curatorFramework, "/");
        cache.start();
        cache.getListenable().addListener(() -> {
            byte[] ret = cache.getCurrentData().getData();
            System.out.println(new String(ret)+"=======");
        });
    }

}
