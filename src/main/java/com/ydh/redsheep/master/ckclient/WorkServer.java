package com.ydh.redsheep.master.ckclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkServer {

    private volatile boolean running = false;

    private ZkClient zkClient;

    // master节点路径
    private static final String MASTER_PATH = "/master";

    // 监听删除事件
    private IZkDataListener dataListener;

    // 记录当前服务器的具体信息
    private MyCkClientData serverData;

    // 记录集群中master的具体信息
    private MyCkClientData masterData;

    private ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);
    private int delayTime = 5;

    /**
     * 监听删除事件
     *
     * @param rd
     */
    public WorkServer(MyCkClientData rd) {
        this.serverData = rd;
        this.dataListener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
//                takeMaster();
                if (masterData != null && masterData.getName().equals(serverData.getName())) {
                    takeMaster();
                } else {
                    delayExector.schedule(() -> {
                        takeMaster();
                    }, delayTime, TimeUnit.SECONDS);
                }
            }

            @Override
            public void handleDataChange(String dataPath, Object data)
                    throws Exception {

            }
        };
    }

    public void start() throws Exception {
        if (running) {
            throw new Exception("server has startup...");
        }
        running = true;
        zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
        takeMaster();

    }

    public void stop() throws Exception {
        if (!running) {
            throw new Exception("server has stoped");
        }
        running = false;

        delayExector.shutdown();

        zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);

        releaseMaster();

    }

    /**
     * 选举，增强master权利
     */
    private void takeMaster() {
        if (!running) {
            return;
        }
        try {
            zkClient.create(MASTER_PATH, serverData, CreateMode.EPHEMERAL);
            masterData = serverData;
            System.out.println(serverData.getName() + " is master");
            delayExector.schedule(() -> {
                if (checkMaster()) {
                    releaseMaster();
                }
            }, 5, TimeUnit.SECONDS);
        } catch (ZkNodeExistsException e) {
            MyCkClientData myCkClientData = zkClient.readData(MASTER_PATH, true);
            if (myCkClientData == null) {
                takeMaster();
            } else {
                masterData = myCkClientData;
            }
        } catch (Exception e) {
            // ignore;
        }

    }

    /**
     * 释放master权利
     */
    private void releaseMaster() {
        if (checkMaster()) {
            zkClient.delete(MASTER_PATH);
        }
    }

    private boolean checkMaster() {
        try {
            MyCkClientData eventData = zkClient.readData(MASTER_PATH);
            masterData = eventData;
            if (masterData.getName().equals(serverData.getName())) {
                return true;
            }
            return false;
        } catch (ZkNoNodeException e) {
            return false;
        } catch (ZkInterruptedException e) {
            return checkMaster();
        } catch (ZkException e) {
            return false;
        }
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

}
