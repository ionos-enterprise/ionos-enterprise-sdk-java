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
import com.profitbricks.rest.domain.Group;
import com.profitbricks.rest.domain.User;
import com.profitbricks.rest.domain.Users;
import com.profitbricks.rest.domain.RequestStatus;
import com.profitbricks.rest.test.resource.CommonResource;
import com.profitbricks.rest.test.resource.GroupResource;
import com.profitbricks.rest.test.resource.UserResource;
import com.profitbricks.sdk.ProfitbricksApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author denis@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String userId;
    static String email;
    static String email1;
    static String groupId;

    @BeforeClass
    public static void t1_createUser() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        //Set email
        email = "no-reply" + System.currentTimeMillis() + "@example.com";
        email1 = "no-reply"+ (System.currentTimeMillis()+1) +"@example.com";

        Group newGroup = profitbricksApi.getGroup().createGroup(GroupResource.getGroup());
        groupId = newGroup.getId();

        waitTillProvisioned(newGroup.getRequestId());

        //Create a user
        User user = UserResource.getUser();
        user.getProperties().setEmail(email);

        User newUser = profitbricksApi.getUser().createUser(user);
        userId = newUser.getId();

        waitTillProvisioned(newUser.getRequestId());

        assertEquals(newUser.getProperties().getEmail(), user.getProperties().getEmail());
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
    public void t2_testGetAllUsers() throws RestClientException, IOException {
        Users users = profitbricksApi.getUser().getAllUsers();

        assertNotNull(users);
        assertTrue(users.getItems().size() > 0);
    }

    @Test
    public void t3_testGetUser() throws RestClientException, IOException {
        User user = profitbricksApi.getUser().getUser(userId);
        assertNotNull(user);
    }

    @Test
    public void t4_testGetUserFail() throws RestClientException, IOException {
        try {
            User user = profitbricksApi.getUser().getUser(CommonResource.getBadId());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_updateUser() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        User user = UserResource.getEditUser();
        user.getProperties().setEmail(email1);

        User updateUser = profitbricksApi.getUser().updateUser(userId, user.getProperties());
        assertEquals(updateUser.getProperties().getFirstname(), UserResource.getEditUser().getProperties().getFirstname());
        assertEquals(updateUser.getProperties().getLastname(), UserResource.getEditUser().getProperties().getLastname());
        assertEquals(updateUser.getProperties().getEmail(), email1);
        assertEquals(updateUser.getProperties().getAdministrator(), UserResource.getEditUser().getProperties().getAdministrator());
        assertEquals(updateUser.getProperties().getForceSecAuth(), UserResource.getEditUser().getProperties().getForceSecAuth());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getUser().deleteUser(userId);
        profitbricksApi.getGroup().deleteGroup(groupId);
    }
}
