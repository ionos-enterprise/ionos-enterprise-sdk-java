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

import static com.ionosenterprise.rest.test.DatacenterTest.waitTillProvisioned;

import com.ionosenterprise.sdk.IonosEnterpriseApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
public class LoadBalancerTest {

    static IonosEnterpriseApi ionosEnterpriseApi;

    static {
        try {
            ionosEnterpriseApi = new IonosEnterpriseApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dataCenterId;
    static String loadBalancerId;
    static String serverId;
    static String nicId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        LoadBalancer newLoadBalancer = ionosEnterpriseApi.getLoadbalancer().createLoadBalancer(dataCenterId, LoadBalancerResource.getLoadBalancer());
        assertNotNull(newLoadBalancer);
        waitTillProvisioned(newLoadBalancer.getRequestId());
        loadBalancerId = newLoadBalancer.getId();
        assertEquals(newLoadBalancer.getProperties().getName(), LoadBalancerResource.getLoadBalancer().getProperties().getName());
        assertEquals(newLoadBalancer.getProperties().getIp(), LoadBalancerResource.getLoadBalancer().getProperties().getIp());

        Server newServer = ionosEnterpriseApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        serverId = newServer.getId();

        Nic newNic = ionosEnterpriseApi.getNic().createNic(dataCenterId, serverId, NicResource.getNic());
        waitTillProvisioned(newNic.getRequestId());
        assertNotNull(newNic);
        nicId = newNic.getId();
    }

    @Test
    public void t1_getAllLoadBalancers() throws RestClientException, IOException {
        LoadBalancers loadbalancers = ionosEnterpriseApi.getLoadbalancer().getAllLoadBalancers(dataCenterId);
        assertNotNull(loadbalancers);
    }

    @Test
    public void t2_getLoadBalancer() throws RestClientException, IOException {
        LoadBalancer loadBalancer = ionosEnterpriseApi.getLoadbalancer().getLoadBalancer(dataCenterId, loadBalancerId);
        assertNotNull(loadBalancer);
        assertEquals(loadBalancerId, loadBalancer.getId());
    }

    @Test
    public void t3_updateLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LoadBalancer loadBalancer = ionosEnterpriseApi.getLoadbalancer().updateLoadBalancer(dataCenterId, loadBalancerId, LoadBalancerResource.getEditLoadBalancer().getProperties());
        assertNotNull(loadBalancer);
        assertEquals(loadBalancer.getProperties().getName(), LoadBalancerResource.getEditLoadBalancer().getProperties().getName());
    }

    @Test
    public void t4_assignNicToLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Nic nic = ionosEnterpriseApi.getNic().assignNicToLoadBalancer(dataCenterId, loadBalancerId, nicId);
        assertNotNull(nic);
        assertEquals(nic.getId(), nicId);
        waitTillProvisioned(nic.getRequestId());
    }

    @Test
    public void t5_listAssignedNics() throws RestClientException, IOException {
        Nics nics = ionosEnterpriseApi.getNic().getAllBalancedNics(dataCenterId, loadBalancerId);
        assertNotNull(nics);
    }

    @Test
    public void t6_listAssignedNic() throws RestClientException, IOException {
        Nic nic = ionosEnterpriseApi.getNic().getBalancedNic(dataCenterId, loadBalancerId, serverId, nicId);
        assertNotNull(nic);
    }

    @Test
    public void t7_deleteLoadBalancer() throws RestClientException, IOException {
        ionosEnterpriseApi.getLoadbalancer().deleteLoadBalaner(dataCenterId, loadBalancerId);
    }

    @Test
    public void t8_getFaildLoadBalancer() throws RestClientException, IOException {
        try {
            LoadBalancer loadBalancer = ionosEnterpriseApi.getLoadbalancer().getLoadBalancer(dataCenterId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t9_createFailLoadBalancer() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            LoadBalancer newLoadBalancer = ionosEnterpriseApi.getLoadbalancer().createLoadBalancer(dataCenterId, new LoadBalancer());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }


    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
