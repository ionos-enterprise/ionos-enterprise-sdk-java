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
import com.profitbricks.rest.domain.LoadBalancer;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.LoadBalancers;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin.gacic
 */
public class LoadbalancerApi extends ProfitbricksAPIBase {

   public LoadbalancerApi() {
      super("loadbalancers", "datacenters");
   }

   public LoadBalancers getAllLoadBalancers(String dataCenterId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource)
              .concat(depth), null, LoadBalancers.class);
   }

   public LoadBalancer getLoadBalancer(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(loadBalancerId)
              .concat(depth), null, LoadBalancer.class);
   }

   public LoadBalancer createLoadBalancer(String dataCenterId, LoadBalancer loadBalancer) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), loadBalancer, LoadBalancer.class, 202);
   }

   public LoadBalancer updateLoadBalancer(String dataCenterId, String loadBalancerId, PBObject loadBalancer) throws RestClientException, IOException,NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(loadBalancerId), loadBalancer, LoadBalancer.class, 202);
   }

   public void deleteLoadBalaner(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(loadBalancerId), 202);
   }
}
