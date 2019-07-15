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

import com.ionosenterprise.rest.client.RequestInterceptor;
import com.ionosenterprise.rest.client.ResourcePathBuilder;
import com.ionosenterprise.rest.client.RestClient;
import com.ionosenterprise.rest.client.RestClientBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author jasmin@stackpointcloud.com
 */
public abstract class AbstractBaseApi {

    private static final String IONOS_ENTERPRISE_API_URL = "https://api.ionos.com/cloudapi/v5/";

    private String pathFormat;

    protected RestClient client;

    public AbstractBaseApi(String pathFormat) {
        this.pathFormat = pathFormat;

        RequestInterceptor authorize = getAuthorizeRequestInterceptor(null);
        client = RestClientBuilder.create().withInterceptor(authorize).build();
    }

    /**
     * @return the ResourcePathBuilder
     */
    protected ResourcePathBuilder getResourcePathBuilder() {
        return ResourcePathBuilder.create(pathFormat, getUrlBase());
    }

    /**
     * @param pathFormat the template of the uri
     * @return the ResourcePathBuilder
     */
    protected ResourcePathBuilder getResourcePathBuilder(String pathFormat) {
        return ResourcePathBuilder.create(pathFormat, getUrlBase());
    }

    /**
     * @param credentials the credentials to set
     */
    protected void setCredentials(String credentials) {
        RequestInterceptor authorize = getAuthorizeRequestInterceptor(credentials);
        client.setInterceptor(authorize);
    }

    private String getUrlBase() {
        String urlBase;
        if (System.getenv("IONOS_ENTERPRISE_API_URL") != null) {
            urlBase = System.getenv("IONOS_ENTERPRISE_API_URL");
            if (!urlBase.endsWith("/")) {
                urlBase += ("/");
            }
        } else {
            urlBase = IONOS_ENTERPRISE_API_URL;
        }

        return urlBase;
    }

    private RequestInterceptor getAuthorizeRequestInterceptor(final String credentials) {
        return new RequestInterceptor() {
            @Override
            public void intercept(HttpRequestBase request) {
                String authorizationCredentials = credentials;
                if (authorizationCredentials == null) {
                    if (System.getenv("IONOS_ENTERPRISE_USERNAME") != null
                            && System.getenv("IONOS_ENTERPRISE_PASSWORD") != null) {
                        byte[] bytesEncoded = Base64.encodeBase64((System.getenv("IONOS_ENTERPRISE_USERNAME")
                                + ":" + System.getenv("IONOS_ENTERPRISE_PASSWORD")).getBytes());
                        authorizationCredentials = new String(bytesEncoded);
                    }
                }

                if (authorizationCredentials == null) {
                    try {
                        throw new Exception("Credentials not set.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                request.addHeader("Authorization", "Basic ".concat(authorizationCredentials));
            }
        };
    }
}
