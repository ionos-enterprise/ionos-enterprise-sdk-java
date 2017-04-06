/*
 * Copyright (c) 2017, ProfitBricks GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the ProfitBricks nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ProfitBricks GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ProfitBricks GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin@stackpointcloud.com
 */
public class LoadBalancerTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static String dataCenterId;
    static String loadBalancerId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST LOADBALANCER - Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();
        assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());
        waitTillProvisioned(newDatacenter.getRequestId());

        LoadBalancer loadBalancer = new LoadBalancer();
        LoadBalancer.Properties properties = new LoadBalancer().new Properties();
        properties.setName("SDK TEST LOADBALANCER - LoadBalancer");
        properties.setIp("123.123.123.125");
        loadBalancer.setProperties(properties);

        LoadBalancer newLoadBalancer = profitbricksApi.getLoadbalancer().createLoadBalancer(dataCenterId, loadBalancer);
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
        LoadBalancers loadbalancers = profitbricksApi.getLoadbalancer().getAllLoadBalancers(dataCenterId);
        assertNotNull(loadbalancers);
    }

    public void getLoadBalancer() throws RestClientException, IOException {
        LoadBalancer loadBalancer = profitbricksApi.getLoadbalancer().getLoadBalancer(dataCenterId, loadBalancerId);
        assertNotNull(loadBalancer);
        assertEquals(loadBalancerId, loadBalancer.getId());
    }

    private void updateLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LoadBalancer.Properties object = new LoadBalancer().new Properties();
        object.setName("SDK TEST LOADBALANCER - LoadBalancer - Changed");
        LoadBalancer loadBalancer = profitbricksApi.getLoadbalancer().updateLoadBalancer(dataCenterId, loadBalancerId, object);
        assertNotNull(loadBalancer);
        assertEquals(object.getName(), loadBalancer.getProperties().getName());
    }

    private void deleteLoadBalancer() throws RestClientException, IOException {
        profitbricksApi.getLoadbalancer().deleteLoadBalaner(dataCenterId, loadBalancerId);
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
