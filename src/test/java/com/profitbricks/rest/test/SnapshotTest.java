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
   public static void setUp() throws RestClientException, IOException, InterruptedException {
      DataCenter datacenter = new DataCenter();
      datacenter.properties.name = "SDK TEST SNAPSHOT - Data Center";
      datacenter.properties.location = Location.US_LAS_DEV;
      datacenter.properties.description = "SDK TEST Description";

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dataCenterId = newDatacenter.id;

      Server server = new Server();
      server.properties.name = "SDK TEST SNAPSHOT - Server";
      server.properties.ram = "1024";
      server.properties.cores = "4";

      Server newServer = profitbricksApi.serverApi.createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

      Volume volume = new Volume();
      
      volume.getProperties().setName("SDK TEST SNAPSHOT - Volume");
      volume.getProperties().setSize("1024");
      volume.getProperties().setLicenceType("LINUX");

      Volume newVolume = profitbricksApi.volumeApi.createVolume(dataCenterId, volume);
      assertNotNull(newVolume);

      volumeId = newVolume.id;
      Thread.sleep(15000);

      Snapshot snapshot = profitbricksApi.snapshotApi.createSnapshot(dataCenterId, volumeId, "SDK TEST SNAPSHOT - Snapshot", "SDK TEST Description");
      snapshotId = snapshot.id;
   }

   @Test
   public void getSnapshot() throws RestClientException, IOException {
      Snapshot snapshot = profitbricksApi.snapshotApi.getSnapshot(snapshotId);
      assertNotNull(snapshot);
   }

   @Test
   public void getAllSnapshots() throws RestClientException, IOException {
      Snapshots snapshots = profitbricksApi.snapshotApi.getAllSnapshots();
      assertNotNull(snapshots);
   }

   @Test
   public void restoreSnapshot() throws RestClientException, IOException {
      profitbricksApi.snapshotApi.restoreSnapshot(dataCenterId, volumeId, snapshotId);
   }

   @Test
   public void updateSnapshot() throws RestClientException, IOException {
      PBObject object = new PBObject();
      object.name = "SDK TEST SNAPSHOT - Snapshot - changed";

      Snapshot snapshot = profitbricksApi.snapshotApi.updateSnapshot(dataCenterId, snapshotId, object);

      assertEquals(snapshot.properties.name, object.name);
   }

   @AfterClass
   public static void cleanUp() throws RestClientException, IOException {
      profitbricksApi.snapshotApi.deleteSnapshot(snapshotId);
      profitbricksApi.serverApi.deleteServer(dataCenterId, serverId);
      profitbricksApi.volumeApi.deleteVolume(dataCenterId, volumeId);
      profitbricksApi.dataCenterApi.deleteDataCenter(dataCenterId);
   }
}
