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
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;

import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin.gacic
 */
public class LoadBalancerTest {

    static String dataCenterId;
    static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    static String loadBalancerId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST LOADBALANCER - Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();
        assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());
        waitTillProvisioned(newDatacenter.getRequestId());

        LoadBalancer loadBalancer = new LoadBalancer();
        LoadBalancer.Properties properties = new LoadBalancer.Properties();
        properties.setName("SDK TEST LOADBALANCER - LoadBalancer");
        properties.setIp("123.123.123.125");
        loadBalancer.setProperties(properties);

        LoadBalancer newLoadBalancer = profitbricksApi.getLoadbalancerApi().createLoadBalancer(dataCenterId, loadBalancer);
        assertNotNull(newLoadBalancer);
        waitTillProvisioned(newLoadBalancer.getRequestId());

        loadBalancerId = newLoadBalancer.getId();
    }

    @Test
    public void orderedTest() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        getAllLoadBalancers();
        Thread.sleep(5000);
        getLoadBalancer();
        updateLoadBalancer();
        deleteLoadBalancer();
    }

    public void getAllLoadBalancers() throws RestClientException, IOException {
        LoadBalancers loadbalancers = profitbricksApi.getLoadbalancerApi().getAllLoadBalancers(dataCenterId);
        assertNotNull(loadbalancers);
    }

    public void getLoadBalancer() throws RestClientException, IOException {
        LoadBalancer loadBalancer = profitbricksApi.getLoadbalancerApi().getLoadBalancer(dataCenterId, loadBalancerId);
        assertNotNull(loadBalancer);
        assertEquals(loadBalancerId, loadBalancer.getId());
    }

    private void updateLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        PBObject object = new PBObject();
        object.setName("SDK TEST LOADBALANCER - LoadBalancer - Changed");
        LoadBalancer loadBalancer = profitbricksApi.getLoadbalancerApi().updateLoadBalancer(dataCenterId, loadBalancerId, object);
        assertNotNull(loadBalancer);
        assertEquals(object.getName(), loadBalancer.getProperties().getName());
    }

    private void deleteLoadBalancer() throws RestClientException, IOException {
        profitbricksApi.getLoadbalancerApi().deleteLoadBalaner(dataCenterId, loadBalancerId);
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
