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
import com.profitbricks.rest.domain.*;

import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin.gacic
 */
public class NicTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String dataCenterId;
   static String serverId;
   private static String nicId;
   private static String loadBalancerId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
      DataCenter datacenter = new DataCenter();
      datacenter.getProperties().setName("SDK TEST NIC - Server");
      datacenter.getProperties().setLocation("us/las");
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
      dataCenterId = newDatacenter.getId();

      LoadBalancer loadBalancer = new LoadBalancer();
      LoadBalancer.Properties properties = new LoadBalancer.Properties();
      properties.setName("SDK TEST NIC - LoadBalancer");
      properties.setIp("123.123.129.132");
      loadBalancer.setProperties(properties);

      LoadBalancer newLoadBalancer = profitbricksApi.getLoadbalancerApi().createLoadBalancer(dataCenterId, loadBalancer);
      waitTillProvisioned(newLoadBalancer.getRequestId());
      assertNotNull(newLoadBalancer);

      loadBalancerId = newLoadBalancer.getId();

      Server server = new Server();
      server.getProperties().setName("SDK TEST NIC - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);
      assertNotNull(newServer);
      waitTillProvisioned(newServer.getRequestId());

      serverId = newServer.getId();

      Nic nic = new Nic();

      nic.getProperties().setName("SDK TEST NIC - Nic");
      nic.getProperties().setLan("1");
      nic.getProperties().setNat(Boolean.FALSE);

      nic.getEntities().setFirewallrules(null);

      Nic newNic = profitbricksApi.getNicApi().createNic(dataCenterId, serverId, nic);
      waitTillProvisioned(newNic.getRequestId());

      assertNotNull(newNic);
      nicId = newNic.getId();
   }

   @Test
   public void orderedTests() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      getAllNics();
      getNic();
      updateNic();
      assignNicToLoadBalancer();
      listAssignedNics();
      listAssignedNic();
      unassignNicToLoadBalancer();
   }

   public void updateNic() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
      PBObject object = new PBObject();
      object.setName("SDK TEST NIC - Nic - changed");
      List<String> ips = new ArrayList<String>();
      ips.add("123.123.121.129");
      object.setIps(ips);
      Nic nic = profitbricksApi.getNicApi().updateNic(dataCenterId, serverId, nicId, object);

      assertNotNull(nic);
      assertEquals(object.getName(), nic.getProperties().getName());
      waitTillProvisioned(nic.getRequestId());
   }

   public void getNic() throws RestClientException, IOException {
      Nic nic = profitbricksApi.getNicApi().getNic(dataCenterId, serverId, nicId);
      assertNotNull(nic);
      assertEquals(nicId, nic.getId());
   }

   public void getAllNics() throws RestClientException, IOException {
      Nics nics = profitbricksApi.getNicApi().getAllNics(dataCenterId, serverId);
      assertNotNull(nics);
   }

   private void assignNicToLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      Nic nic = profitbricksApi.getNicApi().assignNicToLoadBalancer(dataCenterId, loadBalancerId, nicId);
      assertNotNull(nic);
      assertEquals(nic.getId(), nicId);
      waitTillProvisioned(nic.getRequestId());
   }

   private void unassignNicToLoadBalancer() throws RestClientException, IOException {
      profitbricksApi.getNicApi().unassignNicFromLoadBalancer(dataCenterId, loadBalancerId, nicId);
   }

   private void listAssignedNics() throws RestClientException, IOException {
      Nics nics = profitbricksApi.getNicApi().getAllBalancedNics(dataCenterId, loadBalancerId);
      assertNotNull(nics);
   }

   private void listAssignedNic() throws RestClientException, IOException {
      Nic nic = profitbricksApi.getNicApi().getBalancedNic(dataCenterId, loadBalancerId, serverId, nicId);
      assertNotNull(nic);
   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      profitbricksApi.getNicApi().deleteNic(dataCenterId, serverId, nicId);
      profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
   }

}
