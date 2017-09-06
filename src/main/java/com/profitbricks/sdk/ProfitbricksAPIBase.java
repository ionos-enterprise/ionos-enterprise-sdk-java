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

import com.profitbricks.rest.client.RequestInterceptor;
import com.profitbricks.rest.client.RestClient;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author jasmin@stackpointcloud.com
 */
public abstract class ProfitbricksAPIBase {

    public String resource;
    public String parentResource;

    public RequestInterceptor authorize;
    public RestClient client;
    public String depth = "?depth=".concat("5");

    private String credentials;
    private String urlBase = "https://api.profitbricks.com/cloudapi/v4/";

    public ProfitbricksAPIBase(String resource, String parentResource) throws Exception {
        this.resource = resource;
        this.parentResource = parentResource;

        authorize = new RequestInterceptor() {
            @Override
            public void intercept(HttpRequestBase request) {
                if (credentials == null) {
                    if (System.getenv("PROFITBRICKS_USERNAME") != null && System.getenv("PROFITBRICKS_PASSWORD") != null) {
                        byte[] bytesEncoded = Base64.encodeBase64((System.getenv("PROFITBRICKS_USERNAME") + ":" + System.getenv("PROFITBRICKS_PASSWORD")).getBytes());
                        credentials = new String(bytesEncoded);
                    }
                }

                if (credentials == null) {
                    try {
                        throw new Exception("Credentials not set.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                request.addHeader("Authorization", "Basic ".concat(credentials));
            }
        };

        client = RestClient.builder().requestInterceptor(authorize).build();
    }

    /**
     * @return the urlBase
     */
    public String getUrlBase() {
        if (System.getenv("PROFITBRICKS_API_URL") != null) {
            urlBase = System.getenv("PROFITBRICKS_API_URL");
            if (urlBase.charAt(urlBase.length() - 1) != '/') {
                urlBase += ("/");
            }
        }
        return urlBase;
    }

    /**
     * @param urlBase the urlBase to set
     */
    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    /**
     * @param credentials the credentials to set
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
