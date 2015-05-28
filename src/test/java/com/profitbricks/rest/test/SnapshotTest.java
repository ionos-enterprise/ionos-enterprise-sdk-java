 /*
 * Copyright (c) <year>, <copyright holder>
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
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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

   static String dcId;
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
      dcId = newDatacenter.id;

      Server server = new Server();
      server.properties.name = "SDK TEST SNAPSHOT - Server";
      server.properties.ram = "1024";
      server.properties.cores = "4";

      Server newServer = profitbricksApi.serverApi.createServer(dcId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

      Volume volume = new Volume();
      volume.properties.name = "SDK TEST SNAPSHOT - Volume";
      volume.properties.size = "1024";
      volume.properties.licenceType = "LINUX";

      Volume newVolume = profitbricksApi.volumeApi.createVolume(dcId, volume);
      assertNotNull(newVolume);

      volumeId = newVolume.id;
      Thread.sleep(15000);

      Snapshot snapshot = profitbricksApi.snapshotApi.createSnapshot(dcId, volumeId, "SDK TEST SNAPSHOT - Snapshot", "SDK TEST Description");
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
      profitbricksApi.snapshotApi.restoreSnapshot(dcId, volumeId, snapshotId);
   }

   @Test
   public void updateSnapshot() throws RestClientException, IOException {
      PBObject object = new PBObject();
      object.name = "SDK TEST SNAPSHOT - Snapshot - changed";

      Snapshot snapshot = profitbricksApi.snapshotApi.updateSnapshot(dcId, snapshotId, object);

      assertEquals(snapshot.properties.name, object.name);
   }

   @AfterClass
   public static void cleanUp() throws RestClientException, IOException {
      profitbricksApi.snapshotApi.deleteSnapshot(snapshotId);
      profitbricksApi.serverApi.deleteServer(dcId, serverId);
      profitbricksApi.volumeApi.deleteVolume(dcId, volumeId);
      profitbricksApi.dataCenterApi.deleteDataCenter(dcId);
   }
}
