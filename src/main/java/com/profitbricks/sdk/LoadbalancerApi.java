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
import com.profitbricks.rest.domain.Helper;
import com.profitbricks.rest.domain.LoadBalancer;
import com.profitbricks.rest.domain.raw.LoadBalancerRaw;
import com.profitbricks.rest.domain.raw.LoadBalancersRaw;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class LoadbalancerApi extends ProfitbricksAPIBase {

   public LoadbalancerApi() {
      super("loadbalancers", "datacenters");
   }

   public List<LoadBalancer> getAllLoadBalancers(String dataCenterId) throws RestClientException, IOException {
      return Helper.convertLoadBalancers(client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource)
              .concat(depth), null, LoadBalancersRaw.class));
   }

   public LoadBalancer getLoadBalancer(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
      return Helper.convertLoadBalancer(client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(loadBalancerId)
              .concat(depth), null, LoadBalancerRaw.class));
   }

   public LoadBalancer createLoadBalancer(String dataCenterId, LoadBalancerRaw loadBalancer) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return Helper.convertLoadBalancer(client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), loadBalancer, LoadBalancerRaw.class, 202));
   }

   public LoadBalancer updateLoadBalancer(String dataCenterId, String loadBalancerId, PBObject loadBalancer) throws RestClientException, IOException {
      return Helper.convertLoadBalancer(client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(loadBalancerId), loadBalancer, LoadBalancerRaw.class, 202));
   }

   public void deleteLoadBalaner(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(loadBalancerId), 202);
   }
}
