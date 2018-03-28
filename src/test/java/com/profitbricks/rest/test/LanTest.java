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
import com.profitbricks.rest.test.resource.CommonResource;
import com.profitbricks.rest.test.resource.DataCenterResource;
import com.profitbricks.rest.test.resource.LanResource;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LanTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String dataCenterId;
    private static String lanId;
    private static String ipBlockId;

    @BeforeClass
    public static void t1_createDataCenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));


        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = newDatacenter.getId();
        assertEquals(newDatacenter.getProperties().getName(), DataCenterResource.getDataCenter().getProperties().getName());
        waitTillProvisioned(newDatacenter.getRequestId());

        Lan newLan = profitbricksApi.getLan().createLan(dataCenterId, LanResource.getLan());
        lanId = newLan.getId();
        assertNotNull(newLan);
        assertEquals(newLan.getProperties().getName(), LanResource.getLan().getProperties().getName());
        assertEquals(newLan.getProperties().isIsPublic(), LanResource.getLan().getProperties().isIsPublic());
        waitTillProvisioned(newLan.getRequestId());

        IPBlock ipb = new IPBlock();
        ipb.getProperties().setLocation("us/las");
        ipb.getProperties().setSize(1);
        IPBlock iPBlock = profitbricksApi.getIpBlock().createIPBlock(ipb);
        ipBlockId = iPBlock.getId();
    }

    @Test
    public void t2_getAllLans() throws RestClientException, IOException {
        Lans lans = profitbricksApi.getLan().getAllLans(dataCenterId);
        assertNotNull(lans);
    }

    @Test
    public void t3_getLan() throws RestClientException, IOException {
        Lan lan = profitbricksApi.getLan().getLan(dataCenterId, lanId);
        assertNotNull(lan);
        assertEquals(lan.getId(), lanId);
        assertEquals(lan.getProperties().getName(), LanResource.getLan().getProperties().getName());
        assertEquals(lan.getProperties().isIsPublic(), LanResource.getLan().getProperties().isIsPublic());
    }

    @Test
    public void t4_getLanFail() throws RestClientException, IOException {
        try {
            Lan lan = profitbricksApi.getLan().getLan(dataCenterId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_updateLan() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Lan updatedLan = profitbricksApi.getLan().updateLan(dataCenterId, lanId, LanResource.getEditLan().getProperties().getName(), LanResource.getEditLan().getProperties().isIsPublic());
        waitTillProvisioned(updatedLan.getRequestId());
        assertEquals(updatedLan.getProperties().getName(), LanResource.getEditLan().getProperties().getName());
        assertEquals(updatedLan.getProperties().isIsPublic(), LanResource.getEditLan().getProperties().isIsPublic());
    }

    @Test
    public void t6_createLanComposite() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RestClientException, IOException, InterruptedException {
        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST DC - Composite Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        Lan lan = new Lan();

        lan.getProperties().setName("SDK TEST Lan - Lan");
        lan.getProperties().setIsPublic(false);

        Lans lans = new Lans();
        List<Lan> lanList = new ArrayList<Lan>();
        lanList.add(lan);
        lans.setItems(lanList);
        datacenter.getEntities().setLans(lans);

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);

        waitTillProvisioned(newDatacenter.getRequestId());

        profitbricksApi.getDataCenter().deleteDataCenter(newDatacenter.getId());
    }

    @Test
    public void t7_createLanFail() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            Lan lan = profitbricksApi.getLan().createLan(dataCenterId,LanResource.getBadLan());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @Test
    public void t8_updateLanWithFailover() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Server server1 = new Server();
        server1.getProperties().setName("SDK TEST SERVER - Server Failover 1");
        server1.getProperties().setRam(1024);
        server1.getProperties().setCores(1);
        Server newServer1 = profitbricksApi.getServer().createServer(dataCenterId, server1);
        waitTillProvisioned(newServer1.getRequestId());
        String server1Id = newServer1.getId();

        Lan lan1 = new Lan();
        lan1.getProperties().setName("SDK TEST Lan - Lan");
        lan1.getProperties().setIsPublic(false);
        Lan newLan1 = profitbricksApi.getLan().createLan(dataCenterId, lan1);
        String lan1Id = newLan1.getId();
        waitTillProvisioned(newLan1.getRequestId());

        IPBlock iPBlock = profitbricksApi.getIpBlock().getIPBlock(ipBlockId);

        Nic nic = new Nic();
        nic.getProperties().setName("SDK TEST NIC - Nic");
        nic.getProperties().setLan(lan1Id);
        nic.getProperties().setNat(Boolean.FALSE);
        nic.getProperties().setIps(iPBlock.getProperties().getIps());
        nic.getEntities().setFirewallrules(null);
        Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, server1Id, nic);
        waitTillProvisioned(newNic.getRequestId());

        IpFailover ipFailover = new IpFailover();
        ipFailover.setIp(iPBlock.getProperties().getIps().get(0));
        ipFailover.setNicUuid(newNic.getId());

        List<IpFailover> failovers = new ArrayList<IpFailover>();
        failovers.add(ipFailover);

        Lan updatedLan = profitbricksApi.getLan().updateLan(dataCenterId, lan1Id, Boolean.TRUE, failovers);
        waitTillProvisioned(updatedLan.getRequestId());

        Server server2 = new Server();
        server2.getProperties().setName("SDK TEST SERVER - Server Failover 2");
        server2.getProperties().setRam(1024);
        server2.getProperties().setCores(1);
        Server newServer2 = profitbricksApi.getServer().createServer(dataCenterId, server2);
        waitTillProvisioned(newServer2.getRequestId());
        String server2Id = newServer2.getId();

        Nic nic2 = new Nic();
        nic2.getProperties().setName("SDK TEST NIC - Nic2");
        nic2.getProperties().setLan(lan1Id);
        nic2.getProperties().setNat(Boolean.FALSE);
        nic2.getProperties().setIps(iPBlock.getProperties().getIps());
        nic2.getEntities().setFirewallrules(null);
        Nic newNic2 = profitbricksApi.getNic().createNic(dataCenterId, server2Id, nic2);
        waitTillProvisioned(newNic2.getRequestId());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);

        try {
            TimeUnit.MINUTES.sleep(1);
        }
        catch (Exception ex) {}

        profitbricksApi.getIpBlock().deleteIPBlock(ipBlockId);
    }
}
