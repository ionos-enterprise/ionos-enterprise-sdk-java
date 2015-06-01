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
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin.gacic
 */
public class DatacenterApi extends ProfitbricksAPIBase {

   public DatacenterApi() {
      super("datacenters", "");
   }

   public DataCenters getAllDataCenters() throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat(depth), null, DataCenters.class);
   }

   public DataCenter getDataCenter(String id) throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat("/").concat(id).concat(depth), null, DataCenter.class);
   }

   public DataCenter createDataCenter(DataCenter datacenter) throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      return client.create(urlBase.concat(resource), datacenter, DataCenter.class, 202);
   }

   public DataCenter updateDataCenter(String id, PBObject datacenter) throws RestClientException, IOException {
      return client.update(urlBase.concat(resource).concat("/").concat(id), datacenter, DataCenter.class, 202);
   }

   public void deleteDataCenter(String id) throws RestClientException, IOException {
      client.delete(urlBase.concat(resource).concat("/").concat(id), 202);
   }
}
