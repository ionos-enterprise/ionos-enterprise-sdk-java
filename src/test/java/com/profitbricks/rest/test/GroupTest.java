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
import com.profitbricks.sdk.ProfitbricksApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 *
 * @author denis@stackpointcloud.com
 */
public class GroupTest {
    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String groupId;

    @BeforeClass
    public static void createGroup() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        Group group = new Group();

        group.getProperties().setName("Java SDK Test");
        group.getProperties().setCreateDataCenter(true);
        group.getProperties().setCreateSnapshot(true);
        group.getProperties().setReserveIp(true);
        group.getProperties().setAccessActivityLog(true);

        Group newGroup = profitbricksApi.getGroup().createGroup(group);
        groupId = newGroup.getId();

        waitTillProvisioned(newGroup.getRequestId());

        assertEquals(newGroup.getProperties().getName(), group.getProperties().getName());
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
    public void testGetAllGroups() throws RestClientException, IOException {
        Groups groups = profitbricksApi.getGroup().getAllGroups();

        assertNotNull(groups);
        assertTrue(groups.getItems().size() > 0);
    }

    @Test
    public void testGetGroup() throws RestClientException, IOException {
        Group group = profitbricksApi.getGroup().getGroup(groupId);
        assertNotNull(group);
    }

    @Test
    public void testGetGroupFail() throws RestClientException, IOException {
        try {
            Group group = profitbricksApi.getGroup().getGroup("00000000-0000-0000-0000-000000000000");
            assertNotNull(group);
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void updateGroup() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String newName = "Java SDK Test - RENAME";
        Group.Properties object = new Group().getProperties();
        object.setName(newName);
        object.setCreateDataCenter(false);

        Group updateGroup = profitbricksApi.getGroup().updateGroup(groupId, object);
        assertEquals(newName, updateGroup.getProperties().getName());
        assertFalse( updateGroup.getProperties().getCreateDataCenter());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getGroup().deleteGroup(groupId);
    }

}
