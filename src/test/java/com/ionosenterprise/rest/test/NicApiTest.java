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
public class NicApiTest extends BaseTest {

    private static String dataCenterId;
    private static String serverId;
    private static String nicId;
    private static String loadBalancerId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        LoadBalancer newLoadBalancer = ionosEnterpriseApi.getLoadbalancerApi().createLoadBalancer(dataCenterId,
                LoadBalancerResource.getLoadBalancer());
        assertNotNull(newLoadBalancer);
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
    public void t1_getNic() throws RestClientException, IOException {
        Nic nic = ionosEnterpriseApi.getNicApi().getNic(dataCenterId, serverId, nicId);
        assertNotNull(nic);
        Nic.Properties properties = NicResource.getNic().getProperties();
        assertEquals(nic.getProperties().getName(), properties.getName());
        assertEquals(nic.getProperties().getNat(), properties.getNat());
        assertEquals(nic.getProperties().getLan(), properties.getLan());
    }

    @Test
    public void t2_getAllNics() throws RestClientException, IOException {
        Nics nics = ionosEnterpriseApi.getNicApi().getAllNics(dataCenterId, serverId);
        assertNotNull(nics);
        assertTrue(nics.getItems().size() > 0);
    }

    @Test
    public void t3_updateNic() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InterruptedException {

        Nic nic = ionosEnterpriseApi.getNicApi().updateNic(dataCenterId, serverId, nicId, NicResource.getEditNic().getProperties());
        assertNotNull(nic);
        assertEquals(nic.getProperties().getName(), NicResource.getEditNic().getProperties().getName());
        waitTillProvisioned(nic.getRequestId());
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
    public void t7_unassignNicToLoadBalancer() throws RestClientException, IOException {
        ionosEnterpriseApi.getNicApi().unassignNicFromLoadBalancer(dataCenterId, loadBalancerId, nicId);
    }

    @Test
    public void t8_getFailNic() throws IOException {
        try {
            ionosEnterpriseApi.getNicApi().getNic(dataCenterId, serverId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t9_createNicFail() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {

        try {
            ionosEnterpriseApi.getNicApi().createNic(dataCenterId, serverId, NicResource.getBadNic());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }

}
