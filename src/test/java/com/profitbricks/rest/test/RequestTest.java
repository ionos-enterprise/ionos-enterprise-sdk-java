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
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Request;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class RequestTest {

    static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    private static String requestId;
    private static String dataCenterId;
    private static DataCenter newDatacenter;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials("bXVoYW1lZEBzdGFja3BvaW50Y2xvdWQuY29tOnRlc3QxMjMh");
        DataCenterRaw datacenter = new DataCenterRaw();

        datacenter.getProperties().setName("SDK TEST DC - Data center");
        datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
        datacenter.getProperties().setDescription("SDK TEST Description");

        newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
        requestId = newDatacenter.getRequestId();
        dataCenterId = newDatacenter.getId();
        assertEquals(newDatacenter.getName(), datacenter.getProperties().getName());
    }

    @Test
    public void getRequestStatus() throws RestClientException, IOException {
        Request request = profitbricksApi.getRequestApi().getRequest(requestId);
        assertNotNull(request);
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
