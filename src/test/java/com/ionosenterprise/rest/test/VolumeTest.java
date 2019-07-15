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
public class VolumeTest extends BaseTest {

    private static String dataCenterId;
    private static String serverId;
    private static String volumeId;
    private static String labelId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Server newServer = ionosEnterpriseApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        serverId = newServer.getId();
        waitTillProvisioned(newServer.getRequestId());

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(getImageId());
        Volume newVolume = ionosEnterpriseApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        volumeId = newVolume.getId();
        waitTillProvisioned(newVolume.getRequestId());
    }

    @Test
    public void t1_testGetAllVolumes() throws RestClientException, IOException {
        Volumes volumes = ionosEnterpriseApi.getVolume().getAllVolumes(dataCenterId);
        assertNotNull(volumes);
        assertTrue(volumes.getItems().size() > 0);
    }

    @Test
    public void t2_testGetVolume() throws RestClientException, IOException {
        Volume volume = ionosEnterpriseApi.getVolume().getVolume(dataCenterId, volumeId);
        assertNotNull(volume);
        Volume.Properties properties = VolumeResource.getVolume().getProperties();
        assertEquals(volume.getProperties().getName(), properties.getName());
        assertEquals(volume.getProperties().getSize(), properties.getSize());
        assertEquals(volume.getProperties().getType(), properties.getType());
        assertEquals(volume.getProperties().getAvailabilityZone(), properties.getAvailabilityZone());
    }

    @Test
    public void t3_testUpdateVolume() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException {

        Volume.Properties properties = VolumeResource.getEditVolume().getProperties();
        Volume after = ionosEnterpriseApi.getVolume().updateVolume(dataCenterId, volumeId, properties);
        assertEquals(after.getProperties().getName(), properties.getName());
        assertEquals(after.getProperties().getSize(), properties.getSize());
    }

    @Test
    public void t4_testUpdateLvsVolume() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException {

        Volume.Properties properties = VolumeResource.getEditLvsVolume().getProperties();
        Volume after = ionosEnterpriseApi.getVolume().updateVolume(dataCenterId, volumeId, properties);
        assertEquals(after.getProperties().getName(), properties.getName());
        assertEquals(after.getProperties().getCpuHotPlug(), properties.getCpuHotPlug());
        assertEquals(after.getProperties().getRamHotPlug(), properties.getRamHotPlug());
        assertEquals(after.getProperties().getNicHotPlug(), properties.getNicHotPlug());
    }

    @Test
    public void t5_testAttachVolume() throws RestClientException, IOException, InterruptedException,
            NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Volume attachedVolume = ionosEnterpriseApi.getVolume().attachVolume(dataCenterId, serverId, volumeId);
        assertNotNull(attachedVolume);
        waitTillProvisioned(attachedVolume.getRequestId());
    }

    @Test
    public void t6_testGetAllAttachedVolumes() throws RestClientException, IOException {
        Volumes volumes = ionosEnterpriseApi.getVolume().getAllVolumes(dataCenterId, serverId);
        assertNotNull(volumes);
    }

    @Test
    public void t7_testDetachVolume() throws RestClientException, IOException {
        ionosEnterpriseApi.getVolume().detachVolume(dataCenterId, serverId, volumeId);
    }

    @Test
    public void t8_testFailVolumeCreate() throws InvocationTargetException, NoSuchMethodException,
            IllegalAccessException, IOException {

        try {
            ionosEnterpriseApi.getVolume().createVolume(dataCenterId, VolumeResource.getBadVolume());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @Test
    public void t9_testGetFailVolume() throws IOException {
        try {
            ionosEnterpriseApi.getVolume().getVolume(dataCenterId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t9_1_testCreateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        Label newLabel = ionosEnterpriseApi.getVolume().createLabel(label, volumeId, dataCenterId);
        assertNotNull(newLabel);
        assertEquals(newLabel.getProperties().getKey(), label.getProperties().getKey());
        assertEquals(newLabel.getProperties().getValue(), label.getProperties().getValue());
        labelId = newLabel.getId();
    }

    @Test
    public void t9_2_testGetAllLabels() throws RestClientException, IOException {
        Labels labels = ionosEnterpriseApi.getVolume().getAllLabels(volumeId, dataCenterId);
        assertNotNull(labels);
        assertTrue(labels.getItems().size() > 0);
    }

    @Test
    public void t9_3_testGetLabel() throws RestClientException, IOException {
        Label label = ionosEnterpriseApi.getVolume().getLabel(labelId, volumeId, dataCenterId);
        assertNotNull(label);

        Label.Properties properties = LabelResource.getLabel().getProperties();
        assertEquals(label.getProperties().getKey(), properties.getKey());
        assertEquals(label.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t9_4_testGelLabelFail() throws IOException {
        try {
            ionosEnterpriseApi.getVolume().getLabel(CommonResource.getBadId(), volumeId, dataCenterId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t9_5_testUpdateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label.Properties  properties = LabelResource.getLabelEdit().getProperties();
        Label labelUpdatde = ionosEnterpriseApi.getVolume().updateLabel(labelId, properties, volumeId, dataCenterId);
        assertEquals(labelUpdatde.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t9_6_testCreateLabelWithExistingKeyFail() throws NoSuchMethodException, IOException,
            IllegalAccessException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        try {
            ionosEnterpriseApi.getVolume().createLabel(label, volumeId, dataCenterId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        ionosEnterpriseApi.getVolume().deleteLabel(labelId, volumeId, dataCenterId);
        ionosEnterpriseApi.getVolume().deleteVolume(dataCenterId, volumeId);
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
