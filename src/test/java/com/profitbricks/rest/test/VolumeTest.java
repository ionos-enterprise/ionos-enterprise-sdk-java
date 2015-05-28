/*
 * Copyright 2015 jasmin.gacic.
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
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.rest.domain.Volumes;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class VolumeTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String dataCenterId;
   static String serverId;
   static String volumeId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, InterruptedException {
      DataCenter datacenter = new DataCenter();
      datacenter.properties.name = "SDK TEST VOLUME - Data Center";
      datacenter.properties.location = Location.US_LAS_DEV;
      datacenter.properties.description = "SDK TEST Description";

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dataCenterId = newDatacenter.id;

      Server server = new Server();
      server.properties.name = "SDK TEST VOLUME - Server";
      server.properties.ram = "1024";
      server.properties.cores = "4";

      Server newServer = profitbricksApi.serverApi.createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

      Volume volume = new Volume();
      volume.getProperties().setName("SDK TEST VOLUME - Volume");
      volume.getProperties().setSize("1024");
      volume.getProperties().setLicenceType("LINUX");

      Volume newVolume = profitbricksApi.volumeApi.createVolume(dataCenterId, volume);
      assertNotNull(newVolume);

      volumeId = newVolume.id;
      Thread.sleep(1000);

   }

   @AfterClass
   public static void cleanUp() throws RestClientException, IOException {
      profitbricksApi.volumeApi.deleteVolume(dataCenterId, volumeId);
      profitbricksApi.serverApi.deleteServer(dataCenterId, serverId);
      profitbricksApi.dataCenterApi.deleteDataCenter(dataCenterId);
   }

   @Test
   public void orderedTest() throws RestClientException, IOException, InterruptedException {
      testGetAllVolumes();
      Thread.sleep(15000);
      testGetVolume();
      testAttachVolume();
      Thread.sleep(15000);
      testGetAllAttachedVolumes();
      testDetachVolume();
   }

   public void testGetAllVolumes() throws RestClientException, IOException {
      Volumes volumes = profitbricksApi.volumeApi.getAllVolumes(dataCenterId);
      assertNotNull(volumes);
   }

   public void testGetAllAttachedVolumes() throws RestClientException, IOException {
      Volumes volumes = profitbricksApi.volumeApi.getAllVolumes(dataCenterId, serverId);
      assertNotNull(volumes);
   }

   public void testGetVolume() throws RestClientException, IOException, InterruptedException {
      Volume volume = profitbricksApi.volumeApi.getVolume(dataCenterId, volumeId);
      assertNotNull(volume);
   }

   public void testAttachVolume() throws RestClientException, IOException, InterruptedException {
      Volume attachedVolume = profitbricksApi.volumeApi.attachVolume(dataCenterId, serverId, volumeId);
      assertNotNull(attachedVolume);
   }

   public void testDetachVolume() throws RestClientException, IOException, InterruptedException {
      profitbricksApi.volumeApi.detachVolume(dataCenterId, serverId, volumeId);
   }
}
