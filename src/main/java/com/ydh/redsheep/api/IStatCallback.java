package com.ydh.redsheep.api;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/30.
 */
public class IStatCallback implements AsyncCallback.StatCallback {
    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        System.out.println("i = [" + i + "], s = [" + s + "], o = [" + o + "], stat = [" + stat + "]");
    }
}
