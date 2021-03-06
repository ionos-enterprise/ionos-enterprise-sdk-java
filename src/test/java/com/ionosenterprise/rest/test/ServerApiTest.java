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
public class ServerApiTest extends BaseTest {

    private static String dataCenterId;
    private static String serverId;
    private static String labelId;

    @BeforeClass
    public static void t1_createServer() throws RestClientException, IOException, InterruptedException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Server newServer = ionosEnterpriseApi.getServerApi().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        serverId = newServer.getId();
        waitTillProvisioned(newServer.getRequestId());
    }

    @Test
    public void t2_GetAllServers() throws RestClientException, IOException {
        Servers servers = ionosEnterpriseApi.getServerApi().getAllServers(dataCenterId);
        assertNotNull(servers);
    }

    @Test
    public void t3_GetServer() throws RestClientException, IOException {
        Server server = ionosEnterpriseApi.getServerApi().getServer(dataCenterId, serverId);
        assertNotNull(server);
    }

    @Test
    public void t4_UpdateServer() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Server.Properties properties = ServerResource.getEditServer().getProperties();

        Server updatedServer = ionosEnterpriseApi.getServerApi().updateServer(dataCenterId, serverId, properties);
        assertEquals(properties.getName(), updatedServer.getProperties().getName());
    }

    @Test
    public void t5_StartServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServerApi().startServer(dataCenterId, serverId);
    }

    @Test
    public void t6_StopServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServerApi().stopServer(dataCenterId, serverId);
    }

    @Test
    public void t7_RebootServer() throws RestClientException, IOException {
        ionosEnterpriseApi.getServerApi().rebootServer(dataCenterId, serverId);
    }

    @Test
    public void t8_1_testCreateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        Label newLabel = ionosEnterpriseApi.getServerApi().createLabel(label, serverId, dataCenterId);
        assertNotNull(newLabel);
        assertEquals(newLabel.getProperties().getKey(), label.getProperties().getKey());
        assertEquals(newLabel.getProperties().getValue(), label.getProperties().getValue());
        labelId = newLabel.getId();
    }

    @Test
    public void t8_2_testGetAllLabels() throws RestClientException, IOException {
        Labels labels = ionosEnterpriseApi.getServerApi().getAllLabels(serverId, dataCenterId);
        assertNotNull(labels);
        assertTrue(labels.getItems().size() > 0);
    }

    @Test
    public void t8_3_testGetLabel() throws RestClientException, IOException {
        Label label = ionosEnterpriseApi.getServerApi().getLabel(labelId, serverId, dataCenterId);
        assertNotNull(label);

        Label.Properties properties = LabelResource.getLabel().getProperties();
        assertEquals(label.getProperties().getKey(), properties.getKey());
        assertEquals(label.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t8_4_testGelLabelFail() throws IOException {
        try {
            ionosEnterpriseApi.getServerApi().getLabel(CommonResource.getBadId(), serverId, dataCenterId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t8_5_testUpdateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label.Properties  properties = LabelResource.getLabelEdit().getProperties();
        Label labelUpdatde = ionosEnterpriseApi.getServerApi().updateLabel(labelId, properties, serverId, dataCenterId);
        assertEquals(labelUpdatde.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t8_6_testCreateLabelWithExistingKeyFail() throws NoSuchMethodException, IOException,
            IllegalAccessException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        try {
            ionosEnterpriseApi.getServerApi().createLabel(label, serverId, dataCenterId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getServerApi().deleteLabel(labelId, serverId, dataCenterId);
        ionosEnterpriseApi.getServerApi().deleteServer(dataCenterId, serverId);
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
