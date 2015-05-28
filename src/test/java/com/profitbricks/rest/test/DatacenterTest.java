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
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DatacenterTest {

   static String dcId;
   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();

   @BeforeClass
   public static void createDataCenter() throws RestClientException, IOException {

      DataCenter datacenter = new DataCenter();

      datacenter.properties.name = "SDK TEST DC";
      datacenter.properties.location = Location.US_LAS_DEV;
      datacenter.properties.description = "SDK TEST Description";

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dcId = newDatacenter.id;
      assertEquals(newDatacenter.properties.name, datacenter.properties.name);

   }

   @Test
   public void testGetAllDatacenters() throws RestClientException, IOException {
      DataCenters datacenters = profitbricksApi.dataCenterApi.getAllDataCenters();
      assertNotNull(datacenters);
      assertTrue(datacenters.items.size() > 0);
   }

   @Test
   public void testGetDatacenter() throws RestClientException, IOException {
      DataCenter datacenter = profitbricksApi.dataCenterApi.getDataCenter(dcId);
      assertNotNull(datacenter);
   }

   @Test
   public void updateDataCenter() throws RestClientException, IOException {
      String newName = "SDK TEST DC CHANGED";
      PBObject object = new PBObject();
      object.name = newName;

      DataCenter updatedDataCenter = profitbricksApi.dataCenterApi.updateDataCenter(dcId, object);
      assertEquals(newName, updatedDataCenter.properties.name);
   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dcId);
   }
}
