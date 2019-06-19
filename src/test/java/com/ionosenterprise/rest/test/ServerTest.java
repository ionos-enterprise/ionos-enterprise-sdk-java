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
import com.ionosenterprise.rest.domain.DataCenter;
import com.ionosenterprise.rest.domain.Server;
import com.ionosenterprise.rest.domain.Servers;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.ServerResource;
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
public class ServerTest {

    static IonosEnterpriseApi ionosEnterpriseApi;

    static {
        try {
            ionosEnterpriseApi = new IonosEnterpriseApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    static String dataCenterId;
    static String serverId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server newServer = ionosEnterpriseApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        waitTillProvisioned(newServer.getRequestId());

        assertNotNull(newServer);
        serverId = newServer.getId();
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().deleteServer(dataCenterId, serverId);
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

    @Test
    public void t1_GetAllServers() throws RestClientException, IOException {
        Servers servers = ionosEnterpriseApi.getServer().getAllServers(dataCenterId);
        assertNotNull(servers);
    }

    @Test
    public void t2_GetServer() throws RestClientException, IOException, InterruptedException {
        Thread.sleep(5000);
        Server server = ionosEnterpriseApi.getServer().getServer(dataCenterId, serverId);
        assertNotNull(server);
    }

    @Test
    public void t3_UpdateServer() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String newName = ServerResource.getEditServer().getProperties().getName();
        Server.Properties object = new Server().new Properties();
        object.setName(newName);

        Server updatedServer = ionosEnterpriseApi.getServer().updateServer(dataCenterId, serverId, object);
        assertEquals(newName, updatedServer.getProperties().getName());
    }

    @Test
    public void t4_StartServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().startServer(dataCenterId, serverId);
    }

    @Test
    public void t5_StopServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().stopServer(dataCenterId, serverId);
    }

    @Test
    public void t6_RebootServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().rebootServer(dataCenterId, serverId);
    }
}
