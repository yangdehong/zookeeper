package com.ydh.redsheep.api.zkClient;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

public class ZkClientChildListener implements IZkChildListener {

    @Override
    public void handleChildChange(String s, List<String> list) throws Exception {
        System.out.println("handleChildChange"+list.toString());
    }

}
