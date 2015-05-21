/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client;

import com.profitbricks.rest.domain.BusType;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class ServerTest {

   ProfitbricksApi profitbricksApi;
   String dcId;//= "67b49555-692a-4dfa-abb9-eba9349e074b";
   String serverId;// = "ec71996c-0e0c-4b5b-b71b-474a89e29e56";

   public ServerTest() {
      this.profitbricksApi = new ProfitbricksApi();
   }

   @Before
   public void testCreateServer() throws RestClientException, IOException, InterruptedException {

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

   @Test
   public void testGetAllServers() throws RestClientException, IOException {
      System.out.println("Getting All Servers");
      Servers servers = profitbricksApi.serverApi.getAllServers(dcId);
      assertNotNull(servers);
      assertTrue(servers.items.size() > 0);
   }

   @Test
   public void testGetServer() throws RestClientException, IOException {
      System.out.println("Getting One Server");
      Server server = profitbricksApi.serverApi.getServer(dcId, serverId);
      assertNotNull(server);

   }

   @After
   public void testDeleteServer() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dcId);
      //profitbricksApi.serverApi.deleteServer(dcId, serverId);
   }
}
