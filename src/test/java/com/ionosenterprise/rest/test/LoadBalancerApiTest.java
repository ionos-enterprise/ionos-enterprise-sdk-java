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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoadBalancerApiTest extends BaseTest {

    private static String dataCenterId;
    private static String loadBalancerId;
    private static String serverId;
    private static String nicId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        LoadBalancer loadBalancer = LoadBalancerResource.getLoadBalancer();
        LoadBalancer newLoadBalancer = ionosEnterpriseApi.getLoadbalancerApi().createLoadBalancer(dataCenterId,
                loadBalancer);
        assertNotNull(newLoadBalancer);
        assertEquals(newLoadBalancer.getProperties().getName(), loadBalancer.getProperties().getName());
        assertEquals(newLoadBalancer.getProperties().getIp(), loadBalancer.getProperties().getIp());
        loadBalancerId = newLoadBalancer.getId();
        waitTillProvisioned(newLoadBalancer.getRequestId());

        Server newServer = ionosEnterpriseApi.getServerApi().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        serverId = newServer.getId();
        waitTillProvisioned(newServer.getRequestId());

        Nic newNic = ionosEnterpriseApi.getNicApi().createNic(dataCenterId, serverId, NicResource.getNic());
        assertNotNull(newNic);
        nicId = newNic.getId();
        waitTillProvisioned(newNic.getRequestId());
    }

    @Test
    public void t1_getAllLoadBalancers() throws RestClientException, IOException {
        LoadBalancers loadbalancers = ionosEnterpriseApi.getLoadbalancerApi().getAllLoadBalancers(dataCenterId);
        assertNotNull(loadbalancers);
    }

    @Test
    public void t2_getLoadBalancer() throws RestClientException, IOException {
        LoadBalancer loadBalancer = ionosEnterpriseApi.getLoadbalancerApi().getLoadBalancer(dataCenterId, loadBalancerId);
        assertNotNull(loadBalancer);
        assertEquals(loadBalancerId, loadBalancer.getId());
    }

    @Test
    public void t3_updateLoadBalancer() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        LoadBalancer.Properties properties = LoadBalancerResource.getEditLoadBalancer().getProperties();
        LoadBalancer loadBalancer = ionosEnterpriseApi.getLoadbalancerApi().updateLoadBalancer(
                dataCenterId, loadBalancerId, properties);
        assertNotNull(loadBalancer);
        assertEquals(loadBalancer.getProperties().getName(), properties.getName());
    }

    @Test
    public void t4_assignNicToLoadBalancer() throws RestClientException, IOException, InterruptedException,
            NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Nic nic = ionosEnterpriseApi.getNicApi().assignNicToLoadBalancer(dataCenterId, loadBalancerId, nicId);
        assertNotNull(nic);
        assertEquals(nic.getId(), nicId);
        waitTillProvisioned(nic.getRequestId());
    }

    @Test
    public void t5_listAssignedNics() throws RestClientException, IOException {
        Nics nics = ionosEnterpriseApi.getNicApi().getAllBalancedNics(dataCenterId, loadBalancerId);
        assertNotNull(nics);
    }

    @Test
    public void t6_listAssignedNic() throws RestClientException, IOException {
        Nic nic = ionosEnterpriseApi.getNicApi().getBalancedNic(dataCenterId, loadBalancerId, nicId);
        assertNotNull(nic);
    }

    @Test
    public void t7_deleteLoadBalancer() throws RestClientException, IOException {
        ionosEnterpriseApi.getLoadbalancerApi().deleteLoadBalaner(dataCenterId, loadBalancerId);
    }

    @Test
    public void t8_getFaildLoadBalancer() throws IOException {
        try {
            ionosEnterpriseApi.getLoadbalancerApi().getLoadBalancer(dataCenterId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t9_createFailLoadBalancer() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException
    {
        try {
            ionosEnterpriseApi.getLoadbalancerApi().createLoadBalancer(dataCenterId,
                    LoadBalancerResource.getBadLoadBalancer());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
