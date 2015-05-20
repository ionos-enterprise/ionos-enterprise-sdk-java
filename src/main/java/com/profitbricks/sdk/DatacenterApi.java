/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.UpdateObject;
import java.io.IOException;
import org.apache.http.Header;

/**
 *
 * @author jasmin.gacic
 */
public class DatacenterApi extends ProfitbricksAPIBase {

   public DatacenterApi() {
      super("datacenters");
   }

   public DataCenters getAllDataCenters() throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat(depth), null, DataCenters.class);
   }

   public DataCenter getDataCenter(String id) throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat("/").concat(id).concat(depth), null, DataCenter.class);
   }

   public DataCenter createDataCenter(DataCenter datacenter) throws RestClientException, IOException {
      return client.create(urlBase.concat(resource), datacenter, DataCenter.class, 202);
   }

   public DataCenter updateDataCenter(String id, UpdateObject datacenter) throws RestClientException, IOException {
      return client.update(urlBase.concat(resource).concat("/").concat(id), datacenter, DataCenter.class, 202);
   }
}
