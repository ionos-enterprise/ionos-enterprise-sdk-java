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

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        DataCenter datacenter = new DataCenter();
        datacenter.getProperties().setName("SDK TEST SNAPSHOT - Data Center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();

        Volume volume = new Volume();

        volume.getProperties().setName("SDK TEST SNAPSHOT - Volume");
        volume.getProperties().setSize(1);
        volume.getProperties().setLicenceType(LicenceType.LINUX);
        volume.getProperties().setType("HDD");

        Volume newVolume = profitbricksApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);

        volumeId = newVolume.getId();

        waitTillProvisioned(newVolume.getRequestId());

        Snapshot snapshot = profitbricksApi.getSnapshot().createSnapshot(dataCenterId, volumeId, "SDK TEST SNAPSHOT - Snapshot", "SDK TEST Description");
        snapshotId = snapshot.getId();

        waitTillProvisioned(snapshot.getRequestId());

    }

    @Test
    public void getSnapshot() throws RestClientException, IOException {
        Snapshot snapshot = profitbricksApi.getSnapshot().getSnapshot(snapshotId);
        assertNotNull(snapshot);
    }

    @Test
    public void getAllSnapshots() throws RestClientException, IOException {
        Snapshots snapshots = profitbricksApi.getSnapshot().getAllSnapshots();
        assertNotNull(snapshots);
    }

    @Test
    public void restoreSnapshot() throws RestClientException, IOException {
        profitbricksApi.getSnapshot().restoreSnapshot(dataCenterId, volumeId, snapshotId);
    }

    @Test
    public void updateSnapshot() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        Snapshot.Properties object = new Snapshot().new Properties();
        object.setName("SDK TEST SNAPSHOT - Snapshot - changed");

        Snapshot snapshot = profitbricksApi.getSnapshot().updateSnapshot(dataCenterId, snapshotId, object);
        waitTillProvisioned(snapshot.getRequestId());
        assertEquals(snapshot.getProperties().getName(), object.getName());
        snapshotId = snapshot.getId();
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException, InterruptedException {
        Thread.sleep(80000);

        profitbricksApi.getSnapshot().deleteSnapshot(snapshotId);
        profitbricksApi.getVolume().deleteVolume(dataCenterId, volumeId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
