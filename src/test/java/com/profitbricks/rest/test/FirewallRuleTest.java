/*
 * Copyright (c) 2017, ProfitBricks GmbH
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
 * 4. Neither the name of the ProfitBricks nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ProfitBricks GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ProfitBricks GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin@stackpointcloud.com
 */
public class FirewallRuleTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    static String dataCenterId;
    static String serverId;
    private static String nicId;
    private static String firewallRuleId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter datacenter = new DataCenter();
        datacenter.getProperties().setName("SDK TEST FIREWALLRULES - Data Center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");
        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server server = new Server();
        server.getProperties().setName("SDK TEST FIREWALLRULES - Server");
        server.getProperties().setRam(1024);
        server.getProperties().setCores(4);

        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, server);
        waitTillProvisioned(newServer.getRequestId());

        assertNotNull(newServer);
        serverId = newServer.getId();

        Nic nic = new Nic();

        nic.getProperties().setName("SDK TEST FIREWALLRULES - Nic");
        nic.getProperties().setLan("1");

        nic.getEntities().setFirewallrules(null);

        Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, serverId, nic);
        waitTillProvisioned(newNic.getRequestId());

        assertNotNull(newNic);
        nicId = newNic.getId();

        FirewallRule firewallRule = new FirewallRule();

        firewallRule.getProperties().setProtocol(Protocol.ICMP.toString());
        firewallRule.getProperties().setIcmpType("8");
        firewallRule.getProperties().setIcmpCode("0");
        firewallRule.getProperties().setName("SDK TEST FIREWALLRULES - FirewallRule");

        FirewallRule newFirewallRule = profitbricksApi.getFirewallRule().createFirewallRule(dataCenterId, serverId, nicId, firewallRule);
        waitTillProvisioned(newFirewallRule.getRequestId());

        assertNotNull(newFirewallRule);
        firewallRuleId = newFirewallRule.getId();
    }

    @Test
    public void orderedTest() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        getAllFirewallRules();
        getFirewallRule();
        updateFirewallRule();
    }

    public void getAllFirewallRules() throws RestClientException, IOException {
        FirewallRules fireWallRules = profitbricksApi.getFirewallRule().getAllFirewallRules(dataCenterId, serverId, nicId);
        assertNotNull(fireWallRules);
    }

    public void getFirewallRule() throws RestClientException, IOException {
        FirewallRule firewallRule = profitbricksApi.getFirewallRule().getFirewallRule(dataCenterId, serverId, nicId, firewallRuleId);
        assertNotNull(firewallRule);
    }

    public void updateFirewallRule() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        FirewallRule.Properties object = new FirewallRule().new Properties();
        object.setName("SDK TEST FIREWALLRULES - FirewallRule - changed");

        FirewallRule firewallRule = profitbricksApi.getFirewallRule().updateFirewWallRule(dataCenterId, serverId, nicId, firewallRuleId, object);
        waitTillProvisioned(firewallRule.getRequestId());
        assertEquals(object.getName(), firewallRule.getProperties().getName());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getFirewallRule().deleteFirewallRule(dataCenterId, serverId, nicId, firewallRuleId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

}
