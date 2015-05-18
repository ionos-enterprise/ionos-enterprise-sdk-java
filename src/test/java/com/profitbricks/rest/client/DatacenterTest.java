package com.profitbricks.rest.client;

import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenters;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DatacenterTest {

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
      assertTrue(datacenters.getId().length() > 0);
   }

   @Test
   public void testGetDatacenter() throws RestClientException, IOException {
      DataCenter datacenter = profitbricksApi.dataCenterApi.getDataCenter("659b7137-79d1-4e64-b368-81c038cb30dd");
      assertNotNull(datacenter);      
   }
}
