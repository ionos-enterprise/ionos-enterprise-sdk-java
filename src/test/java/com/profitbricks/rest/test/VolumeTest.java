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
   static String dcId;
   static String serverId;
   static String volumeId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, InterruptedException {
      DataCenter datacenter = new DataCenter();
      datacenter.properties.name = "SDK TEST DC";
      datacenter.properties.location = Location.US_LAS;
      datacenter.properties.description = "SDK TEST Description";

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dcId = newDatacenter.id;

      Server server = new Server();
      server.properties.name = "SDK TEST SERVER";
      server.properties.ram = "1024";
      server.properties.cores = "4";

      Server newServer = profitbricksApi.serverApi.createServer(dcId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

      Volume volume = new Volume();
      volume.properties.size = "1024";
      volume.properties.licenceType = "LINUX";

      Volume newVolume = profitbricksApi.volumeApi.createVolume(dcId, volume);
      assertNotNull(newVolume);

      volumeId = newVolume.id;
      Thread.sleep(30000);

   }

   @AfterClass
   public static void testDeleteServer() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dcId);
   }

   @Test
   public void orderedTest() throws RestClientException, IOException, InterruptedException {
      testGetAllVolumes();

      testGetVolume();
      testAttachVolume();
      Thread.sleep(15000);
      testGetAllAttachedVolumes();
      testDetachVolume();
   }

   public void testGetAllVolumes() throws RestClientException, IOException {
      Volumes volumes = profitbricksApi.volumeApi.getAllVolumes(dcId);
      assertNotNull(volumes);
   }

   public void testGetAllAttachedVolumes() throws RestClientException, IOException {
      Volumes volumes = profitbricksApi.volumeApi.getAllVolumes(dcId, serverId);
      assertNotNull(volumes);
   }

   public void testGetVolume() throws RestClientException, IOException, InterruptedException {
      Volume volume = profitbricksApi.volumeApi.getVolume(dcId, volumeId);
      assertNotNull(volume);
   }

   public void testAttachVolume() throws RestClientException, IOException, InterruptedException {
      Volume attachedVolume = profitbricksApi.volumeApi.attachVolume(dcId, serverId, volumeId);
      assertNotNull(attachedVolume);
   }

   public void testDetachVolume() throws RestClientException, IOException, InterruptedException {
      profitbricksApi.volumeApi.detachVolume(dcId, serverId, volumeId);
   }
}
