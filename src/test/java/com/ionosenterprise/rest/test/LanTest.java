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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LanTest extends BaseTest {

    private static String dataCenterId;
    private static String lanId;
    private static String ipBlockId;

    @BeforeClass
    public static void t1_createDataCenter() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Lan lan = LanResource.getLan();
        Lan newLan = ionosEnterpriseApi.getLan().createLan(dataCenterId, lan);
        assertNotNull(newLan);
        assertEquals(newLan.getProperties().getName(), lan.getProperties().getName());
        assertEquals(newLan.getProperties().isIsPublic(), lan.getProperties().isIsPublic());
        lanId = newLan.getId();
        waitTillProvisioned(newLan.getRequestId());

        IPBlock ipb = IpBlockResource.getIpBlock();
        IPBlock iPBlock = ionosEnterpriseApi.getIpBlock().createIPBlock(ipb);
        assertNotNull(iPBlock);
        ipBlockId = iPBlock.getId();
    }

    @Test
    public void t2_getAllLans() throws RestClientException, IOException {
        Lans lans = ionosEnterpriseApi.getLan().getAllLans(dataCenterId);
        assertNotNull(lans);
    }

    @Test
    public void t3_getLan() throws RestClientException, IOException {
        Lan lan = ionosEnterpriseApi.getLan().getLan(dataCenterId, lanId);
        assertNotNull(lan);
        assertEquals(lan.getId(), lanId);
        assertEquals(lan.getProperties().getName(), LanResource.getLan().getProperties().getName());
        assertEquals(lan.getProperties().isIsPublic(), LanResource.getLan().getProperties().isIsPublic());
    }

    @Test
    public void t4_getLanFail() throws IOException {
        try {
            ionosEnterpriseApi.getLan().getLan(dataCenterId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_updateLan() throws RestClientException, IOException, InterruptedException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Lan.Properties properties = LanResource.getEditLan().getProperties();
        Lan updatedLan = ionosEnterpriseApi.getLan().updateLan(dataCenterId, lanId,
                properties.getName(), properties.isIsPublic());
        waitTillProvisioned(updatedLan.getRequestId());
        assertEquals(updatedLan.getProperties().getName(), properties.getName());
        assertEquals(updatedLan.getProperties().isIsPublic(), properties.isIsPublic());
    }

    @Test
    public void t6_createLanComposite() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException, InterruptedException {

        DataCenter datacenter = DataCenterResource.getDataCenter();
        Lan lan = LanResource.getLan();
        Lans lans = new Lans();
        lans.setItems(Arrays.asList(lan));
        datacenter.getEntities().setLans(lans);
        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(datacenter);

        waitTillProvisioned(newDatacenter.getRequestId());

        ionosEnterpriseApi.getDataCenter().deleteDataCenter(newDatacenter.getId());
    }

    @Test
    public void t7_updateLanWithFailover() throws RestClientException, IOException, InterruptedException,
            NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Server server1 = ServerResource.getServer();
        server1.getProperties().setName("SDK TEST SERVER - Server Failover 1");
        Server newServer1 = ionosEnterpriseApi.getServer().createServer(dataCenterId, server1);
        waitTillProvisioned(newServer1.getRequestId());
        String server1Id = newServer1.getId();

        Lan lan1 = LanResource.getLan();
        Lan newLan1 = ionosEnterpriseApi.getLan().createLan(dataCenterId, lan1);
        waitTillProvisioned(newLan1.getRequestId());
        String lan1Id = newLan1.getId();

        IPBlock iPBlock = ionosEnterpriseApi.getIpBlock().getIPBlock(ipBlockId);

        Nic nic = NicResource.getNicForLanIdAndIPBlock(lan1Id, iPBlock);
        nic.getProperties().setName("SDK TEST NIC - Nic1");
        Nic newNic = ionosEnterpriseApi.getNic().createNic(dataCenterId, server1Id, nic);
        waitTillProvisioned(newNic.getRequestId());

        IpFailover ipFailover = new IpFailover();
        ipFailover.setIp(iPBlock.getProperties().getIps().get(0));
        ipFailover.setNicUuid(newNic.getId());
        Lan updatedLan = ionosEnterpriseApi.getLan().updateLan(dataCenterId, lan1Id, Boolean.TRUE,
                Arrays.asList(ipFailover));
        waitTillProvisioned(updatedLan.getRequestId());

        Server server2 = ServerResource.getServer();
        server2.getProperties().setName("SDK TEST SERVER - Server Failover 2");
        Server newServer2 = ionosEnterpriseApi.getServer().createServer(dataCenterId, server2);
        waitTillProvisioned(newServer2.getRequestId());
        String server2Id = newServer2.getId();

        Nic nic2 = NicResource.getNicForLanIdAndIPBlock(lan1Id, iPBlock);
        nic2.getProperties().setName("SDK TEST NIC - Nic2");
        Nic newNic2 = ionosEnterpriseApi.getNic().createNic(dataCenterId, server2Id, nic2);
        waitTillProvisioned(newNic2.getRequestId());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException, InterruptedException {
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);

        TimeUnit.MINUTES.sleep(1);

        ionosEnterpriseApi.getIpBlock().deleteIPBlock(ipBlockId);
    }
}
