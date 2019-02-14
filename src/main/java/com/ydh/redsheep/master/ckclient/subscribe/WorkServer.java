package com.ydh.redsheep.master.ckclient.subscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

public class WorkServer {

	//
	private ZkClient zkClient;
	//
	private String configPath;
	//
	private String serversPath;
	//
	private MyServerData myServerData;
	//
	private ServerConfig serverConfig;
	// 数据监听
	private IZkDataListener dataListener;

	/**
	 *
	 * @param configPath config路径
	 * @param serversPath server路径
	 * @param myServerData server数据
	 * @param zkClient
	 * @param initConfig 当前工作服务器的初始配置
	 */
	public WorkServer(String configPath, String serversPath,
					  MyServerData myServerData, ZkClient zkClient, ServerConfig initConfig) {
		this.zkClient = zkClient;
		this.serversPath = serversPath;
		this.configPath = configPath;
		this.serverConfig = initConfig;
		this.myServerData = myServerData;

		this.dataListener = new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				String retJson = new String((byte[])data);
				ServerConfig serverConfigLocal = (ServerConfig)JSON.parseObject(retJson,ServerConfig.class);
				updateConfig(serverConfigLocal);
				System.out.println("new Work server config is:"+serverConfig.toString());
			}
		};

	}

	public void start() {
		System.out.println("work server start...");
		initRunning();
	}

	public void stop() {
		System.out.println("work server stop...");
		zkClient.unsubscribeDataChanges(configPath, dataListener);
	}

	private void initRunning() {
		registMe();
		zkClient.subscribeDataChanges(configPath, dataListener);
	}

	private void registMe() {
		String mePath = serversPath.concat("/").concat(myServerData.getAddress());
		try {
			zkClient.createEphemeral(mePath, JSON.toJSONString(myServerData)
					.getBytes());
		} catch (ZkNoNodeException e) {
			// 创建serversPath路径
			zkClient.createPersistent(serversPath, true);
			registMe();
		}
	}

	private void updateConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

}
