/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client;

import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class ServerTest {

   ProfitbricksApi profitbricksApi;
   String dcId = "3c3b9da5-ae8c-4fcf-af09-a8d40a118198";
   String serverId = "ec71996c-0e0c-4b5b-b71b-474a89e29e56";

   public ServerTest() {
      this.profitbricksApi = new ProfitbricksApi();
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
