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
import com.ionosenterprise.rest.domain.Request;
import com.ionosenterprise.rest.domain.RequestStatus;
import com.ionosenterprise.rest.domain.Requests;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RequestApiTest extends BaseTest {

    private static String requestId;
    private static String dataCenterId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(
                DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        requestId = newDatacenter.getRequestId();
        dataCenterId = newDatacenter.getId();
    }

    @Test
    public void listRequests() throws RestClientException, IOException {
        Requests requests = ionosEnterpriseApi.getRequestApi().listRequests();
        assertNotNull(requests);
        requestId = requests.getItems().get(0).getId();
    }

    @Test
    public void getRequest() throws RestClientException, IOException {
        Request request = ionosEnterpriseApi.getRequestApi().getRequest(requestId);
        assertNotNull(request);
    }


    @Test
    public void getRequestStatus() throws RestClientException, IOException {
        RequestStatus request = ionosEnterpriseApi.getRequestApi().getRequestStatus(requestId);
        assertNotNull(request);
    }

    @Test
    public void getRequestFail() throws IOException {
        try{
            ionosEnterpriseApi.getRequestApi().getRequest(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}
