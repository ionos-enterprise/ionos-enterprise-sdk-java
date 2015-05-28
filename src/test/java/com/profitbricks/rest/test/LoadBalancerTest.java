/*
 * Copyright 2015 jasmin.gacic.
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
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.LoadBalancer;
import com.profitbricks.rest.domain.LoadBalancers;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class LoadBalancerTest {

   static String dataCenterId;
   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String loadBalancerId;

   @BeforeClass
   public static void createDataCenter() throws RestClientException, IOException {

      DataCenter datacenter = new DataCenter();

      datacenter.properties.name = "SDK TEST LOADBALANCER - Data center";
      datacenter.properties.location = Location.US_LAS_DEV;
      datacenter.properties.description = "SDK TEST Description";

      DataCenter newDatacenter = profitbricksApi.dataCenterApi.createDataCenter(datacenter);
      dataCenterId = newDatacenter.id;
      assertEquals(newDatacenter.properties.name, datacenter.properties.name);

      LoadBalancer loadBalancer = new LoadBalancer();
      LoadBalancer.Properties properties = new LoadBalancer.Properties();
      properties.setName("SDK TEST LOADBALANCER - LoadBalancer");
      properties.setIp("123.123.123.123");
      loadBalancer.setProperties(properties);

      LoadBalancer newLoadBalancer = profitbricksApi.loadbalancerApi.createLoadBalancer(dataCenterId, loadBalancer);
      assertNotNull(newLoadBalancer);

      loadBalancerId = newLoadBalancer.id;
   }

   @Test
   public void orderedTest() throws RestClientException, IOException, InterruptedException {
      getAllLoadBalancers();
      Thread.sleep(5000);
      getLoadBalancer();
      updateLoadBalancer();
      deleteLoadBalancer();
   }

   public void getAllLoadBalancers() throws RestClientException, IOException {
      LoadBalancers loadbalancers = profitbricksApi.loadbalancerApi.getAllLoadBalancers(dataCenterId);
      assertNotNull(loadbalancers);
   }

   public void getLoadBalancer() throws RestClientException, IOException {
      LoadBalancer loadBalancer = profitbricksApi.loadbalancerApi.getLoadBalancer(dataCenterId, loadBalancerId);
      assertNotNull(loadBalancer);
      assertEquals(loadBalancerId, loadBalancer.id);
   }

   private void updateLoadBalancer() throws RestClientException, IOException {
      PBObject object = new PBObject();
      object.name = "SDK TEST LOADBALANCER - LoadBalancer - Changed";
      LoadBalancer loadBalancer = profitbricksApi.loadbalancerApi.updateLoadBalancer(dataCenterId, loadBalancerId, object);
      assertNotNull(loadBalancer);
      assertEquals(object.name, loadBalancer.getProperties().getName());

   }

   private void deleteLoadBalancer() throws RestClientException, IOException {
      profitbricksApi.loadbalancerApi.deleteLoadBalaner(dataCenterId, loadBalancerId);
   }

   @AfterClass
   public static void cleanup() throws RestClientException, IOException {
      profitbricksApi.dataCenterApi.deleteDataCenter(dataCenterId);
   }
}
