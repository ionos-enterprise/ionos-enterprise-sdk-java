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
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.raw.FirewallRuleRaw;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Nic;
import com.profitbricks.rest.domain.raw.NicRaw;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Protocol;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.raw.ServerRaw;
import com.profitbricks.sdk.ProfitbricksApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.AfterClass;

import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin.gacic
 */
public class FirewallRuleTest {

    static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    static String dataCenterId;
    static String serverId;
    private static String nicId;
    private static String firewallRuleId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenterRaw datacenter = new DataCenterRaw();
        datacenter.getProperties().setName("SDK TEST FIREWALLRULES - Data Center");
        datacenter.getProperties().setLocation(Location.US_LAS.value());
        datacenter.getProperties().setDescription("SDK TEST Description");
        DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        ServerRaw server = new ServerRaw();
        server.getProperties().setName("SDK TEST FIREWALLRULES - Server");
        server.getProperties().setRam("1024");
        server.getProperties().setCores("4");

        Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);
        waitTillProvisioned(newServer.getRequestId());

        assertNotNull(newServer);
        serverId = newServer.getId();

        NicRaw nic = new NicRaw();

        nic.getProperties().setName("SDK TEST FIREWALLRULES - Nic");
        nic.getProperties().setLan("1");

        nic.getEntities().setFirewallrules(null);

        Nic newNic = profitbricksApi.getNicApi().createNic(dataCenterId, serverId, nic);
        waitTillProvisioned(newNic.getRequestId());

        assertNotNull(newNic);
        nicId = newNic.getId();

        FirewallRuleRaw firewallRule = new FirewallRuleRaw();

        firewallRule.getProperties().setProtocol(Protocol.ICMP.toString());
        firewallRule.getProperties().setIcmpType("8");
        firewallRule.getProperties().setIcmpCode("0");
        firewallRule.getProperties().setName("SDK TEST FIREWALLRULES - FirewallRule");

        FirewallRule newFirewallRule = profitbricksApi.getFirewallRuleApi().createFirewallRule(dataCenterId, serverId, nicId, firewallRule);
        waitTillProvisioned(newFirewallRule.getRequestId());

        assertNotNull(newFirewallRule);
        firewallRuleId = newFirewallRule.getId();
    }

    @Test
    public void orderedTest() throws RestClientException, IOException, InterruptedException {
        getAllFirewallRules();
        getFirewallRule();
        updateFirewallRule();
    }

    public void getAllFirewallRules() throws RestClientException, IOException {
     System.out.println("getAllFirewallRules");
        List<FirewallRule> fireWallRules = profitbricksApi.getFirewallRuleApi().getAllFirewallRules(dataCenterId, serverId, nicId);
        assertNotNull(fireWallRules);
    }

    public void getFirewallRule() throws RestClientException, IOException {
        System.out.println("getFirewallRule");
        FirewallRule firewallRule = profitbricksApi.getFirewallRuleApi().getFirewallRule(dataCenterId, serverId, nicId, firewallRuleId);
        assertNotNull(firewallRule);
    }

    public void updateFirewallRule() throws RestClientException, IOException, InterruptedException {
        System.out.println("updateFirewallRule");
        PBObject object = new PBObject();
        object.setName("SDK TEST FIREWALLRULES - FirewallRule - changed");

        FirewallRule firewallRule = profitbricksApi.getFirewallRuleApi().updateFirewWallRule(dataCenterId, serverId, nicId, firewallRuleId, object);
        waitTillProvisioned(firewallRule.getRequestId());
        assertEquals(object.getName(), firewallRule.getName());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getFirewallRuleApi().deleteFirewallRule(dataCenterId, serverId, nicId, firewallRuleId);
        profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }

}
