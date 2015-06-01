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
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Snapshot;
import com.profitbricks.rest.domain.Snapshots;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class SnapshotTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();

   static String dataCenterId;
   static String serverId;
   static String volumeId;
   static String snapshotId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
     
      DataCenter datacenter = new DataCenter();
      datacenter.getProperties().setName("SDK TEST SNAPSHOT - Data Center");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
      dataCenterId = newDatacenter.getId();

      Server server = new Server();
      server.getProperties().setName("SDK TEST SNAPSHOT - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.getId();

      Volume volume = new Volume();

      volume.getProperties().setName("SDK TEST SNAPSHOT - Volume");
      volume.getProperties().setSize("1024");
      volume.getProperties().setLicenceType("LINUX");

      Volume newVolume = profitbricksApi.getVolumeApi().createVolume(dataCenterId, volume);
      assertNotNull(newVolume);

      volumeId = newVolume.getId();
      Thread.sleep(15000);

      //Snapshot snapshot = profitbricksApi.getSnapshotApi().createSnapshot(dataCenterId, volumeId, "SDK TEST SNAPSHOT - Snapshot", "SDK TEST Description");
      //snapshotId = snapshot.getId();
   }

  // @Test
   public void getSnapshot() throws RestClientException, IOException {
      Snapshot snapshot = profitbricksApi.getSnapshotApi().getSnapshot(snapshotId);
      assertNotNull(snapshot);
   }

   @Test
   public void getAllSnapshots() throws RestClientException, IOException {
      Snapshots snapshots = profitbricksApi.getSnapshotApi().getAllSnapshots();
      assertNotNull(snapshots);
   }

  // @Test
   public void restoreSnapshot() throws RestClientException, IOException {
      profitbricksApi.getSnapshotApi().restoreSnapshot(dataCenterId, volumeId, snapshotId);
   }

  // @Test
   public void updateSnapshot() throws RestClientException, IOException {
      PBObject object = new PBObject();
      object.setName("SDK TEST SNAPSHOT - Snapshot - changed");

      Snapshot snapshot = profitbricksApi.getSnapshotApi().updateSnapshot(dataCenterId, snapshotId, object);

      assertEquals(snapshot.properties.name, object.getName());
   }

   @AfterClass
   public static void cleanUp() throws RestClientException, IOException {
      //profitbricksApi.getSnapshotApi().deleteSnapshot(snapshotId);
      profitbricksApi.getServerApi().deleteServer(dataCenterId, serverId);
      profitbricksApi.getVolumeApi().deleteVolume(dataCenterId, volumeId);
      profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
   }
}
