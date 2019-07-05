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
package com.ionosenterprise.rest.client;

import com.ionosenterprise.rest.domain.PBObject;
import org.apache.commons.io.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
/**
 * @author jasmin@stackpointcloud.com
 */
public class RestClient extends AbstractRestClient {

    public <T> T get(String path, Map<String, String> queryParams, Class<T> entityClass)
            throws RestClientException, IOException {

        HttpGet get = newHttpGet(appendParams(path, queryParams));
        HttpResponse response = execute(get, 200);
        String content = null;
        try {
            content = contentAsString(response);
        } catch (IOException e) {
            consume(response);
        }

        if (content != null) {
            return bindObject(content, entityClass);
        } else {
            return null;
        }
    }

    public void create(String path, Map<String, String> params, int expectedStatus) throws IOException,
            RestClientException {

        HttpPost post = contentTypeUrlEncoded(newHttpPost(path));
        HttpEntity entity = new StringEntity(queryString(params).substring(1), Charsets.UTF_8);
        post.setEntity(entity);
        HttpResponse response = execute(post, expectedStatus);
        consume(response);
    }

    public <T> T create(String path, Map<String, String> params, Class<T> entityClass, int expectedStatus)
            throws IOException, RestClientException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        HttpPost post = contentTypeUrlEncoded(newHttpPost(path));
        HttpEntity entity = new StringEntity(queryString(params).substring(1), Charsets.UTF_8);
        post.setEntity(entity);
        HttpResponse response = execute(post, expectedStatus);
        return bindObject(response, entityClass);
    }

    public <T> T create(String path, T object, Class<T> entityClass, int expectedStatus)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        HttpPost post = contentTypeJson(newHttpPost(path));
        HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
        post.setEntity(entity);
        HttpResponse response = execute(post, expectedStatus);
        return bindObject(response, entityClass);
    }

    public <T> T create(String path, PBObject object, Class<T> entityClass, int expectedStatus)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        HttpPost post = contentTypeJson(newHttpPost(path));
        HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
        post.setEntity(entity);
        HttpResponse response = execute(post, expectedStatus);
        if (response != null) {
            return bindObject(response, entityClass);
        } else {
            return null;
        }
    }

    public void execute(String path, int expectedStatus) throws RestClientException,
            IOException {

        HttpPost post = newHttpPost(path);
        HttpResponse response = execute(post, expectedStatus);
        consume(response);
    }

    public void delete(String path, int expectedStatus) throws RestClientException, IOException {
        HttpDelete delete = newHttpDelete(path);
        consume(execute(delete, expectedStatus));
    }

    public <T> T update(String path, Object object, Class<T> entityClass, int expectedStatus)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        HttpPatch patch = contentTypePartialJson(newHttpPatch(path));
        HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
        patch.setEntity(entity);
        HttpResponse response = execute(patch, expectedStatus);
        if (response != null) {
            return bindObject(response, entityClass);
        } else {
            return null;
        }
    }

    public <T> T put(String path, Object object, Class<T> entityClass, int expectedStatus)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        HttpPut patch = contentTypePartialJson(newHttpPut(path));
        HttpEntity entity = new StringEntity(toJson(wrappWithProperties(object)).toString(), Charsets.UTF_8);
        patch.setEntity(entity);
        HttpResponse response = execute(patch, expectedStatus);
        if (response != null) {
            return bindObject(response, entityClass);
        } else {
            return null;
        }
    }

    private void consume(HttpResponse response) throws IOException {
        if (response != null && response.getEntity() != null) {
            EntityUtils.consume(response.getEntity());
        }
    }
}
