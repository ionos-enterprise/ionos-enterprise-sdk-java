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
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.Groups;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author denis@stackpointcloud.com
 */
public class Group extends ProfitbricksAPIBase {
    public Group() throws Exception {
        super("um/groups", null);
    }

    /**
     * Retrieve a list of Groups.
     *
     * @return Groups object with a list of Groups
     */
    public Groups getAllGroups() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat(depth), null, Groups.class);
    }

    /**
     * Retrieves the attributes of a specific group
     *
     * @param groupId The unique ID of the group.
     * @return Group object with properties and metadata
     */
    public com.profitbricks.rest.domain.Group getGroup(String groupId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(groupId).concat(depth), null, com.profitbricks.rest.domain.Group.class);
    }

    /**
     * Deletes a specific group.
     *
     * @param groupId The unique ID of the group.
     */
    public void deleteGroup(String groupId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(resource).concat("/").concat(groupId),202);
    }

    /**
     * Create a single Group, you can add child items to trigger a composite provision.
     * @param  group object has the following properties:
     * <br>
     * name= A name that was given to the group.
     * <br>
     * createDataCenter= The group has permission to create virtual data centers.
     * <br>
     * createSnapshot= The group has permission to create snapshots.
     * <br>
     * reserveIp= The group has permission to reserve IP addresses.
     * <br>
     * accessActivityLog= The group has permission to access the activity log.
     * @return Group object with properties and metadata.
     */
    public com.profitbricks.rest.domain.Group createGroup(com.profitbricks.rest.domain.Group group) throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return client.create(getUrlBase().concat(resource), group, com.profitbricks.rest.domain.Group.class, 202);
    }

    /**
     * Updates a specific group.
     *
     * @param groupId The unique ID of the group.
     * @return Group object with properties and metadata
     */
    public com.profitbricks.rest.domain.Group updateGroup(String groupId, Object object) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.put(getUrlBase().concat(resource).concat("/").concat(groupId), object, com.profitbricks.rest.domain.Group.class, 202);
    }
}
