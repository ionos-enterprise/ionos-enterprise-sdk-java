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
import java.util.ArrayList;
import java.util.List;

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
public class NicTest {

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
    private static String loadBalancerId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = newDatacenter.getId();

        LoadBalancer newLoadBalancer = profitbricksApi.getLoadbalancer().createLoadBalancer(dataCenterId, LoadBalancerResource.getLoadBalancer());
        waitTillProvisioned(newLoadBalancer.getRequestId());
        assertNotNull(newLoadBalancer);

        loadBalancerId = newLoadBalancer.getId();


        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        serverId = newServer.getId();

        Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, serverId, NicResource.getNic());
        waitTillProvisioned(newNic.getRequestId());
        assertNotNull(newNic);
        nicId = newNic.getId();
    }

    @Test
    public void t1_getNic() throws RestClientException, IOException {
        Nic nic = profitbricksApi.getNic().getNic(dataCenterId, serverId, nicId);
        assertNotNull(nic);
        assertEquals(nic.getProperties().getName(), NicResource.getNic().getProperties().getName());
        assertEquals(nic.getProperties().getNat(), NicResource.getNic().getProperties().getNat());
        assertEquals(nic.getProperties().getLan(), NicResource.getNic().getProperties().getLan());
    }

    @Test
    public void t2_getAllNics() throws RestClientException, IOException {
        Nics nics = profitbricksApi.getNic().getAllNics(dataCenterId, serverId);
        assertNotNull(nics);
        assertTrue(nics.getItems().size() > 0);
    }

    @Test
    public void t3_updateNic() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        Nic nic = profitbricksApi.getNic().updateNic(dataCenterId, serverId, nicId, NicResource.getEditNic().getProperties());

        assertNotNull(nic);
        assertEquals(nic.getProperties().getName(), NicResource.getEditNic().getProperties().getName());
        waitTillProvisioned(nic.getRequestId());
    }

    @Test
    public void t4_assignNicToLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Nic nic = profitbricksApi.getNic().assignNicToLoadBalancer(dataCenterId, loadBalancerId, nicId);
        assertNotNull(nic);
        assertEquals(nic.getId(), nicId);
        waitTillProvisioned(nic.getRequestId());
    }

    @Test
    public void t5_listAssignedNics() throws RestClientException, IOException {
        Nics nics = profitbricksApi.getNic().getAllBalancedNics(dataCenterId, loadBalancerId);
        assertNotNull(nics);
    }

    @Test
    public void t6_listAssignedNic() throws RestClientException, IOException {
        Nic nic = profitbricksApi.getNic().getBalancedNic(dataCenterId, loadBalancerId, serverId, nicId);
        assertNotNull(nic);
    }

    @Test
    public void t7_unassignNicToLoadBalancer() throws RestClientException, IOException {
        profitbricksApi.getNic().unassignNicFromLoadBalancer(dataCenterId, loadBalancerId, nicId);
    }

    @Test
    public void t7_getFailNic() throws RestClientException, IOException {
        try {
            Nic nic = profitbricksApi.getNic().getNic(dataCenterId, serverId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t8_createNicFail() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            Nic nic = profitbricksApi.getNic().createNic(dataCenterId, serverId, NicResource.getBadNic());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }

    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getNic().deleteNic(dataCenterId, serverId, nicId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

}
