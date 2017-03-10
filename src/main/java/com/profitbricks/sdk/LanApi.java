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
import com.profitbricks.rest.domain.Lan;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Lans;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class LanApi extends ProfitbricksAPIBase {

   public LanApi() {
      super("lans", "datacenters");
   }

   public Lans getAllLans(String dataCenterId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat(depth), null, Lans.class);
   }

   public Lan getLan(String dataCenterId, String lanId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId).concat(depth), null, Lan.class);
   }

   public Lan createLan(String dataCenterId, Lan lan) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource), lan, Lan.class, 202);
   }

   public Lan updateLan(String dataCenterId, String lanId, Boolean isPublic) throws RestClientException, IOException,NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      PBObject pbObject = new PBObject();
      pbObject.setIsPublic(isPublic);
      return client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId), pbObject, Lan.class, 202);
   }

   public void deleteLan(String dataCenterId, String lanId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId), 202);
   }
}
