package com.ydh.redsheep.master.ckclient.subscribe;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SubscribeZkClient {

    private static final int CLIENT_QTY = 5;

    private static final String ZOOKEEPER_SERVER = "192.168.2.144:2181";

    private static final String CONFIG_PATH = "/config";
    private static final String COMMAND_PATH = "/command";
    private static final String SERVERS_PATH = "/servers";

    public static void main(String[] args) throws Exception {

        List<ZkClient> clients = new ArrayList<>();
        List<WorkServer> workServers = new ArrayList<>();
        ManageServer manageServer;

        try {
            ServerConfig initConfig = new ServerConfig();
            initConfig.setDbUrl("jdbc:mysql://192.168.2.246:3306/test");
            initConfig.setDbUser("dream");
            initConfig.setDbPwd("Dream@123456");
            // manageServer
            ZkClient clientManage = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new BytesPushThroughSerializer());
            manageServer = new ManageServer(SERVERS_PATH, COMMAND_PATH, CONFIG_PATH, clientManage, initConfig);
            manageServer.start();

            for (int i = 0; i < CLIENT_QTY; ++i) {
                ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new BytesPushThroughSerializer());
                clients.add(client);
                MyServerData myServerData = new MyServerData();
                myServerData.setId(i);
                myServerData.setName("WorkServer#" + i);
                myServerData.setAddress("192.168.2." + i);

                WorkServer workServer = new WorkServer(CONFIG_PATH, SERVERS_PATH, myServerData, client, initConfig);
                workServers.add(workServer);
                workServer.start();

            }
            System.out.println("敲回车键退出！\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        } finally {
            System.out.println("Shutting down...");

            for (WorkServer workServer : workServers) {
                try {
                    workServer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (ZkClient client : clients) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
