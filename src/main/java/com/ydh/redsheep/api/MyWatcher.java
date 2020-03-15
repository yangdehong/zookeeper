package com.ydh.redsheep.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
*
* @author : yangdehong
* @date : 2020-03-15 12:47
*/
public class MyWatcher implements Watcher {

    @Override
    public void process(WatchedEvent watcher) {
        System.out.println("事件："+watcher);
        if (watcher.getState() == Event.KeeperState.SyncConnected) {
            Event.EventType type = watcher.getType();
            switch (type) {
                case None:
                    System.out.println("事件处理连接成功");
                    break;
                case NodeCreated:
                    System.out.println("事件处理NodeCreated");
                    break;
                case NodeChildrenChanged:
                    System.out.println("事件处理NodeChildrenChanged");
                    break;
                case NodeDataChanged:
                    System.out.println("事件处理NodeDataChanged");
                    break;
                case NodeDeleted:
                    System.out.println("事件处理NodeDeleted");
                    break;
            }
        }
    }
}
