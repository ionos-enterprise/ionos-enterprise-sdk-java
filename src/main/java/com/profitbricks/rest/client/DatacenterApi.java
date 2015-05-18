/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client;

import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenters;
import java.io.IOException;

/**
 *
 * @author jasmin.gacic
 */
public class DatacenterApi extends ProfitbricksAPIBase {

   public DatacenterApi() {
      super("datacenters");
   }

   public DataCenters getAllDataCenters() throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat("?depth=5"), null, DataCenters.class);

   }

   DataCenter getDataCenter(String id) throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat("/").concat(id).concat("?depth=5"), null, DataCenter.class);

   }
}
