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
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.Request;
import com.profitbricks.rest.domain.RequestStatus;
import com.profitbricks.rest.domain.Requests;
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
public class RequestTest {

    static ProfitbricksApi profitbricksApi;
    static String requestId;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String dataCenterId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST DC - Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        requestId = newDatacenter.getRequestId();
        dataCenterId = newDatacenter.getId();
        assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());

        RequestStatus request = profitbricksApi.getRequest().getRequestStatus(requestId);
        assertNotNull(request);
    }

    @Test
    public void listRequests() throws RestClientException, IOException {
        Requests requests = profitbricksApi.getRequest().listRequests();
        assertNotNull(requests);
        requestId = requests.getItems().get(0).getId();
        Request request = profitbricksApi.getRequest().getRequest(requestId);
        assertNotNull(request);
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
