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
import com.ionosenterprise.rest.test.resource.LabelResource;
import com.ionosenterprise.rest.test.resource.ServerResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

/**
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerTest extends BaseTest {

    private static String dataCenterId;
    private static String serverId;
    private static String labelId;

    @BeforeClass
    public static void t1_createServer() throws RestClientException, IOException, InterruptedException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Server newServer = ionosEnterpriseApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        serverId = newServer.getId();
        waitTillProvisioned(newServer.getRequestId());
    }

    @Test
    public void t2_GetAllServers() throws RestClientException, IOException {
        Servers servers = ionosEnterpriseApi.getServer().getAllServers(dataCenterId);
        assertNotNull(servers);
    }

    @Test
    public void t3_GetServer() throws RestClientException, IOException {
        Server server = ionosEnterpriseApi.getServer().getServer(dataCenterId, serverId);
        assertNotNull(server);
    }

    @Test
    public void t4_UpdateServer() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        String newName = ServerResource.getEditServer().getProperties().getName();
        Server.Properties object = new Server().new Properties();
        object.setName(newName);

        Server updatedServer = ionosEnterpriseApi.getServer().updateServer(dataCenterId, serverId, object);
        assertEquals(newName, updatedServer.getProperties().getName());
    }

    @Test
    public void t5_StartServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().startServer(dataCenterId, serverId);
    }

    @Test
    public void t6_StopServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().stopServer(dataCenterId, serverId);
    }

    @Test
    public void t7_RebootServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().rebootServer(dataCenterId, serverId);
    }

    @Test
    public void t8_1_testCreateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        Label newLabel = ionosEnterpriseApi.getServer().createLabel(label, serverId, dataCenterId);
        assertNotNull(newLabel);
        assertEquals(newLabel.getProperties().getKey(), label.getProperties().getKey());
        assertEquals(newLabel.getProperties().getValue(), label.getProperties().getValue());
        labelId = newLabel.getId();
    }

    @Test
    public void t8_2_testGetAllLabels() throws RestClientException, IOException {
        Labels labels = ionosEnterpriseApi.getServer().getAllLabels(serverId, dataCenterId);
        assertNotNull(labels);
        assertTrue(labels.getItems().size() > 0);
    }

    @Test
    public void t8_3_testGetLabel() throws RestClientException, IOException {
        Label label = ionosEnterpriseApi.getServer().getLabel(labelId, serverId, dataCenterId);
        assertNotNull(label);

        Label.Properties properties = LabelResource.getLabel().getProperties();
        assertEquals(label.getProperties().getKey(), properties.getKey());
        assertEquals(label.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t8_4_testGelLabelFail() throws IOException {
        try {
            ionosEnterpriseApi.getServer().getLabel(CommonResource.getBadId(), serverId, dataCenterId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t8_5_testUpdateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label.Properties  properties = LabelResource.getLabelEdit().getProperties();
        Label labelUpdatde = ionosEnterpriseApi.getServer().updateLabel(labelId, properties, serverId, dataCenterId);
        assertEquals(labelUpdatde.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t8_6_testCreateLabelWithExistingKeyFail() throws NoSuchMethodException, IOException,
            IllegalAccessException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        try {
            ionosEnterpriseApi.getServer().createLabel(label, serverId, dataCenterId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getServer().deleteLabel(labelId, serverId, dataCenterId);
        ionosEnterpriseApi.getServer().deleteServer(dataCenterId, serverId);
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
