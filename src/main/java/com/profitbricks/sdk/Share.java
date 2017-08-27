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
import com.profitbricks.rest.domain.Shares;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author denis@stackpointcloud.com
 */
public class Share extends ProfitbricksAPIBase{
    private String credentials;

    public Share() throws Exception {
        super("shares", "um/groups");
    }

    /**
     * Retrieve a list of Shares.
     * @param groupId The unique ID of the group.
     * @return Shares object with a list of Shares
     */
    public Shares getAllShares(String groupId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(parentResource).concat("/").concat(groupId).concat("/").concat(resource).concat(depth), null, Shares.class);
    }

    /**
     * Retrieves the attributes of a specific share
     * @param groupId The unique ID of the group.
     * @param shareId The unique ID of the share.
     * @return Share object with properties and metadata
     */
    public com.profitbricks.rest.domain.Share getShare(String groupId, String shareId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(parentResource).concat("/").concat(groupId).concat("/").concat(resource).concat("/").concat(shareId).concat(depth), null, com.profitbricks.rest.domain.Share.class);
    }

    /**
     * Deletes a specific share.
     * @param groupId The unique ID of the group.
     * @param shareId The unique ID of the share.
     */
    public void deleteShare(String groupId, String shareId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(parentResource).concat("/").concat(groupId).concat("/").concat(resource).concat("/").concat(shareId),202);
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
    public com.profitbricks.rest.domain.Share createShare(String groupId, String resourceId, com.profitbricks.rest.domain.Share share) throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return client.create(getUrlBase().concat(parentResource).concat("/").concat(groupId).concat("/").concat(resource).concat("/").concat(resourceId), share, com.profitbricks.rest.domain.Share.class, 202);
    }

    /**
     * Updates a specific share.
     * @param groupId The unique ID of the group
     * @param shareId The unique ID of the share.
     * @return Share object with properties and metadata
     */
    public com.profitbricks.rest.domain.Share updateShare(String groupId, String shareId, Object object) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.put(getUrlBase().concat(parentResource).concat("/").concat(groupId).concat("/").concat(resource).concat("/").concat(shareId), object, com.profitbricks.rest.domain.Share.class, 202);
    }
}
