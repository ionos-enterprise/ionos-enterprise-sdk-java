/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.rest.domain.Volumes;
import static com.profitbricks.rest.test.ServerTest.profitbricksApi;
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
   public static void setUp() throws RestClientException, IOException {
      DataCenter datacenter = new DataCenter();

      datacenter.properties.name = "SDK TEST DC";
      datacenter.properties.location = Location.US_LAS;
      datacenter.properties.description = "SDK TEST Description";

      datacenter.entities = null;
      datacenter.metadata = null;
      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dcId = newDatacenter.id;

      Server server = new Server();

      server.properties.name = "SDK TEST SERVER";
      server.properties.ram = "1024";
      server.properties.cores = "4";

      server.metadata = null;
      server.entities = null;//.volumes.items.add(volume);

      Server newServer = profitbricksApi.serverApi.createServer(dcId, server);

      assertNotNull(newServer);
      serverId = newServer.id;

      Volume toCreate = new Volume();

      toCreate.properties.size = "1024";
      toCreate.properties.licenceType = "LINUX";
      Volume newVolume = profitbricksApi.volumeApi.createVolume(dcId, toCreate);

      volumeId = newVolume.id;
   }

   @AfterClass
   public static void testDeleteServer() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dcId);
   }

   @Test
   public void testGetAllVolumes() throws RestClientException, IOException {
      Volumes volumes = profitbricksApi.volumeApi.getAllVolumes(dcId);
      assertNotNull(volumes);
   }

   @Test
   public void testGetVolume() throws RestClientException, IOException, InterruptedException {
      Thread.sleep(10000);
      Volume volume = profitbricksApi.volumeApi.getVolume(dcId, volumeId);
      assertNotNull(volume);
   }

}
