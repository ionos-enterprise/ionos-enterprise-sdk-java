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
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DatacenterTest {

    static String dataCenterId;
    static ProfitbricksApi profitbricksApi = new ProfitbricksApi();

    @BeforeClass
    public static void createDataCenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST DC - Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        List<Server> serverList = new ArrayList<Server>();
        Server server = new Server();

        server.getProperties().setName("comp test");
        server.getProperties().setCores("1");
        server.getProperties().setRam("1024");
        serverList.add(server);

        Servers servers = new Servers();
        servers.setItems(serverList);
        datacenter.getEntities().setServers(servers);

        DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);        dataCenterId = newDatacenter.getId();

        waitTillProvisioned(newDatacenter.getRequestId());

        assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());
    }

    public static void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException, IOException {
        int counter = 120;
        for (int i = 0; i < counter; i++) {
            profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
            Request request = profitbricksApi.getRequestApi().getRequest(requestId);
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
    public void testGetAllDatacenters() throws RestClientException, IOException {
        DataCenters datacenters = profitbricksApi.getDataCenterApi().getAllDataCenters();

        assertNotNull(datacenters);
        assertTrue(datacenters.getItems().size() > 0);
    }

    @Test
    public void testGetDatacenter() throws RestClientException, IOException {
        DataCenter datacenter = profitbricksApi.getDataCenterApi().getDataCenter(dataCenterId);
        assertNotNull(datacenter);
    }

    @Test
    public void updateDataCenter() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String newName = "SDK TEST DC CHANGED";
        PBObject object = new PBObject();
        object.setName(newName);

        DataCenter updatedDataCenter = profitbricksApi.getDataCenterApi().updateDataCenter(dataCenterId, object);
        assertEquals(newName, updatedDataCenter.getProperties().getName());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
