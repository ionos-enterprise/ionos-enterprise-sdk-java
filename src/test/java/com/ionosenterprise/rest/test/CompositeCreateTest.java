/*
 * Copyright 2017.
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
package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.DataCenter;
import com.ionosenterprise.rest.domain.RequestStatus;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.sdk.IonosEnterpriseApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Test;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class CompositeCreateTest {

    static IonosEnterpriseApi ionosEnterpriseApi;
    static String dataCenterId;

    static {
        try {
            ionosEnterpriseApi = new IonosEnterpriseApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDataCenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(DataCenterResource.getCompositeDataCenter());
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());
    }

    public void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException, IOException {
        int counter = 120;
        for (int i = 0; i < counter; i++) {
            ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));
            RequestStatus request = ionosEnterpriseApi.getRequest().getRequestStatus(requestId);
            TimeUnit.SECONDS.sleep(1);
            if (request.getMetadata().getStatus().equals("DONE")) {
                break;
            }
            if (request.getMetadata().getStatus().equals("FAILED")) {
                throw new IOException("The request execution has failed with following message: " + request.getMetadata().getMessage());
            }
        }
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
