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
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.raw.ServerRaw;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
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
   public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      profitbricksApi.setCredentials("bXVoYW1lZEBzdGFja3BvaW50Y2xvdWQuY29tOnRlc3QxMjMh");

      DataCenterRaw datacenter = new DataCenterRaw();
      datacenter.getProperties().setName("SDK TEST SERVER - Server");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
      dataCenterId = newDatacenter.getId();

      ServerRaw server = new ServerRaw();
      server.getProperties().setName("SDK TEST SERVER - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.getId();

   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      profitbricksApi.getServerApi().deleteServer(dataCenterId, serverId);
      profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
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
      List<Server> servers = profitbricksApi.getServerApi().getAllServers(dataCenterId);
      assertNotNull(servers);
   }

   public void testGetServer() throws RestClientException, IOException, InterruptedException {
      System.out.println("Getting One Server");
      Thread.sleep(5000);
      Server server = profitbricksApi.getServerApi().getServer(dataCenterId, serverId);
      assertNotNull(server);
   }

   public void testUpdateServer() throws RestClientException, IOException {
      String newName = "SDK TEST SERVER CHANGED";
      PBObject object = new PBObject();
      object.setName(newName);

      Server updatedServer = profitbricksApi.getServerApi().updateServer(dataCenterId, serverId, object);
      assertEquals(newName, updatedServer.getName());

   }

   public void testStartServer() throws RestClientException, IOException {
      profitbricksApi.getServerApi().startServer(dataCenterId, serverId);
   }

   public void testStopServer() throws RestClientException, IOException {
      profitbricksApi.getServerApi().stopServer(dataCenterId, serverId);

   }

   public void testRebootServer() throws RestClientException, IOException {
      profitbricksApi.getServerApi().rebootServer(dataCenterId, serverId);
   }
}
