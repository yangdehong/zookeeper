package com.ydh.redsheep.test;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/29.
 */
public class IChildren2Callback implements AsyncCallback.Children2Callback{
    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        System.out.println("i = [" + i + "], s = [" + s + "], o = [" + o + "], list = [" + list + "], stat = [" + stat + "]");
    }
}
