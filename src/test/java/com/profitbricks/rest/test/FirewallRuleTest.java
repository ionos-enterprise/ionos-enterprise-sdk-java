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

import com.profitbricks.rest.test.resource.*;
import com.profitbricks.sdk.ProfitbricksApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.AfterClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirewallRuleTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dataCenterId;
    static String serverId;
    private static String nicId;
    private static String firewallRuleId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        waitTillProvisioned(newServer.getRequestId());
        assertNotNull(newServer);
        serverId = newServer.getId();

        Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, serverId, NicResource.getNic());
        waitTillProvisioned(newNic.getRequestId());
        assertNotNull(newNic);
        nicId = newNic.getId();

        FirewallRule newFirewallRule = profitbricksApi.getFirewallRule().createFirewallRule(dataCenterId, serverId, nicId, FirewallRuleResource.getFirewallRule());
        waitTillProvisioned(newFirewallRule.getRequestId());
        assertNotNull(newFirewallRule);
        firewallRuleId = newFirewallRule.getId();
    }

    @Test
    public void t1_getAllFirewallRules() throws RestClientException, IOException {
        FirewallRules fireWallRules = profitbricksApi.getFirewallRule().getAllFirewallRules(dataCenterId, serverId, nicId);
        assertNotNull(fireWallRules);
        assertTrue(fireWallRules.getItems().size() > 0);
    }

    @Test
    public void t2_getFirewallRule() throws RestClientException, IOException {
        FirewallRule firewallRule = profitbricksApi.getFirewallRule().getFirewallRule(dataCenterId, serverId, nicId, firewallRuleId);
        assertNotNull(firewallRule);
        assertEquals(firewallRule.getProperties().getName(), FirewallRuleResource.getFirewallRule().getProperties().getName());
    }

    @Test
    public void t3_updateFirewallRule() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        FirewallRule firewallRule = profitbricksApi.getFirewallRule().updateFirewWallRule(dataCenterId, serverId, nicId, firewallRuleId, FirewallRuleResource.getEditFirewallRule().getProperties());
        waitTillProvisioned(firewallRule.getRequestId());
        assertEquals(firewallRule.getProperties().getName(), FirewallRuleResource.getEditFirewallRule().getProperties().getName());
    }

    @Test
    public void t4_getFailFirewall() throws RestClientException, IOException {
        try {
            FirewallRule firewallRule = profitbricksApi.getFirewallRule().getFirewallRule(dataCenterId, serverId, nicId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_createFirewallFail() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            FirewallRule firewallRule = profitbricksApi.getFirewallRule().createFirewallRule(dataCenterId, serverId,nicId, FirewallRuleResource.getBadFirewallRule());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }

    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getFirewallRule().deleteFirewallRule(dataCenterId, serverId, nicId, firewallRuleId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

}
