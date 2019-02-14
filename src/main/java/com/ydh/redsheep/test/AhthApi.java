package com.ydh.redsheep.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 权限
 * @author: yangdehong
 * @version: 2017/8/30.
 */
public class AhthApi {

    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception{
        String connectString = "192.168.2.144:2181";
        int timeout = 5000;
        zooKeeper = new ZooKeeper(connectString, timeout, watchedEvent -> {
            System.out.println("事件："+watchedEvent);
//            authCreate();
            authGet();

        });

        System.out.println(zooKeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static  void authGet(){
        zooKeeper.addAuthInfo("digest", "ydh:123456".getBytes());

        try {
            System.out.println(new String(zooKeeper.getData("/node_auth_1", true, stat)));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 权限模式（scheme）：ip，digest
     * 授权对象（ID）
     *      ip模式：具体的ip地址
     *      digest模式：username.Base64(SHA-1(username:password))
     * 权限（permission）：create(C),delete(D),read(R),write(W),admin(a)
     *      注：单个权限，完全权限，符合权限
     *
     * 权限组合： scheme + ID + permission
     */
    private static void authCreate(){

        String path = null;
        try {
            ACL aclIp = new ACL(ZooDefs.Perms.READ, new Id("ip", "192.168.2.144"));
            ACL aclDigest = new ACL(ZooDefs.Perms.READ| ZooDefs.Perms.WRITE,
                    new Id("digest", DigestAuthenticationProvider.generateDigest("ydh:123456")));
            // 还有其他授权方式ZooDefs.Ids.ANYONE_ID_UNSAFE这个类
            List<ACL> aclList = new ArrayList<>();
            aclList.add(aclIp);
            aclList.add(aclDigest);
            path = zooKeeper.create("/node_auth_1", "123".getBytes(), aclList, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("return path:"+path);

    }

}
