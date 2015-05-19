package com.profitbricks.rest.client;

import com.profitbricks.sdk.ProfitbricksApi;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenter.Properties;
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.Location;
import java.io.IOException;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DatacenterTest {

   String dcId;
   ProfitbricksApi profitbricksApi;

   public DatacenterTest() {
      profitbricksApi = new ProfitbricksApi();
   }

   @Test
   public void testInstantiate() {
      DataCenter dc = new DataCenter();
      assertNotNull(dc);
   }

   @Test
   public void testGetAllDatacenters() throws RestClientException, IOException {
      DataCenters datacenters = profitbricksApi.dataCenterApi.getAllDataCenters();
      assertNotNull(datacenters);
      assertTrue(datacenters.getItems().size() > 0);
   }

   @Test
   public void testGetDatacenter() throws RestClientException, IOException {
      DataCenter datacenter = profitbricksApi.dataCenterApi.getDataCenter(dcId);
      assertNotNull(datacenter);
   }

   @Before
   public void createDataCenter() throws RestClientException, IOException {

      DataCenter datacenter = new DataCenter();

      datacenter.properties.name = "SDK TEST DC 1000";
      datacenter.properties.location = Location.US_LAS;
      datacenter.properties.description = "SDK TEST Description";

      datacenter.entities = null;
      datacenter.metadata = null;
      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dcId = newDatacenter.id;
      assertEquals(newDatacenter.properties.name, datacenter.properties.name);
   }
   
   @Test
   public void updateDataCenter()throws RestClientException, IOException{
      
   }
}
