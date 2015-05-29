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
import com.profitbricks.rest.domain.FirewallRule;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Nic;
import com.profitbricks.rest.domain.Protocol;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class FirewallRuleTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String dataCenterId;
   static String serverId;
   private static String nicId;
   private static String firewallRuleId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, InterruptedException {
      DataCenter datacenter = new DataCenter();
      datacenter.getProperties().setName("SDK TEST FIREWALLRULES - Server");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dataCenterId = newDatacenter.id;

      Server server = new Server();
      server.getProperties().setName("SDK TEST FIREWALLRULES - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.serverApi.createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

      Nic nic = new Nic();

      nic.getProperties().setName("SDK TEST FIREWALLRULES - Nic");
      nic.getProperties().setLan("1");

      Nic newNic = profitbricksApi.nicApi.createNic(dataCenterId, serverId, nic);

      assertNotNull(newNic);
      nicId = newNic.id;

      FirewallRule firewallRule = new FirewallRule();
      
      firewallRule.getProperties().setProtocol(Protocol.ANY.toString());
      
      FirewallRule newFirewallRule = profitbricksApi.firewallRuleApi.createFirewallRule(dataCenterId, serverId, nicId, firewallRule);
      
      assertNotNull(newFirewallRule);      
      firewallRuleId = newFirewallRule.id;
   }

   @Test
   public void getAllFirewallRules() {
      
      
   }

}
