package com.ydh.redsheep.api.zkClient;

import org.I0Itec.zkclient.IZkDataListener;

public class ZkClientDataListener implements IZkDataListener {
    @Override
    public void handleDataChange(String s, Object o) throws Exception {
        System.out.println("handleDataChange"+s+o);
    }

    @Override
    public void handleDataDeleted(String s) throws Exception {
        System.out.println("handleDataDeleted"+s);
    }
}
