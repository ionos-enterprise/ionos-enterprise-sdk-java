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

import com.profitbricks.rest.test.resource.CommonResource;
import com.profitbricks.rest.test.resource.DataCenterResource;
import com.profitbricks.rest.test.resource.SnapshotResource;
import com.profitbricks.rest.test.resource.VolumeResource;
import com.profitbricks.sdk.ProfitbricksApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
public class SnapshotTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dataCenterId;
    static String volumeId;
    static String snapshotId;

    public static String getImageId() throws RestClientException, IOException {
        Images images = profitbricksApi.getImage().getAllImages();
        for (Image image : images.getItems()) {
            if (image.getProperties().getName().toLowerCase().contains("ubuntu".toLowerCase()) && image.getProperties().getLocation().equals("us/las")
                    && image.getProperties().getIsPublic() && image.getProperties().getImageType().equals("HDD")) {
                return image.getId();
            }
        }
        return "";
    }

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = newDatacenter.getId();

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(getImageId());
        Volume newVolume = profitbricksApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);

        volumeId = newVolume.getId();

        waitTillProvisioned(newVolume.getRequestId());

        Snapshot snapshot = profitbricksApi.getSnapshot().createSnapshot(dataCenterId, volumeId, SnapshotResource.getSnapshot().getProperties().getName(), SnapshotResource.getSnapshot().getProperties().getDescription());
        snapshotId = snapshot.getId();
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), SnapshotResource.getSnapshot().getProperties().getName());
        assertEquals(snapshot.getProperties().getDescription(), SnapshotResource.getSnapshot().getProperties().getDescription());
    }

    @Test
    public void t1_getSnapshot() throws RestClientException, IOException {
        Snapshot snapshot = profitbricksApi.getSnapshot().getSnapshot(snapshotId);
        assertNotNull(snapshot);
        assertEquals(snapshot.getProperties().getName(), SnapshotResource.getSnapshot().getProperties().getName());
        assertEquals(snapshot.getProperties().getDescription(), SnapshotResource.getSnapshot().getProperties().getDescription());
    }

    @Test
    public void t2_getAllSnapshots() throws RestClientException, IOException {
        Snapshots snapshots = profitbricksApi.getSnapshot().getAllSnapshots();
        assertNotNull(snapshots);
        assertTrue(snapshots.getItems().size() > 0);
    }

    @Test
    public void t3_restoreSnapshot() throws RestClientException, IOException {
        profitbricksApi.getSnapshot().restoreSnapshot(dataCenterId, volumeId, snapshotId);
    }

    @Test
    public void t4_updateSnapshot() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        Snapshot snapshot = profitbricksApi.getSnapshot().updateSnapshot(dataCenterId, snapshotId, SnapshotResource.getEditSnapshot().getProperties());
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), SnapshotResource.getEditSnapshot().getProperties().getName());
        assertEquals(snapshot.getProperties().getDescription(), SnapshotResource.getEditSnapshot().getProperties().getDescription());
    }

    @Test
    public void t5_getSnapshotFail() throws RestClientException, IOException {
        try {
            Snapshot snapshot = profitbricksApi.getSnapshot().getSnapshot(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t6_createSnapshotFail() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            Snapshot snapshot = profitbricksApi.getSnapshot().createSnapshot(dataCenterId, volumeId, "", "");
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException, InterruptedException {
        Thread.sleep(100000);

        profitbricksApi.getSnapshot().deleteSnapshot(snapshotId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
