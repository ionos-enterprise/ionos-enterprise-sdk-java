/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class ServerTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String dataCenterId;
   static String serverId;

   @BeforeClass
   public static void testCreateServer() throws RestClientException, IOException, InterruptedException {
      DataCenter datacenter = new DataCenter();
      datacenter.getProperties().setName("SDK TEST DC - Server");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dataCenterId = newDatacenter.id;

      Server server = new Server();
      server.getProperties().setName("SDK TEST SERVER - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.serverApi.createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      profitbricksApi.serverApi.deleteServer(dataCenterId, serverId);
      profitbricksApi.dataCenterApi.deleteDataCenter(dataCenterId);
   }

   @Test
   public void testInOrder() throws RestClientException, IOException, InterruptedException {
      testGetAllServers();
      testGetServer();
      testUpdateServer();
      testRebootServer();
      testStartServer();
      testStopServer();
   }

   public void testGetAllServers() throws RestClientException, IOException {
      System.out.println("Getting All Servers");
      Servers servers = profitbricksApi.serverApi.getAllServers(dataCenterId);
      assertNotNull(servers);
   }

   public void testGetServer() throws RestClientException, IOException, InterruptedException {
      System.out.println("Getting One Server");
      Thread.sleep(5000);
      Server server = profitbricksApi.serverApi.getServer(dataCenterId, serverId);
      assertNotNull(server);
   }

   public void testUpdateServer() throws RestClientException, IOException {
      String newName = "SDK TEST SERVER CHANGED";
      PBObject object = new PBObject();
      object.name = newName;

      Server updatedServer = profitbricksApi.serverApi.updateServer(dataCenterId, serverId, object);
      assertEquals(newName, updatedServer.getProperties().getName());

   }

   public void testStartServer() throws RestClientException, IOException {
      profitbricksApi.serverApi.startServer(dataCenterId, serverId);
   }

   public void testStopServer() throws RestClientException, IOException {
      profitbricksApi.serverApi.stopServer(dataCenterId, serverId);

   }

   public void testRebootServer() throws RestClientException, IOException {
      profitbricksApi.serverApi.rebootServer(dataCenterId, serverId);
   }
}
