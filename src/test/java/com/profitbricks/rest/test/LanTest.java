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
import com.profitbricks.rest.domain.Lan;
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.raw.LanRaw;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class LanTest {

   static String dataCenterId;
   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   private static String lanId;

   @BeforeClass
   public static void createDataCenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

      profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
      DataCenterRaw datacenter = new DataCenterRaw();

      datacenter.getProperties().setName("SDK TEST Lan - Data center");
      datacenter.getProperties().setLocation(Location.US_LAS.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
      dataCenterId = newDatacenter.getId();
      assertEquals(newDatacenter.getName(), datacenter.getProperties().getName());

      LanRaw lan = new LanRaw();

      lan.getProperties().setName("SDK TEST Lan - Lan");
      lan.getProperties().setIsPublic(false);

      Lan newLan = profitbricksApi.getLanApi().createLan(dataCenterId, lan);
      lanId = newLan.getId();
      assertNotNull(newLan);

      Thread.sleep(15000);
   }

   @Test
   public void getAllLans() throws RestClientException, IOException {
      List<Lan> lans = profitbricksApi.getLanApi().getAllLans(dataCenterId);
      assertNotNull(lans);
   }

   @Test
   public void getLan() throws RestClientException, IOException {
      Lan lan = profitbricksApi.getLanApi().getLan(dataCenterId, lanId);
      assertNotNull(lan);
      assertEquals(lan.getId(), lanId);
   }

   @Test
   public void updateLan() throws RestClientException, IOException {
      Lan updatedLan = profitbricksApi.getLanApi().updateLan(dataCenterId, lanId, Boolean.TRUE);
      assertEquals(updatedLan.getIsPublic(), true);
   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      //profitbricksApi.getLanApi().deleteLan(dataCenterId, lanId);
      profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
   }
}
