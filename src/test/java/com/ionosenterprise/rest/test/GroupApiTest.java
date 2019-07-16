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
import com.ionosenterprise.rest.domain.Groups;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.GroupResource;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupApiTest extends BaseTest {

    private static String groupId;

    @BeforeClass
    public static void t1_createGroup() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        Group group = GroupResource.getGroup();
        Group newGroup = ionosEnterpriseApi.getGroupApi().createGroup(group);
        assertNotNull(newGroup);
        assertEquals(newGroup.getProperties().getName(), group.getProperties().getName());
        assertEquals(newGroup.getProperties().getCreateDataCenter(), group.getProperties().getCreateDataCenter());
        assertEquals(newGroup.getProperties().getCreateSnapshot(), group.getProperties().getCreateSnapshot());
        assertEquals(newGroup.getProperties().getReserveIp(), group.getProperties().getReserveIp());
        assertEquals(newGroup.getProperties().getAccessActivityLog(), group.getProperties().getAccessActivityLog());
        assertEquals(newGroup.getProperties().getCreatePcc(), group.getProperties().getCreatePcc());
        assertEquals(newGroup.getProperties().getS3Privilege(), group.getProperties().getS3Privilege());
        assertEquals(newGroup.getProperties().getCreateBackupUnit(), group.getProperties().getCreateBackupUnit());
        assertEquals(newGroup.getProperties().getCreateInternetAccess(), group.getProperties().getCreateInternetAccess());

        groupId = newGroup.getId();
        waitTillProvisioned(newGroup.getRequestId());
    }

    @Test
    public void t2_testGetAllGroups() throws RestClientException, IOException {
        Groups groups = ionosEnterpriseApi.getGroupApi().getAllGroups();
        assertNotNull(groups);
        assertTrue(groups.getItems().size() > 0);
    }

    @Test
    public void t3_testGetGroup() throws RestClientException, IOException {
        Group group = ionosEnterpriseApi.getGroupApi().getGroup(groupId);
        assertNotNull(group);

        Group.Properties properties = GroupResource.getGroup().getProperties();
        assertEquals(group.getProperties().getName(), properties.getName());
        assertEquals(group.getProperties().getCreateDataCenter(), properties.getCreateDataCenter());
        assertEquals(group.getProperties().getCreateSnapshot(), properties.getCreateSnapshot());
        assertEquals(group.getProperties().getReserveIp(), properties.getReserveIp());
        assertEquals(group.getProperties().getAccessActivityLog(), properties.getAccessActivityLog());
        assertEquals(group.getProperties().getCreatePcc(), properties.getCreatePcc());
        assertEquals(group.getProperties().getS3Privilege(), properties.getS3Privilege());
        assertEquals(group.getProperties().getCreateBackupUnit(), properties.getCreateBackupUnit());
        assertEquals(group.getProperties().getCreateInternetAccess(), properties.getCreateInternetAccess());
    }

    @Test
    public void t4_testGetGroupFail() throws IOException {
        try {
            ionosEnterpriseApi.getGroupApi().getGroup(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t5_updateGroup() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Group.Properties properties = GroupResource.getEditGroup().getProperties();
        Group updateGroup = ionosEnterpriseApi.getGroupApi().updateGroup(groupId, properties);
        assertEquals(updateGroup.getProperties().getName(), properties.getName());
        assertEquals(updateGroup.getProperties().getCreateDataCenter(), properties.getCreateDataCenter());
    }

    @Test
    public void t6_createGroupFail() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {

        try {
            ionosEnterpriseApi.getGroupApi().createGroup(GroupResource.getBadGroup());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getGroupApi().deleteGroup(groupId);
    }

}
