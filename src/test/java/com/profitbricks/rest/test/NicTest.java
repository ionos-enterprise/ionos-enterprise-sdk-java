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
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin@stackpointcloud.com
 */
public class NicTest {

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
    private static String loadBalancerId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        DataCenter datacenter = new DataCenter();
        datacenter.getProperties().setName("SDK TEST NIC - Server");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();

        LoadBalancer loadBalancer = new LoadBalancer();
        LoadBalancer.Properties properties = new LoadBalancer().new Properties();
        properties.setName("SDK TEST NIC - LoadBalancer");
        properties.setIp("123.123.129.132");
        loadBalancer.setProperties(properties);

        LoadBalancer newLoadBalancer = profitbricksApi.getLoadbalancer().createLoadBalancer(dataCenterId, loadBalancer);
        waitTillProvisioned(newLoadBalancer.getRequestId());
        assertNotNull(newLoadBalancer);

        loadBalancerId = newLoadBalancer.getId();

        Server server = new Server();
        server.getProperties().setName("SDK TEST NIC - Server");
        server.getProperties().setRam(1024);
        server.getProperties().setCores(4);

        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, server);
        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());

        serverId = newServer.getId();

        Nic nic = new Nic();

        nic.getProperties().setName("SDK TEST NIC - Nic");
        nic.getProperties().setLan("1");
        nic.getProperties().setNat(Boolean.FALSE);

        nic.getEntities().setFirewallrules(null);

        Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, serverId, nic);
        waitTillProvisioned(newNic.getRequestId());

        assertNotNull(newNic);
        nicId = newNic.getId();
    }

    @Test
    public void orderedTests() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        getAllNics();
        getNic();
        updateNic();
        assignNicToLoadBalancer();
        listAssignedNics();
        listAssignedNic();
        unassignNicToLoadBalancer();
    }

    public void updateNic() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        Nic.Properties object = new Nic().new Properties();
        object.setName("SDK TEST NIC - Nic - changed");
        List<String> ips = new ArrayList<String>();
        ips.add("123.123.121.129");
        object.setIps(ips);
        Nic nic = profitbricksApi.getNic().updateNic(dataCenterId, serverId, nicId, object);

        assertNotNull(nic);
        assertEquals(object.getName(), nic.getProperties().getName());
        waitTillProvisioned(nic.getRequestId());
    }

    public void getNic() throws RestClientException, IOException {
        Nic nic = profitbricksApi.getNic().getNic(dataCenterId, serverId, nicId);
        assertNotNull(nic);
        assertEquals(nicId, nic.getId());
    }

    public void getAllNics() throws RestClientException, IOException {
        Nics nics = profitbricksApi.getNic().getAllNics(dataCenterId, serverId);
        assertNotNull(nics);
    }

    private void assignNicToLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Nic nic = profitbricksApi.getNic().assignNicToLoadBalancer(dataCenterId, loadBalancerId, nicId);
        assertNotNull(nic);
        assertEquals(nic.getId(), nicId);
        waitTillProvisioned(nic.getRequestId());
    }

    private void unassignNicToLoadBalancer() throws RestClientException, IOException {
        profitbricksApi.getNic().unassignNicFromLoadBalancer(dataCenterId, loadBalancerId, nicId);
    }

    private void listAssignedNics() throws RestClientException, IOException {
        Nics nics = profitbricksApi.getNic().getAllBalancedNics(dataCenterId, loadBalancerId);
        assertNotNull(nics);
    }

    private void listAssignedNic() throws RestClientException, IOException {
        Nic nic = profitbricksApi.getNic().getBalancedNic(dataCenterId, loadBalancerId, serverId, nicId);
        assertNotNull(nic);
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getNic().deleteNic(dataCenterId, serverId, nicId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

}
