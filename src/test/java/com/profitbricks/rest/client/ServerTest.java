/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client;

import com.profitbricks.rest.domain.BusType;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
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
   String dcId = "67b49555-692a-4dfa-abb9-eba9349e074b";
   String serverId = "ec71996c-0e0c-4b5b-b71b-474a89e29e56";
   
   public ServerTest() {
      this.profitbricksApi = new ProfitbricksApi();
   }
   
   @Test
   public void testCreateServer() throws RestClientException, IOException {
      Server server = new Server();
      
      server.properties.name = "SDK TEST SERVER";
      server.properties.ram = "1024";
      server.properties.cores = "4";
      
      server.metadata = null;
      server.entities = null;//.volumes.items.add(volume);

      Server newServer = profitbricksApi.serverApi.createServer(dcId, server);
      
      assertNotNull(newServer);
      serverId = newServer.id;
   }
   
   @Test
   public void testGetAllServers() throws RestClientException, IOException {
      Servers servers = profitbricksApi.serverApi.getAllServers(dcId);
      assertNotNull(servers);
      assertTrue(servers.items.size() > 0);
   }
   
   @Test
   public void testGetServer() throws RestClientException, IOException {
      Server server = profitbricksApi.serverApi.getServer(dcId, serverId);
      assertNotNull(server);
      
   }
}
