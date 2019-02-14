/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ydh.redsheep.master.ckclient;


import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.List;

public class LeaderSelectorZkClient {
    //启动的服务个数
    private static final int CLIENT_QTY = 10;
    //zookeeper服务器的地址
    private static final String ZOOKEEPER_SERVER = "192.168.2.144:2181";

    public static void main(String[] args) throws Exception {
        //保存所有zkClient的列表
        List<ZkClient> clients = new ArrayList<>();
        //保存所有服务的列表
        List<WorkServer> workServers = new ArrayList<>();

        try {
            for (int i = 0; i < CLIENT_QTY; ++i) {
                //创建zkClient
                ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new SerializableSerializer());
                clients.add(client);
                //创建serverData
                MyCkClientData myCkClientData = new MyCkClientData();
                myCkClientData.setCid(Long.valueOf(i));
                myCkClientData.setName("Client #" + i);
                System.out.println("Client #" + i);
                //创建服务
                WorkServer workServer = new WorkServer(myCkClientData);
                workServer.setZkClient(client);
                workServers.add(workServer);
                workServer.start();
            }

//            System.out.println("敲回车键退出！\n");
//            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } finally {
//            System.out.println("Shutting down...");
//
//            for (WorkServer workServer : workServers) {
//                try {
//                    workServer.stop();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            for (ZkClient client : clients) {
//                try {
//                    client.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}
