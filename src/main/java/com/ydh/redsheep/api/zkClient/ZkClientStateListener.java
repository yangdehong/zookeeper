package com.ydh.redsheep.api.zkClient;

import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher;

public class ZkClientStateListener implements IZkStateListener {
    @Override
    public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
        System.out.println("handleStateChanged"+keeperState);
    }

    @Override
    public void handleNewSession() throws Exception {
        System.out.println("handleNewSession");
    }
}
