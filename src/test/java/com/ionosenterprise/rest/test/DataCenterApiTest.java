/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
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
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.DataCenter;
import com.ionosenterprise.rest.domain.DataCenters;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataCenterApiTest extends BaseTest {

    private static String dataCenterId;

    @BeforeClass
    public static void t1_createDataCenter() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        DataCenter dataCenter = DataCenterResource.getDataCenter();
        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(dataCenter);
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        assertEquals(newDatacenter.getProperties().getName(), dataCenter.getProperties().getName());
        assertEquals(newDatacenter.getProperties().getDescription(), dataCenter.getProperties().getDescription());
        assertEquals(newDatacenter.getProperties().getLocation(), dataCenter.getProperties().getLocation());
    }

    @Test
    public void t2_testGetAllDatacenters() throws RestClientException, IOException {
        DataCenters datacenters = ionosEnterpriseApi.getDataCenterApi().getAllDataCenters();
        assertNotNull(datacenters);
        assertTrue(datacenters.getItems().size() > 0);
    }

    @Test
    public void t3_testGetDatacenter() throws RestClientException, IOException {
        DataCenter datacenter = ionosEnterpriseApi.getDataCenterApi().getDataCenter(dataCenterId);
        assertNotNull(datacenter);
        DataCenter.Properties properties = DataCenterResource.getDataCenter().getProperties();
        assertEquals(datacenter.getProperties().getName(), properties.getName());
        assertEquals(datacenter.getProperties().getDescription(), properties.getDescription());
        assertEquals(datacenter.getProperties().getLocation(), properties.getLocation());
    }

    @Test
    public void t4_testGetDatacenterFail() throws IOException {
        try {
            ionosEnterpriseApi.getDataCenterApi().getDataCenter(CommonResource.getBadId());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_testCreateDatacenterFail() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {

        try {
            ionosEnterpriseApi.getDataCenterApi().createDataCenter(DataCenterResource.getBadDataCenter());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @Test
    public void t6_updateDataCenter() throws RestClientException, IOException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        DataCenter.Properties properties = DataCenterResource.getEditDataCenter().getProperties();
        DataCenter updatedDataCenter = ionosEnterpriseApi.getDataCenterApi().updateDataCenter(dataCenterId, properties);
        assertEquals(properties.getName(), updatedDataCenter.getProperties().getName());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
