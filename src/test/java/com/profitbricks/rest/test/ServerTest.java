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
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin@stackpointcloud.com
 */
public class ServerTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    static String dataCenterId;
    static String serverId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter datacenter = new DataCenter();
        datacenter.getProperties().setName("SDK TEST SERVER - Server");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server server = new Server();
        server.getProperties().setName("SDK TEST SERVER - Server");
        server.getProperties().setRam(1024);
        server.getProperties().setCores(1);

        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, server);
        waitTillProvisioned(newServer.getRequestId());

        assertNotNull(newServer);
        serverId = newServer.getId();
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getServer().deleteServer(dataCenterId, serverId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

    @Test
    public void testInOrder() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        testGetAllServers();
        testGetServer();
        testUpdateServer();
        testRebootServer();
        testStartServer();
        testStopServer();
    }

    public void testGetAllServers() throws RestClientException, IOException {
        Servers servers = profitbricksApi.getServer().getAllServers(dataCenterId);
        assertNotNull(servers);
    }

    public void testGetServer() throws RestClientException, IOException, InterruptedException {
        Thread.sleep(5000);
        Server server = profitbricksApi.getServer().getServer(dataCenterId, serverId);
        assertNotNull(server);
    }

    public void testUpdateServer() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String newName = "SDK TEST SERVER CHANGED";
        Server.Properties object = new Server().new Properties();
        object.setName(newName);

        Server updatedServer = profitbricksApi.getServer().updateServer(dataCenterId, serverId, object);
        assertEquals(newName, updatedServer.getProperties().getName());

    }

    public void testStartServer() throws RestClientException, IOException {
        profitbricksApi.getServer().startServer(dataCenterId, serverId);
    }

    public void testStopServer() throws RestClientException, IOException {
        profitbricksApi.getServer().stopServer(dataCenterId, serverId);

    }

    public void testRebootServer() throws RestClientException, IOException {
        profitbricksApi.getServer().rebootServer(dataCenterId, serverId);
    }
}
