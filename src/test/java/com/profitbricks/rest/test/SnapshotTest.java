/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import com.profitbricks.sdk.ProfitbricksApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.AfterClass;

import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin.gacic
 */
public class SnapshotTest {

    static ProfitbricksApi profitbricksApi = new ProfitbricksApi();

    static String dataCenterId;
    static String volumeId;
    static String snapshotId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        DataCenter datacenter = new DataCenter();
        datacenter.getProperties().setName("SDK TEST SNAPSHOT - Data Center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();

        Volume volume = new Volume();

        volume.getProperties().setName("SDK TEST SNAPSHOT - Volume");
        volume.getProperties().setSize("1");
        volume.getProperties().setLicenceType("LINUX");
        volume.getProperties().setType("HDD");

        Volume newVolume = profitbricksApi.getVolumeApi().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);

        volumeId = newVolume.getId();

        waitTillProvisioned(newVolume.getRequestId());

        /*    Request volumeStatus = newVolume.getStatus();
         if (volumeStatus != null)
         do
         Thread.sleep(5000);
         while (volumeStatus.getMetadata().getStatus() != "DONE");
         */
        Snapshot snapshot = profitbricksApi.getSnapshotApi().createSnapshot(dataCenterId, volumeId, "SDK TEST SNAPSHOT - Snapshot", "SDK TEST Description");
        snapshotId = snapshot.getId();

        waitTillProvisioned(snapshot.getRequestId());

    }

    @Test
    public void getSnapshot() throws RestClientException, IOException {
        Snapshot snapshot = profitbricksApi.getSnapshotApi().getSnapshot(snapshotId);
        assertNotNull(snapshot);
    }

    @Test
    public void getAllSnapshots() throws RestClientException, IOException {
        Snapshots snapshots = profitbricksApi.getSnapshotApi().getAllSnapshots();
        assertNotNull(snapshots);
    }

    @Test
    public void restoreSnapshot() throws RestClientException, IOException {
        profitbricksApi.getSnapshotApi().restoreSnapshot(dataCenterId, volumeId, snapshotId);
    }

    @Test
    public void updateSnapshot() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        PBObject object = new PBObject();
        object.setName("SDK TEST SNAPSHOT - Snapshot - changed");

        Snapshot snapshot = profitbricksApi.getSnapshotApi().updateSnapshot(dataCenterId, snapshotId, object);
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), object.getName());
        snapshotId = snapshot.getId();
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException, InterruptedException {
        Thread.sleep(60000);

        profitbricksApi.getSnapshotApi().deleteSnapshot(snapshotId);
        profitbricksApi.getVolumeApi().deleteVolume(dataCenterId, volumeId);
        profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
