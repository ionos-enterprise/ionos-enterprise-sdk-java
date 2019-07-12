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
import com.ionosenterprise.rest.domain.Group;
import com.ionosenterprise.rest.domain.SingleSignOnUrl;
import com.ionosenterprise.rest.domain.User;
import com.ionosenterprise.rest.domain.Users;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.GroupResource;
import com.ionosenterprise.rest.test.resource.UserResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

/**
 *
 * @author denis@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest extends BaseTest {

    private static String userId;
    private static String groupId;
    private static String userOfGroupId;
    private static String contractUserWithoutAdminPrivId;
    private static String contractUserWithoutAdminPrivEmail;

    @BeforeClass
    public static void t1_createUser() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        Group newGroup = ionosEnterpriseApi.getGroup().createGroup(GroupResource.getGroup());
        assertNotNull(newGroup);
        groupId = newGroup.getId();
        waitTillProvisioned(newGroup.getRequestId());

        User user = UserResource.getUser();
        User newUser = ionosEnterpriseApi.getUser().createUser(user);
        assertNotNull(newUser);
        assertEquals(newUser.getProperties().getEmail(), user.getProperties().getEmail());
        userId = newUser.getId();
        waitTillProvisioned(newUser.getRequestId());

        User userOfGroup = UserResource.getUserOfGroup();
        User newUserOfGroup = ionosEnterpriseApi.getUser().createUser(userOfGroup);
        assertNotNull(newUserOfGroup);
        assertEquals(newUserOfGroup.getProperties().getEmail(), userOfGroup.getProperties().getEmail());
        assertEquals(newUserOfGroup.getProperties().getAdministrator(), userOfGroup.getProperties().getAdministrator());
        userOfGroupId = newUserOfGroup.getId();
        waitTillProvisioned(newUserOfGroup.getRequestId());

        User contractUserWithoutAdminPriv = UserResource.getContractUserWithoutAdminPriv();
        User newContractUserWithoutAdminPriv = ionosEnterpriseApi.getUser().createUser(contractUserWithoutAdminPriv);
        assertNotNull(newContractUserWithoutAdminPriv);
        assertEquals(newContractUserWithoutAdminPriv.getProperties().getEmail(),
                contractUserWithoutAdminPriv.getProperties().getEmail());
        contractUserWithoutAdminPrivId = newContractUserWithoutAdminPriv.getId();
        contractUserWithoutAdminPrivEmail = newContractUserWithoutAdminPriv.getProperties().getEmail();
        waitTillProvisioned(newContractUserWithoutAdminPriv.getRequestId());
    }

    @Test
    public void t2_testGetAllUsers() throws RestClientException, IOException {
        Users users = ionosEnterpriseApi.getUser().getAllUsers();
        assertNotNull(users);
        assertTrue(users.getItems().size() > 0);
    }

    @Test
    public void t3_testGetUser() throws RestClientException, IOException {
        User user = ionosEnterpriseApi.getUser().getUser(userId);
        assertNotNull(user);
        assertNotNull(user.getProperties());
        assertNotNull(user.getProperties().getFirstname());
        assertNotNull(user.getProperties().getLastname());
        assertNotNull(user.getProperties().getEmail());
        assertNotNull(user.getProperties().getS3CanonicalUserId());
    }

    @Test
    public void t31_testGetSSOUrl() throws RestClientException, IOException {
        SingleSignOnUrl ssoUrl = ionosEnterpriseApi.getUser().getSSOUrl(userId);
        assertNotNull(ssoUrl);
        assertNotNull(ssoUrl.getSsoUrl());
    }

    @Test
    public void t4_testGetUserFail() throws IOException {
        try {
            ionosEnterpriseApi.getUser().getUser(CommonResource.getBadId());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_updateUserAndSetAsInactive() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        User user = UserResource.getEditUser();
        user.getProperties().setActive(false);

        User updateUser = ionosEnterpriseApi.getUser().updateUser(userId, user.getProperties());
        assertEquals(updateUser.getProperties().getFirstname(), user.getProperties().getFirstname());
        assertEquals(updateUser.getProperties().getLastname(), user.getProperties().getLastname());
        assertEquals(updateUser.getProperties().getEmail(), user.getProperties().getEmail());
        assertEquals(updateUser.getProperties().getAdministrator(), user.getProperties().getAdministrator());
        assertEquals(updateUser.getProperties().getForceSecAuth(), user.getProperties().getForceSecAuth());
        assertEquals(updateUser.getProperties().getActive(), user.getProperties().getActive());
    }

    @Test
    public void t6_addUserToGroup() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException {

        User user = ionosEnterpriseApi.getUser().addUserToGroup(groupId, userOfGroupId);

        Group group = ionosEnterpriseApi.getGroup().getGroup(groupId);
        assertNotNull(group);
        assertNotNull(group.getEntities());
        assertNotNull(group.getEntities().getUsers());
        assertTrue(group.getEntities().getUsers().getItems().size() > 0);
        assertEquals(group.getEntities().getUsers().getItems().get(0).getProperties().getEmail(),
                user.getProperties().getEmail());
    }

    @Test
    public void t7_getAllGroupUsers() throws RestClientException, IOException {
        Users users = ionosEnterpriseApi.getUser().getAllGroupUsers(groupId);
        assertNotNull(users);
        assertTrue(users.getItems().size() > 0);
    }

    @Test
    public void t8_removeUserFromGroup() throws RestClientException, IOException {
        ionosEnterpriseApi.getUser().removeUserFromGroup(groupId, userOfGroupId);

        Group group = ionosEnterpriseApi.getGroup().getGroup(groupId);
        assertNotNull(group);
        assertNotNull(group.getEntities());
        assertNotNull(group.getEntities().getUsers());
        assertTrue(group.getEntities().getUsers().getItems().size() == 0);
    }

    @Test
    public void t9_contractUserWithoutAdminPrivUpdateOwnAccount() throws Exception {

        User user = UserResource.getContractUserWithoutAdminPriv();
        user.getProperties().setEmail(contractUserWithoutAdminPrivEmail);

        setIonosEnterpriseApiCredentials(user.getProperties().getEmail(), user.getProperties().getPassword());

        String newLastname = "Edited";
        user.getProperties().setLastname(newLastname);
        String newEmail = "edited." + user.getProperties().getEmail();
        user.getProperties().setEmail(newEmail);

        User updateUser = ionosEnterpriseApi.getUser().updateUser(contractUserWithoutAdminPrivId, user.getProperties());
        assertEquals(updateUser.getProperties().getLastname(), user.getProperties().getLastname());
        contractUserWithoutAdminPrivEmail = updateUser.getProperties().getEmail();

        resetIonosEnterpriseApiCredentials();
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getUser().deleteUser(userId);
        ionosEnterpriseApi.getUser().deleteUser(userOfGroupId);
        ionosEnterpriseApi.getUser().deleteUser(contractUserWithoutAdminPrivId);
        ionosEnterpriseApi.getGroup().deleteGroup(groupId);
    }
}
