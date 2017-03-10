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
import com.profitbricks.rest.domain.Nic;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Nics;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class NicApi extends ProfitbricksAPIBase {

   public NicApi() {
      super("nics", "servers");
   }

   public Nics getAllNics(String dataCenterId, String serverId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat(parentResource).concat("/").concat(serverId).concat("/")
              .concat(resource).concat(depth), null, Nics.class);
   }

   public Nic getNic(String dataCenterId, String serverId, String nicId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat(parentResource).concat("/").concat(serverId).concat("/")
              .concat(resource).concat("/").concat(nicId).concat(depth), null, Nic.class);
   }

   public Nic createNic(String dataCenterId, String serverId, Nic nic) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat(parentResource).concat("/").concat(serverId).concat("/")
              .concat(resource), nic, Nic.class, 202);
   }

   public Nic updateNic(String dataCenterId, String serverId, String nicId, PBObject nic) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.update(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat(parentResource).concat("/").concat(serverId).concat("/")
              .concat(resource).concat("/").concat(nicId), nic, Nic.class, 202);
   }

   public void deleteNic(String dataCenterId, String serverId, String nicId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat(parentResource).concat("/").concat(serverId).concat("/")
              .concat(resource).concat("/").concat(nicId), 202);
   }

   public Nic assignNicToLoadBalancer(String dataCenterId, String loadBalancerId, String nicId) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      PBObject object = new PBObject();
      object.setId(nicId);
      return client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
              concat("balancednics"), object, Nic.class, 202);
   }

   public void unassignNicFromLoadBalancer(String dataCenterId, String loadBalancerId, String nicId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
              concat("balancednics").concat("/").concat(nicId), 202);
   }

   public Nics getAllBalancedNics(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
              concat("balancednics").concat(depth), null, Nics.class);
   }

   public Nic getBalancedNic(String dataCenterId, String loadBalancerId, String serverId, String nicId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
              concat("balancednics").concat("/").concat(nicId).concat(depth), null, Nic.class);
   }
}
