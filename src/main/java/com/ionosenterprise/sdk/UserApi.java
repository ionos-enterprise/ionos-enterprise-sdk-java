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

package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Users;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class UserApi extends BaseApi {

    private String credentials;

    public UserApi() throws Exception {
        super("um/users","");
    }

    /**
     * Retrieve a list of Users.
     *
     * @return Users object with a list of Users
     */
    public Users getAllUsers() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat(depth), null, Users.class);
    }

    /**
     * Retrieve a list of Users for a group.
     *@param groupId The unique ID of the group
     * @return Users object with a list of Users
     */
    public Users getAllGroupUsers(String groupId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat("um/groups").concat("/").concat(groupId).concat("/").concat("users").concat(depth), null, Users.class);
    }

    /**
     * Retrieves the attributes of a specific user
     *
     * @param userId The unique ID of the user.
     * @return User object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.User getUser(String userId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(userId).concat(depth), null, com.ionosenterprise.rest.domain.User.class);
    }

    /**
     * Deletes a specific user.
     *
     * @param userId The unique ID of the user.
     */
    public void deleteUser(String userId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(resource).concat("/").concat(userId),202);
    }

    /**
     * Create a single User, you can add child items to trigger a composite provision.
     * @param  user object has the following properties:
     * <br>
     * firstname= The first name of the user.
     * <br>
     * lastname=The last name of the user.
     * <br>
     * email=The e-mail address of the user.
     * <br>
     * administrator=Indicates if the user has administrative rights.
     * <br>
     * forceSecAuth=Indicates if secure (two-factor) authentication was enabled for the user.
     * <br>
     * secAuthActive=Indicates if secure (two-factor) authentication is enabled for the user.
     * @return User object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.User createUser(com.ionosenterprise.rest.domain.User user) throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return client.create(getUrlBase().concat(resource), user, com.ionosenterprise.rest.domain.User.class, 202);
    }

    /**
     * Updates a specific user.
     *
     * @param userId The unique ID of the user.
     * @return User object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.User updateUser(String userId, com.ionosenterprise.rest.domain.User.Properties object) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.put(getUrlBase().concat(resource).concat("/").concat(userId), object, com.ionosenterprise.rest.domain.User.class, 202);
    }

    /**
     * Adds a specific user to a specific group.
     * @param groupId The unique ID of the group.
     * @param userId The unique ID of the user.
     * @return User object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.User addUserToGroup(String groupId , String userId) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupId", groupId);
        return client.create(getUrlBase().concat(resource).concat("/").concat(userId),params, com.ionosenterprise.rest.domain.User.class, 202);
    }

    /**
     * Removes a specific user from a specific group.
     * @param groupId The unique ID of the group.
     * @param userId The unique ID of the user.
     * @return User object with properties and metadata
     */
    public void removeUserFromGroup(String groupId,String userId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(parentResource).concat("/").concat(groupId).concat("/").concat(resource).concat("/").concat(userId),   202);
    }
}
