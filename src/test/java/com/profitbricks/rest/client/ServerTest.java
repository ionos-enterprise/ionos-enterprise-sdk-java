/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client;

import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.rest.domain.UpdateObject;
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
   static String dcId;//= "67b49555-692a-4dfa-abb9-eba9349e074b";
   static String serverId;// = "ec71996c-0e0c-4b5b-b71b-474a89e29e56";

   @BeforeClass
   public static void testCreateServer() throws RestClientException, IOException, InterruptedException {

      DataCenter datacenter = new DataCenter();

      datacenter.properties.name = "SDK TEST DC";
      datacenter.properties.location = Location.US_LAS;
      datacenter.properties.description = "SDK TEST Description";

      datacenter.entities = null;
      datacenter.metadata = null;
      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dcId = newDatacenter.id;
      System.out.println("Created DC Servers");

      //Thread.sleep(10000);
      Server server = new Server();

      server.properties.name = "SDK TEST SERVER";
      server.properties.ram = "1024";
      server.properties.cores = "4";

      server.metadata = null;
      server.entities = null;//.volumes.items.add(volume);

      Server newServer = profitbricksApi.serverApi.createServer(dcId, server);

      assertNotNull(newServer);
      serverId = newServer.id;
      System.out.println("Created a Server");

   }

   @AfterClass
   public static void testDeleteServer() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dcId);
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
      Servers servers = profitbricksApi.serverApi.getAllServers(dcId);
      assertNotNull(servers);
   }

   public void testGetServer() throws RestClientException, IOException, InterruptedException {
      System.out.println("Getting One Server");
      Thread.sleep(5000);
      Server server = profitbricksApi.serverApi.getServer(dcId, serverId);
      assertNotNull(server);
   }

   public void testUpdateServer() throws RestClientException, IOException {
      String newName = "SDK TEST SERVER CHANGED";
      UpdateObject object = new UpdateObject();
      object.name = newName;

      Server updatedServer = profitbricksApi.serverApi.updateServer(dcId, serverId, object);
      assertEquals(newName, updatedServer.properties.name);

   }

   public void testStartServer() throws RestClientException, IOException {
      profitbricksApi.serverApi.startServer(dcId, serverId);
   }

   public void testStopServer() throws RestClientException, IOException {
      profitbricksApi.serverApi.stopServer(dcId, serverId);

   }

   public void testRebootServer() throws RestClientException, IOException {
      profitbricksApi.serverApi.rebootServer(dcId, serverId);
   }
}
