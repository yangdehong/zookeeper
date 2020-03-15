package com.ydh.redsheep.api.zkClient;

import org.I0Itec.zkclient.IZkConnection;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class ZkClientConnection implements IZkConnection {
    @Override
    public void connect(Watcher watcher) {
        System.out.println("connect");
    }

    @Override
    public void close() throws InterruptedException {
        System.out.println("close");
    }

    @Override
    public String create(String s, byte[] bytes, CreateMode createMode) throws KeeperException, InterruptedException {
        System.out.println("create");
        return s;
    }

    @Override
    public void delete(String s) throws InterruptedException, KeeperException {
        System.out.println("delete"+s);
    }

    @Override
    public boolean exists(String s, boolean b) throws KeeperException, InterruptedException {
        System.out.println("exists"+s);
        return false;
    }

    @Override
    public List<String> getChildren(String s, boolean b) throws KeeperException, InterruptedException {
        System.out.println("getChildren"+s);
        return null;
    }

    @Override
    public byte[] readData(String s, Stat stat, boolean b) throws KeeperException, InterruptedException {
        System.out.println("readData"+s);
        return new byte[0];
    }

    @Override
    public void writeData(String s, byte[] bytes, int i) throws KeeperException, InterruptedException {
        System.out.println("writeData"+s);
    }

    @Override
    public ZooKeeper.States getZookeeperState() {
        System.out.println("getZookeeperState");
        return null;
    }

    @Override
    public long getCreateTime(String s) throws KeeperException, InterruptedException {
        System.out.println("getCreateTime"+s);
        return 0;
    }

    @Override
    public String getServers() {
        System.out.println("getServers");
        return null;
    }
}
