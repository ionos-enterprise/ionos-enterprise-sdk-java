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
public class IPBlockApiTest extends BaseTest {

    private static String ipBlockId;
    private static String labelId;
    private static String dataCenterId;

    @BeforeClass
    public static void t1_createIPBlock() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        IPBlock ipBlock = IpBlockResource.getIpBlock();
        IPBlock iPBlock = ionosEnterpriseApi.getIpBlockApi().createIPBlock(ipBlock);
        assertNotNull(iPBlock);
        ipBlockId = iPBlock.getId();

        assertEquals(iPBlock.getProperties().getName(), ipBlock.getProperties().getName());
        assertEquals(iPBlock.getProperties().getLocation(), ipBlock.getProperties().getLocation());
        assertEquals(iPBlock.getProperties().getSize(), ipBlock.getProperties().getSize());
    }

    @Test
    public void t2_getAllIpBlocks() throws RestClientException, IOException {
        IPBlocks iPBlocks = ionosEnterpriseApi.getIpBlockApi().getAllIPBlocks();
        assertNotNull(iPBlocks);
    }

    @Test
    public void t3_getIpBlock() throws RestClientException, IOException {
        IPBlock iPBlock = ionosEnterpriseApi.getIpBlockApi().getIPBlock(ipBlockId);
        assertNotNull(iPBlock);

        IPBlock.Properties properties = IpBlockResource.getIpBlock().getProperties();
        assertEquals(iPBlock.getProperties().getName(), properties.getName());
        assertEquals(iPBlock.getProperties().getLocation(), properties.getLocation());
        assertEquals(iPBlock.getProperties().getSize(), properties.getSize());
    }

    @Test
    public void t4_getIpBlockFail() throws IOException {
        try {
            ionosEnterpriseApi.getIpBlockApi().getIPBlock(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t5_createIpBlockFail() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {

        try {
            ionosEnterpriseApi.getIpBlockApi().createIPBlock(IpBlockResource.getBadIpBlock());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @Test
    public void t6_1_testCreateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        Label newLabel = ionosEnterpriseApi.getIpBlockApi().createLabel(label, ipBlockId);
        assertNotNull(newLabel);
        assertEquals(newLabel.getProperties().getKey(), label.getProperties().getKey());
        assertEquals(newLabel.getProperties().getValue(), label.getProperties().getValue());
        labelId = newLabel.getId();
    }

    @Test
    public void t6_2_testGetAllLabels() throws RestClientException, IOException {
        Labels labels = ionosEnterpriseApi.getIpBlockApi().getAllLabels(ipBlockId);
        assertNotNull(labels);
        assertTrue(labels.getItems().size() > 0);
    }

    @Test
    public void t6_3_testGetLabel() throws RestClientException, IOException {
        Label label = ionosEnterpriseApi.getIpBlockApi().getLabel(labelId, ipBlockId);
        assertNotNull(label);

        Label.Properties properties = LabelResource.getLabel().getProperties();
        assertEquals(label.getProperties().getKey(), properties.getKey());
        assertEquals(label.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t6_4_testGelLabelFail() throws IOException {
        try {
            ionosEnterpriseApi.getIpBlockApi().getLabel(CommonResource.getBadId(), ipBlockId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t6_5_testUpdateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label.Properties  properties = LabelResource.getLabelEdit().getProperties();
        Label labelUpdatde = ionosEnterpriseApi.getIpBlockApi().updateLabel(labelId, properties, ipBlockId);
        assertEquals(labelUpdatde.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t6_6_testCreateLabelWithExistingKeyFail() throws NoSuchMethodException, IOException,
            IllegalAccessException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        try {
            ionosEnterpriseApi.getIpBlockApi().createLabel(label, ipBlockId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @Test
    public void t7_testIpBlockConsumerDetails() throws InvocationTargetException, NoSuchMethodException,
            IllegalAccessException, RestClientException, IOException, InterruptedException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server server = ServerResource.getServer();
        Server newServer = ionosEnterpriseApi.getServerApi().createServer(dataCenterId, server);
        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        String serverId = newServer.getId();

        Lan lan = LanResource.getLan();
        Lan newLan = ionosEnterpriseApi.getLanApi().createLan(dataCenterId, lan);
        assertNotNull(newLan);
        waitTillProvisioned(newLan.getRequestId());
        String lanId = newLan.getId();

        IPBlock iPBlock = ionosEnterpriseApi.getIpBlockApi().getIPBlock(ipBlockId);
        assertNotNull(iPBlock);

        Nic nic = NicResource.getNicForLanIdAndIPBlock(lanId, iPBlock);
        Nic newNic = ionosEnterpriseApi.getNicApi().createNic(dataCenterId, serverId, nic);
        assertNotNull(newNic);
        waitTillProvisioned(newNic.getRequestId());

        IPBlock iPBlockWithConsumerDetails = ionosEnterpriseApi.getIpBlockApi().getIPBlock(ipBlockId);
        assertNotNull(iPBlockWithConsumerDetails);
        assertEquals(
                iPBlockWithConsumerDetails.getProperties().getIpConsumers().size(),
                iPBlock.getProperties().getIps().size());
        assertTrue(iPBlock.getProperties().getIps()
                .contains(iPBlockWithConsumerDetails.getProperties().getIpConsumers().get(0).getIp()));
        assertTrue(iPBlock.getProperties().getIps()
                .contains(iPBlockWithConsumerDetails.getProperties().getIpConsumers().get(1).getIp()));
    }

    @Test
    public void t8_updateIpBlock() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException {

        IPBlock ipBlock = ionosEnterpriseApi.getIpBlockApi().updateIPBlock(
                ipBlockId, IpBlockResource.getEditIpBlock().getProperties());
        assertEquals(ipBlock.getProperties().getName(), IpBlockResource.getEditIpBlock().getProperties().getName());
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException, InterruptedException {
        String requestId = ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
        waitTillProvisioned(requestId);
        ionosEnterpriseApi.getIpBlockApi().deleteLabel(labelId, ipBlockId);
        ionosEnterpriseApi.getIpBlockApi().deleteIPBlock(ipBlockId);
    }
}
