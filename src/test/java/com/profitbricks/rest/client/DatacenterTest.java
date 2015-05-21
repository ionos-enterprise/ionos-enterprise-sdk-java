package com.profitbricks.rest.client;

import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.UpdateObject;
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
   static ProfitbricksApi profitbricksApi= new ProfitbricksApi();

   @BeforeClass
   public static void createDataCenter() throws RestClientException, IOException {

      if (dcId != "") {
         DataCenter datacenter = new DataCenter();

         datacenter.properties.name = "SDK TEST DC";
         datacenter.properties.location = Location.US_LAS;
         datacenter.properties.description = "SDK TEST Description";

         datacenter.entities = null;
         datacenter.metadata = null;
         DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
         dcId = newDatacenter.id;
         assertEquals(newDatacenter.properties.name, datacenter.properties.name);
      }
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
      UpdateObject object = new UpdateObject();
      object.name = newName;

      DataCenter updatedDataCenter = profitbricksApi.dataCenterApi.updateDataCenter(dcId, object);
      assertEquals(newName, updatedDataCenter.properties.name);
   }

   @AfterClass
   public static void deleteDataCenter() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dcId);
   }
}
