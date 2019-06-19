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
import com.ionosenterprise.rest.domain.Resources;

import java.io.IOException;

/**
 * @author denis@stackpointcloud.com
 */
public class Resource extends BaseAPI {
    private String credentials;

    public Resource() throws Exception {
        super("um/resources", "");
    }

    /**
     * You can retrieve a list of all resources
     *
     * @return Resources object with a list of resources
     */
    public Resources getAllResources() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource)
                .concat(depth), null, Resources.class);
    }

    /**
     * You can retrieve a list of all resources by a type
     *
     * @return Resources object with a list of resources
     */
    public Resources getAllByType(com.ionosenterprise.rest.domain.Resource.ResourceType type) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource)
                .concat("/")
                .concat(type.name())
                .concat(depth), null, Resources.class);
    }

    /**
     * You can retrieve a resource
     * @param type The type of the specific resource.
     * @param resourceId The ID of the specific resource.
     * @return Resource object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Resource getByType(com.ionosenterprise.rest.domain.Resource.ResourceType type, String resourceId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource)
                .concat("/")
                .concat(type.name())
                .concat("/")
                .concat(resourceId)
                .concat(depth), null, com.ionosenterprise.rest.domain.Resource.class);
    }
}
