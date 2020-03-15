package com.ydh.redsheep.api;

import org.apache.zookeeper.AsyncCallback;

/**
 * @description: 新增节点的异步返回操作
 * @author: yangdehong
 * @version: 2017/8/29.
 */
public class IStringCallback implements AsyncCallback.StringCallback {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("rc = [" + rc + "], path = [" + path + "], ctx = [" + ctx + "], name = [" + name + "]");
    }
}
