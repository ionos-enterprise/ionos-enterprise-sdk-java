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
public class SnapshotApiTest extends BaseTest {

    private static String dataCenterId;
    private static String volumeId;
    private static String snapshotId;
    private static String labelId;

    @BeforeClass
    public static void t1_createSnapshot() throws RestClientException, IOException, InterruptedException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(getImageId());
        Volume newVolume = ionosEnterpriseApi.getVolumeApi().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        volumeId = newVolume.getId();
        waitTillProvisioned(newVolume.getRequestId());

        Snapshot.Properties properties = SnapshotResource.getSnapshot().getProperties();
        Snapshot snapshot = ionosEnterpriseApi.getSnapshotApi().createSnapshot(dataCenterId, volumeId,
                properties.getName(), properties.getDescription(), properties.getLicenceType().name());
        assertNotNull(snapshot);
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
        assertEquals(snapshot.getProperties().getLicenceType(), properties.getLicenceType());
        snapshotId = snapshot.getId();
        waitTillProvisioned(snapshot.getRequestId());
    }

    @Test
    public void t2_getSnapshot() throws RestClientException, IOException {
        Snapshot snapshot = ionosEnterpriseApi.getSnapshotApi().getSnapshot(snapshotId);
        assertNotNull(snapshot);
        Snapshot.Properties properties = SnapshotResource.getSnapshot().getProperties();
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
        assertEquals(snapshot.getProperties().getLicenceType(), properties.getLicenceType());
    }

    @Test
    public void t3_getAllSnapshots() throws RestClientException, IOException {
        Snapshots snapshots = ionosEnterpriseApi.getSnapshotApi().getAllSnapshots();
        assertNotNull(snapshots);
        assertTrue(snapshots.getItems().size() > 0);
    }

    @Test
    public void t4_restoreSnapshot() throws RestClientException, IOException {
        ionosEnterpriseApi.getSnapshotApi().restoreSnapshot(dataCenterId, volumeId, snapshotId);
    }

    @Test
    public void t5_updateSnapshot() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {

        Snapshot.Properties properties = SnapshotResource.getEditSnapshot().getProperties();
        Snapshot snapshot = ionosEnterpriseApi.getSnapshotApi().updateSnapshot(snapshotId, properties);
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
    }

    @Test
    public void t6_updateLvsSnapshot() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {

        Snapshot.Properties properties = SnapshotResource.getEditLvsSnapshot().getProperties();
        Snapshot snapshot = ionosEnterpriseApi.getSnapshotApi().updateSnapshot(snapshotId, properties);
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
        assertEquals(snapshot.getProperties().getCpuHotPlug(), properties.getCpuHotPlug());
        assertEquals(snapshot.getProperties().getCpuHotUnplug(), properties.getCpuHotUnplug());
    }

    @Test
    public void t7_getSnapshotFail() throws IOException {
        try {
            ionosEnterpriseApi.getSnapshotApi().getSnapshot(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t8_createSnapshotFail() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {

        try {
            Snapshot snapshot = SnapshotResource.getSnapshot();
            ionosEnterpriseApi.getSnapshotApi().createSnapshot(dataCenterId, volumeId,
                    "",
                    snapshot.getProperties().getDescription(),
                    snapshot.getProperties().getLicenceType().name());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @Test
    public void t9_1_testCreateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        Label newLabel = ionosEnterpriseApi.getSnapshotApi().createLabel(label, snapshotId);
        assertNotNull(newLabel);
        assertEquals(newLabel.getProperties().getKey(), label.getProperties().getKey());
        assertEquals(newLabel.getProperties().getValue(), label.getProperties().getValue());
        labelId = newLabel.getId();
    }

    @Test
    public void t9_2_testGetAllLabels() throws RestClientException, IOException {
        Labels labels = ionosEnterpriseApi.getSnapshotApi().getAllLabels(snapshotId);
        assertNotNull(labels);
        assertTrue(labels.getItems().size() > 0);
    }

    @Test
    public void t9_3_testGetLabel() throws RestClientException, IOException {
        Label label = ionosEnterpriseApi.getSnapshotApi().getLabel(labelId, snapshotId);
        assertNotNull(label);

        Label.Properties properties = LabelResource.getLabel().getProperties();
        assertEquals(label.getProperties().getKey(), properties.getKey());
        assertEquals(label.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t9_4_testGelLabelFail() throws IOException {
        try {
            ionosEnterpriseApi.getSnapshotApi().getLabel(CommonResource.getBadId(), snapshotId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t9_5_testUpdateLabel() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        Label.Properties  properties = LabelResource.getLabelEdit().getProperties();
        Label labelUpdatde = ionosEnterpriseApi.getSnapshotApi().updateLabel(labelId, properties, snapshotId);
        assertEquals(labelUpdatde.getProperties().getValue(), properties.getValue());
    }

    @Test
    public void t9_6_testCreateLabelWithExistingKeyFail() throws NoSuchMethodException, IOException,
            IllegalAccessException, InvocationTargetException {

        Label label = LabelResource.getLabel();
        try {
            ionosEnterpriseApi.getSnapshotApi().createLabel(label, snapshotId);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException, InterruptedException {
        String requestId = ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
        waitTillProvisioned(requestId);
        ionosEnterpriseApi.getSnapshotApi().deleteLabel(labelId, snapshotId);
        ionosEnterpriseApi.getSnapshotApi().deleteSnapshot(snapshotId);
    }
}
