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
import com.profitbricks.rest.domain.LoadBalancer;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Nic;
import com.profitbricks.rest.domain.Nics;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.util.ArrayList;
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
public class NicTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String dataCenterId;
   static String serverId;
   private static String nicId;
   private static String loadBalancerId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, InterruptedException {
      DataCenter datacenter = new DataCenter();
      datacenter.getProperties().setName("SDK TEST SERVER - Server");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dataCenterId = newDatacenter.getId();

      LoadBalancer loadBalancer = new LoadBalancer();
      LoadBalancer.Properties properties = new LoadBalancer.Properties();
      properties.setName("SDK TEST LOADBALANCER - LoadBalancer");
      properties.setIp("123.123.123.123");
      loadBalancer.setProperties(properties);

      LoadBalancer newLoadBalancer = profitbricksApi.loadbalancerApi.createLoadBalancer(dataCenterId, loadBalancer);
      assertNotNull(newLoadBalancer);

      loadBalancerId = newLoadBalancer.getId();

      Server server = new Server();
      server.getProperties().setName("SDK TEST SERVER - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.serverApi.createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.getId();

      Nic nic = new Nic();

      nic.getProperties().setName("SDK TEST FIREWALLRULES - Nic");
      nic.getProperties().setLan("1");

      nic.getEntities().setFirewallrules(null);

      Thread.sleep(5000);
      Nic newNic = profitbricksApi.nicApi.createNic(dataCenterId, serverId, nic);

      assertNotNull(newNic);
      nicId = newNic.getId();
   }

   @Test
   public void orderedTests() throws RestClientException, IOException, InterruptedException {
      getAllNics();
      Thread.sleep(35000);
      getNic();
      updateNic();
   }

   public void updateNic() throws RestClientException, IOException{
      PBObject object = new PBObject();
      object.setName("SDK TEST FIREWALLRULES - Nic - changed");
      List<String> ips = new ArrayList<String>();
      ips.add("123.123.123.123");
      object.setIps(ips);
      Nic nic = profitbricksApi.nicApi.updateNic(dataCenterId, serverId, nicId, object);
      
      assertNotNull(nic);
      assertEquals(object.getName(), nic.getProperties().getName());
      
      
   }
   public void getNic() throws RestClientException, IOException {
      Nic nic = profitbricksApi.nicApi.getNic(dataCenterId, serverId, nicId);
      assertNotNull(nic);
      assertEquals(nicId, nic.getId());
   }

   public void getAllNics() throws RestClientException, IOException {
      Nics nics = profitbricksApi.nicApi.getAllNics(dataCenterId, serverId);
      assertNotNull(nics);
   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dataCenterId);
   }
}
