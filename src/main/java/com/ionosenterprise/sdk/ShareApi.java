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

import com.ionosenterprise.rest.client.RestClient;
import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Shares;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class ShareApi extends AbstractBaseApi {

    public ShareApi(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return "um/groups/%s/shares";
    }

    /**
     * Retrieve a list of Shares.
     * @param groupId The unique ID of the group.
     * @return Shares object with a list of Shares
     */
    public Shares getAllShares(String groupId) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withPathParams(groupId).withDepth().build(),
                Collections.EMPTY_MAP, Shares.class);
    }

    /**
     * Retrieves the attributes of a specific share
     * @param groupId The unique ID of the group.
     * @param shareId The unique ID of the share.
     * @return Share object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.Share getShare(String groupId, String shareId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(groupId).appendPathSegment(shareId).withDepth()
                .build(),Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.Share.class);
    }

    /**
     * Deletes a specific share.
     * @param groupId The unique ID of the group.
     * @param shareId The unique ID of the share.
     * @return a String representing the requestId
     */
    public String deleteShare(String groupId, String shareId) throws RestClientException, IOException {
        return client.delete(getResourcePathBuilder().withPathParams(groupId).appendPathSegment(shareId).build(),
                HttpStatus.SC_ACCEPTED);
    }

    /**
     * Create a single Share, you can add child items to trigger a composite provision.
     * @param groupId The unique ID of the group
     * @param  share object has the following properties:
     * <br>
     * editPrivilege= The group has permission to edit privileges on this resource.
     * <br>
     * sharePrivilege= The group has permission to share this resource.
     * @return Share object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Share createShare(String groupId, String resourceId,
                                                             com.ionosenterprise.rest.domain.Share share)
            throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {

        return client.create(getResourcePathBuilder().withPathParams(groupId).appendPathSegment(resourceId).build(),
                share, com.ionosenterprise.rest.domain.Share.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * Updates a specific share.
     * @param groupId The unique ID of the group
     * @param shareId The unique ID of the share.
     * @param shareProperties object has the following properties:
     * <br>
     * editPrivilege= The group has permission to edit privileges on this resource.
     * <br>
     * sharePrivilege= The group has permission to share this resource.
     * @return Share object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.Share updateShare(String groupId, String shareId,
            com.ionosenterprise.rest.domain.Share.Properties shareProperties)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.put(getResourcePathBuilder().withPathParams(groupId).appendPathSegment(shareId).build(),
                shareProperties, com.ionosenterprise.rest.domain.Share.class, HttpStatus.SC_ACCEPTED);
    }
}
