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
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DatacenterTest {

   static String dataCenterId;
   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();

   @BeforeClass
   public static void createDataCenter() throws RestClientException, IOException {

      DataCenter datacenter = new DataCenter();

      datacenter.getProperties().setName("SDK TEST DC - Data center");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dataCenterId = newDatacenter.getId();
      assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());
   }

   @Test
   public void testGetAllDatacenters() throws RestClientException, IOException {
      DataCenters datacenters = profitbricksApi.dataCenterApi.getAllDataCenters();
      assertNotNull(datacenters);
      assertTrue(datacenters.items.size() > 0);
   }

   @Test
   public void testGetDatacenter() throws RestClientException, IOException {
      DataCenter datacenter = profitbricksApi.dataCenterApi.getDataCenter(dataCenterId);
      assertNotNull(datacenter);
   }

   @Test
   public void updateDataCenter() throws RestClientException, IOException {
      String newName = "SDK TEST DC CHANGED";
      PBObject object = new PBObject();
      object.setName(newName);

      DataCenter updatedDataCenter = profitbricksApi.dataCenterApi.updateDataCenter(dataCenterId, object);
      assertEquals(newName, updatedDataCenter.getProperties().getName());
   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dataCenterId);
   }
}
