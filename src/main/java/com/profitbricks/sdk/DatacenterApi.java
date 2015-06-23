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
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.raw.DataCentersRaw;
import com.profitbricks.rest.domain.Helper;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class DatacenterApi extends ProfitbricksAPIBase {

   public DatacenterApi() {
      super("datacenters", "");
   }

   public List<DataCenter> getAllDataCenters() throws RestClientException, IOException {
      return Helper.convertDataCenters(client.get(getUrlBase().concat(resource).concat(depth), null, DataCentersRaw.class));
   }

   public DataCenter getDataCenter(String id) throws RestClientException, IOException {
      return Helper.convertDataCenter(client.get(getUrlBase().concat(resource).concat("/").concat(id).concat(depth), null, DataCenterRaw.class));
   }

   public DataCenter createDataCenter(DataCenterRaw datacenter) throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      return Helper.convertDataCenter(client.create(getUrlBase().concat(resource), datacenter, DataCenterRaw.class, 202));
   }

   public DataCenter updateDataCenter(String id, PBObject datacenter) throws RestClientException, IOException {
      return Helper.convertDataCenter(client.update(getUrlBase().concat(resource).concat("/").concat(id), datacenter, DataCenterRaw.class, 202));
   }

   public void deleteDataCenter(String id) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(resource).concat("/").concat(id), 202);
   }
}
