package com.ydh.redsheep.test;

import org.apache.zookeeper.AsyncCallback;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/29.
 */
public class IVoidCallback implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int i, String s, Object o) {
        System.out.println("i = [" + i + "], s = [" + s + "], o = [" + o + "]");
    }
}
