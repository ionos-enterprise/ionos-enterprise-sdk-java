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
import com.ionosenterprise.rest.test.resource.SnapshotResource;
import com.ionosenterprise.rest.test.resource.VolumeResource;

import static com.ionosenterprise.rest.test.DatacenterTest.waitTillProvisioned;

import com.ionosenterprise.sdk.IonosEnterpriseApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SnapshotTest extends BaseTest {

    private static String dataCenterId;
    private static String volumeId;
    private static String snapshotId;

    @BeforeClass
    public static void t1_createSnapshot() throws RestClientException, IOException, InterruptedException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(getImageId());
        Volume newVolume = ionosEnterpriseApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        volumeId = newVolume.getId();
        waitTillProvisioned(newVolume.getRequestId());

        Snapshot.Properties properties = SnapshotResource.getSnapshot().getProperties();
        Snapshot snapshot = ionosEnterpriseApi.getSnapshot().createSnapshot(dataCenterId, volumeId,
                properties.getName(), properties.getDescription());
        assertNotNull(snapshot);
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
        snapshotId = snapshot.getId();
        waitTillProvisioned(snapshot.getRequestId());
    }

    @Test
    public void t2_getSnapshot() throws RestClientException, IOException {
        Snapshot snapshot = ionosEnterpriseApi.getSnapshot().getSnapshot(snapshotId);
        assertNotNull(snapshot);
        Snapshot.Properties properties = SnapshotResource.getSnapshot().getProperties();
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
    }

    @Test
    public void t3_getAllSnapshots() throws RestClientException, IOException {
        Snapshots snapshots = ionosEnterpriseApi.getSnapshot().getAllSnapshots();
        assertNotNull(snapshots);
        assertTrue(snapshots.getItems().size() > 0);
    }

    @Test
    public void t4_restoreSnapshot() throws RestClientException, IOException {
        ionosEnterpriseApi.getSnapshot().restoreSnapshot(dataCenterId, volumeId, snapshotId);
    }

    @Test
    public void t5_updateSnapshot() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {

        Snapshot.Properties properties = SnapshotResource.getEditSnapshot().getProperties();
        Snapshot snapshot = ionosEnterpriseApi.getSnapshot().updateSnapshot(dataCenterId, snapshotId, properties);
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
    }

    @Test
    public void t6_updateLvsSnapshot() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {

        Snapshot.Properties properties = SnapshotResource.getEditLvsSnapshot().getProperties();
        Snapshot snapshot = ionosEnterpriseApi.getSnapshot().updateSnapshot(dataCenterId, snapshotId, properties);
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), properties.getName());
        assertEquals(snapshot.getProperties().getDescription(), properties.getDescription());
        assertEquals(snapshot.getProperties().getCpuHotPlug(), properties.getCpuHotPlug());
        assertEquals(snapshot.getProperties().getCpuHotUnplug(), properties.getCpuHotUnplug());
    }

    @Test
    public void t7_getSnapshotFail() throws IOException {
        try {
            ionosEnterpriseApi.getSnapshot().getSnapshot(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t8_createSnapshotFail() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {

        try {
            ionosEnterpriseApi.getSnapshot().createSnapshot(dataCenterId, volumeId, "", "");
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException, InterruptedException {
        TimeUnit.MINUTES.sleep(1);

        ionosEnterpriseApi.getSnapshot().deleteSnapshot(snapshotId);
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
