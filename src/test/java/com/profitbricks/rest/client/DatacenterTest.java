package com.profitbricks.rest.client;

import com.profitbricks.rest.client.domain.DataCenters;
import http.rest.RestClientException;
import java.io.IOException;
import org.junit.AfterClass;
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
   public void testGetAllDatacentersTest() throws RestClientException, IOException {
      DataCenters datacenters = profitbricksApi.dataCenterApi.getAllDataCenters();
      assertNotNull(datacenters);
      assertTrue(datacenters.getId().length() > 0);
   }
}
