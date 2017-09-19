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
import com.profitbricks.rest.test.resource.CommonResource;
import com.profitbricks.rest.test.resource.DataCenterResource;
import com.profitbricks.sdk.ProfitbricksApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatacenterTest {

    static String dataCenterId;
    static String compositeDataCenterId;
    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void t1_createDataCenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter datacenter = DataCenterResource.getDataCenter();

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();

        waitTillProvisioned(newDatacenter.getRequestId());

        assertEquals(newDatacenter.getProperties().getName(), DataCenterResource.getDataCenter().getProperties().getName());
        assertEquals(newDatacenter.getProperties().getDescription(), DataCenterResource.getDataCenter().getProperties().getDescription());
        assertEquals(newDatacenter.getProperties().getLocation(), DataCenterResource.getDataCenter().getProperties().getLocation());
    }

    public static void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException, IOException {
        int counter = 120;
        for (int i = 0; i < counter; i++) {
            profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
            RequestStatus request = profitbricksApi.getRequest().getRequestStatus(requestId);
            TimeUnit.SECONDS.sleep(1);
            if (request.getMetadata().getStatus().equals("DONE")) {
                break;
            }
            if (request.getMetadata().getStatus().equals("FAILED")) {
                throw new IOException("The request execution has failed with following message: " + request.getMetadata().getMessage());
            }
        }
    }

    @Test
    public void t2_createCompositeDatacenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException  {

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getCompositeDataCenter());
        compositeDataCenterId = newDatacenter.getId();

        waitTillProvisioned(newDatacenter.getRequestId());


        DataCenter datacenter = profitbricksApi.getDataCenter().getDataCenter(compositeDataCenterId);

        assertEquals(datacenter.getProperties().getName(), DataCenterResource.getCompositeDataCenter().getProperties().getName());
        assertEquals(datacenter.getProperties().getDescription(), DataCenterResource.getCompositeDataCenter().getProperties().getDescription());
        assertEquals(datacenter.getProperties().getLocation(), DataCenterResource.getCompositeDataCenter().getProperties().getLocation());
        assertFalse(datacenter.getEntities().getServers().getItems().isEmpty());
        assertFalse(datacenter.getEntities().getVolumes().getItems().isEmpty());

        assertNotNull(datacenter);
    }

    @Test
    public void t3_testGetAllDatacenters() throws RestClientException, IOException {
        DataCenters datacenters = profitbricksApi.getDataCenter().getAllDataCenters();

        assertNotNull(datacenters);
        assertTrue(datacenters.getItems().size() > 0);
    }

    @Test
    public void t4_testGetDatacenter() throws RestClientException, IOException {
        DataCenter datacenter = profitbricksApi.getDataCenter().getDataCenter(dataCenterId);

        assertEquals(datacenter.getProperties().getName(), DataCenterResource.getDataCenter().getProperties().getName());
        assertEquals(datacenter.getProperties().getDescription(), DataCenterResource.getDataCenter().getProperties().getDescription());
        assertEquals(datacenter.getProperties().getLocation(), DataCenterResource.getDataCenter().getProperties().getLocation());

        assertNotNull(datacenter);
    }

    @Test
    public void t5_testGetDatacenterFail() throws RestClientException, IOException {
        try {
            DataCenter datacenter = profitbricksApi.getDataCenter().getDataCenter(CommonResource.getBadId());
            assertNotNull(datacenter);
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t6_testCreateDatacenterFail() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            DataCenter datacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getBadDataCenter());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @Test
    public void t7_updateDataCenter() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        DataCenter updatedDataCenter = profitbricksApi.getDataCenter().updateDataCenter(dataCenterId, DataCenterResource.getEditDataCenter().getProperties());
        assertEquals(DataCenterResource.getEditDataCenter().getProperties().getName(), updatedDataCenter.getProperties().getName());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
        profitbricksApi.getDataCenter().deleteDataCenter(compositeDataCenterId);
    }
}
