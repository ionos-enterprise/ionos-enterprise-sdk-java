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
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.IpBlockResource;
import com.ionosenterprise.sdk.IonosEnterpriseApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;

import static com.ionosenterprise.rest.test.DatacenterTest.waitTillProvisioned;
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
public class IPBlockTest {

    static IonosEnterpriseApi ionosEnterpriseApi;

    static {
        try {
            ionosEnterpriseApi = new IonosEnterpriseApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String ipBlockId;
    static String dataCenterId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));

        IPBlock iPBlock = ionosEnterpriseApi.getIpBlock().createIPBlock(IpBlockResource.getIpBlock());
        assertNotNull(iPBlock);
        assertEquals(iPBlock.getProperties().getName(), IpBlockResource.getIpBlock().getProperties().getName());
        assertEquals(iPBlock.getProperties().getLocation(), IpBlockResource.getIpBlock().getProperties().getLocation());
        assertEquals(iPBlock.getProperties().getSize(), IpBlockResource.getIpBlock().getProperties().getSize());
        ipBlockId = iPBlock.getId();
    }

    @Test
    public void t1_getAllIpBlocks() throws RestClientException, IOException {
        IPBlocks iPBlocks = ionosEnterpriseApi.getIpBlock().getAllIPBlocks();
        assertNotNull(iPBlocks);
    }

    @Test
    public void t2_getIpBlock() throws RestClientException, IOException {
        IPBlock iPBlock = ionosEnterpriseApi.getIpBlock().getIPBlock(ipBlockId);
        assertNotNull(iPBlock);
        assertEquals(iPBlock.getProperties().getName(), IpBlockResource.getIpBlock().getProperties().getName());
        assertEquals(iPBlock.getProperties().getLocation(), IpBlockResource.getIpBlock().getProperties().getLocation());
        assertEquals(iPBlock.getProperties().getSize(), IpBlockResource.getIpBlock().getProperties().getSize());
    }

    @Test
    public void t3_getIpBlockFail() throws RestClientException, IOException {
        try {
            IPBlock iPBlock = ionosEnterpriseApi.getIpBlock().getIPBlock(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t4_createIpBlockFail() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            ionosEnterpriseApi.getIpBlock().createIPBlock(IpBlockResource.geBadtIpBlock());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @Test
    public void t5_testIpBlockConsumerDetails() throws InvocationTargetException, NoSuchMethodException,
            IllegalAccessException, RestClientException, IOException, InterruptedException {
        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server server = new Server();
        server.getProperties().setName("SDK TEST SERVER");
        server.getProperties().setRam(1024);
        server.getProperties().setCores(1);
        Server newServer = ionosEnterpriseApi.getServer().createServer(dataCenterId, server);
        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        String serverId = newServer.getId();

        Lan lan = new Lan();
        lan.getProperties().setName("SDK TEST Lan");
        lan.getProperties().setIsPublic(true);
        Lan newLan = ionosEnterpriseApi.getLan().createLan(dataCenterId, lan);
        assertNotNull(newLan);
        waitTillProvisioned(newLan.getRequestId());
        String lanId = newLan.getId();

        IPBlock iPBlock = ionosEnterpriseApi.getIpBlock().getIPBlock(ipBlockId);
        assertNotNull(iPBlock);

        Nic nic = new Nic();
        nic.getProperties().setName("SDK TEST NIC");
        nic.getProperties().setLan(lanId);
        nic.getProperties().setNat(Boolean.FALSE);
        nic.getProperties().setIps(iPBlock.getProperties().getIps());
        nic.getEntities().setFirewallrules(null);
        Nic newNic = ionosEnterpriseApi.getNic().createNic(dataCenterId, serverId, nic);
        assertNotNull(newNic);
        waitTillProvisioned(newNic.getRequestId());

        IPBlock iPBlockWithConsumerDetails = ionosEnterpriseApi.getIpBlock().getIPBlock(ipBlockId);
        assertNotNull(iPBlockWithConsumerDetails);
        assertEquals(
                iPBlockWithConsumerDetails.getProperties().getIpConsumers().size(),
                iPBlock.getProperties().getIps().size());
        assertTrue(iPBlock.getProperties().getIps()
                .contains(iPBlockWithConsumerDetails.getProperties().getIpConsumers().get(0).getIp()));
        assertTrue(iPBlock.getProperties().getIps()
                .contains(iPBlockWithConsumerDetails.getProperties().getIpConsumers().get(1).getIp()));
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);

        try {
            TimeUnit.MINUTES.sleep(1);
        }
        catch (Exception ex) {}

        ionosEnterpriseApi.getIpBlock().deleteIPBlock(ipBlockId);
    }
}
