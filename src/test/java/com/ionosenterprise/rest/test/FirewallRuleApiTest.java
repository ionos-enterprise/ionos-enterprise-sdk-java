/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
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
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.*;
import com.ionosenterprise.rest.test.resource.*;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirewallRuleApiTest extends BaseTest {

    private static String dataCenterId;
    private static String serverId;
    private static String nicId;
    private static String firewallRuleId;

    @BeforeClass
    public static void t1_createFirewallRule() throws RestClientException, IOException, InterruptedException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Server newServer = ionosEnterpriseApi.getServerApi().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        serverId = newServer.getId();
        waitTillProvisioned(newServer.getRequestId());

        Nic newNic = ionosEnterpriseApi.getNicApi().createNic(dataCenterId, serverId, NicResource.getNic());
        assertNotNull(newNic);
        nicId = newNic.getId();
        waitTillProvisioned(newNic.getRequestId());

        FirewallRule newFirewallRule = ionosEnterpriseApi.getFirewallRuleApi().createFirewallRule(
                dataCenterId, serverId, nicId, FirewallRuleResource.getFirewallRule());
        assertNotNull(newFirewallRule);
        firewallRuleId = newFirewallRule.getId();
        waitTillProvisioned(newFirewallRule.getRequestId());
    }

    @Test
    public void t2_getAllFirewallRules() throws RestClientException, IOException {
        FirewallRules fireWallRules = ionosEnterpriseApi.getFirewallRuleApi().getAllFirewallRules(
                dataCenterId, serverId, nicId);
        assertNotNull(fireWallRules);
        assertTrue(fireWallRules.getItems().size() > 0);
    }

    @Test
    public void t3_getFirewallRule() throws RestClientException, IOException {
        FirewallRule firewallRule = ionosEnterpriseApi.getFirewallRuleApi().getFirewallRule(
                dataCenterId, serverId, nicId, firewallRuleId);
        assertNotNull(firewallRule);
        assertEquals(firewallRule.getProperties().getName(),
                FirewallRuleResource.getFirewallRule().getProperties().getName());
    }

    @Test
    public void t4_updateFirewallRule() throws RestClientException, IOException, InterruptedException,
            NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        FirewallRule firewallRule = ionosEnterpriseApi.getFirewallRuleApi().updateFirewallRule(dataCenterId, serverId,
                nicId, firewallRuleId, FirewallRuleResource.getEditFirewallRule().getProperties());
        waitTillProvisioned(firewallRule.getRequestId());
        assertEquals(firewallRule.getProperties().getName(),
                FirewallRuleResource.getEditFirewallRule().getProperties().getName());
    }

    @Test
    public void t5_getFailFirewall() throws RestClientException, IOException {
        try {
            ionosEnterpriseApi.getFirewallRuleApi().getFirewallRule(dataCenterId, serverId, nicId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t6_createFirewallFail() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        try {
            ionosEnterpriseApi.getFirewallRuleApi().createFirewallRule(dataCenterId, serverId,nicId,
                    FirewallRuleResource.getBadFirewallRule());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }

    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getFirewallRuleApi().deleteFirewallRule(dataCenterId, serverId, nicId, firewallRuleId);
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }

}
