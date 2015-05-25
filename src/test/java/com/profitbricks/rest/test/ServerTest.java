 /*
 * Copyright (c) <year>, <copyright holder>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
   static String dcId;
   static String serverId;

   @BeforeClass
   public static void testCreateServer() throws RestClientException, IOException, InterruptedException {
      DataCenter datacenter = new DataCenter();
      datacenter.properties.name = "SDK TEST DC";
      datacenter.properties.location = Location.US_LAS;
      datacenter.properties.description = "SDK TEST Description";

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dcId = newDatacenter.id;

      Server server = new Server();
      server.properties.name = "SDK TEST SERVER";
      server.properties.ram = "1024";
      server.properties.cores = "4";

      Server newServer = profitbricksApi.serverApi.createServer(dcId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

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
      PBObject object = new PBObject();
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
