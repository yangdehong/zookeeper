package com.ydh.redsheep.master.curator;

/**
 * 触发一下mainstem发生切换
 *
 * @author jianghang 2012-9-11 下午02:26:03
 * @version 1.0.0
 */
public interface RunningListener {

    /**
     * 启动时回调做点事情
     */
    void processStart(Object context);

    /**
     * 关闭时回调做点事情
     */
    void processStop(Object context);

    /**
     * 触发现在轮到自己做为active，需要载入上一个active的上下文数据
     */
    void processActiveEnter(Object context);

    /**
     * 触发一下当前active模式失败
     */
    void processActiveExit(Object context);

}