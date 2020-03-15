package com.ydh.redsheep.api;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/29.
 */
public class IDataCallback implements AsyncCallback.DataCallback {
    @Override
    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        System.out.println("i = [" + i + "], s = [" + s + "], o = [" + o + "], bytes = [" + bytes + "], stat = [" + stat + "]");
    }
}
