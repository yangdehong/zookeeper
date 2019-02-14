package com.ydh.redsheep.distribted.lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimpleDistributedLockMutex extends BaseDistributedLock implements
        DistributedLock {

    // 锁名称前缀
    private static final String LOCK_NAME = "lock-";
    // zookeeper中locker节点的路径
    private final String basePath;
    // 获取锁以后自己促昂就顺序节点的路径
    private String ourLockPath;

    public SimpleDistributedLockMutex(ZkClientExt client, String basePath) {
        super(client, basePath, LOCK_NAME);
        this.basePath = basePath;
    }

    /**
     * 获取锁资源
     * @param time
     * @param unit
     * @return
     * @throws Exception
     */
    private boolean internalLock(long time, TimeUnit unit) throws Exception {
        ourLockPath = attemptLock(time, unit);
        return ourLockPath != null;
    }

    @Override
    public void acquire() throws Exception {
        if (!internalLock(-1, null)) {
            throw new IOException("连接丢失!在路径:'" + basePath + "'下不能获取锁!");
        }
    }

    @Override
    public boolean acquire(long time, TimeUnit unit) throws Exception {
        return internalLock(time, unit);
    }

    @Override
    public void release() throws Exception {
        releaseLock(ourLockPath);
    }


}
