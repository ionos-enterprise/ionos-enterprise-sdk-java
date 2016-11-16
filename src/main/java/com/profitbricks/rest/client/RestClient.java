/*
 * Copyright (c) <year>, <copyright holder>
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
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.profitbricks.rest.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

public class RestClient extends AbstractRestClient {

   protected RestClient(RestClientBuilder builder) {
      super(builder);
   }

   protected String get(RequestInterceptor interceptor, String path, Map<String, String> queryParams,
           int expectedStatus) throws RestClientException, IOException {

      HttpGet get = newHttpGet(appendParams(path, queryParams));
      HttpResponse response = execute(interceptor, get, expectedStatus);
      String content = null;
      try {
         content = contentAsString(response);
      } catch (IOException e) {
         consume(response);
      }
      return content;
   }

   public <T> T get(RequestInterceptor interceptor, String path, Map<String, String> queryParams,
           Class<T> entityClass, int expectedStatus) throws RestClientException, IOException {
      String content = get(interceptor, path, queryParams, expectedStatus);
      if (content != null) {
         return bindObject(content, entityClass);
      } else {
         return null;
      }
   }

   public <T> List<T> get(RequestInterceptor interceptor, String path, Map<String, String> params,
           TypeReference<List<T>> type, int expectedStatus) throws RestClientException, IOException {
      String content = get(interceptor, path, params, expectedStatus);
      if (content != null) {
         return bindJsonArray(content, type);
      } else {
         return null;
      }
   }

   public <T> T get(RequestInterceptor interceptor, String path, Map<String, String> params, Class<T> entityClass)
           throws RestClientException, IOException {
      return get(interceptor, path, params, entityClass, 200);
   }

   public <T> T get(String path, Map<String, String> params, Class<T> entityClass, int expectedStatus)
           throws RestClientException, IOException {
      return get(null, path, params, entityClass, expectedStatus);
   }

   public <T> T get(String path, Map<String, String> params, Class<T> entityClass) throws RestClientException,
           IOException {
      return get(null, path, params, entityClass, 200);
   }

   public <T> List<T> get(RequestInterceptor interceptor, String path, Map<String, String> params,
           TypeReference<List<T>> type) throws RestClientException, IOException {
      return get(interceptor, path, params, type, 200);
   }

   public <T> List<T> get(String path, Map<String, String> params, TypeReference<List<T>> type, int expectedStatus)
           throws RestClientException, IOException {
      return get(null, path, params, type, expectedStatus);
   }

   public <T> List<T> get(String path, Map<String, String> params, TypeReference<List<T>> type)
           throws RestClientException, IOException {
      return get(null, path, params, type, 200);
   }

   public void create(String path, Map<String, String> params, int expectedStatus) throws IOException, RestClientException {
      HttpPost post = contentTypeUrlEncoded(newHttpPost(path));
      HttpEntity entity = new StringEntity(queryString(params).substring(1), Charsets.UTF_8);
      post.setEntity(entity);

      HttpResponse response = execute(interceptor, post, expectedStatus);
      consume(response);
   }

   public <T> T create(String path, Map<String, String> params, Class<T> entityClass, int expectedStatus) throws IOException, RestClientException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      HttpPost post = contentTypeUrlEncoded(newHttpPost(path));
      HttpEntity entity = new StringEntity(queryString(params).substring(1), Charsets.UTF_8);
      post.setEntity(entity);
      HttpResponse response = execute(interceptor, post, expectedStatus);
      return bindObject(response, entityClass);
   }

   public Header create(RequestInterceptor interceptor, String path, PBObject object, int expectedStatus)
           throws RestClientException, IOException {
      HttpPost post = contentTypeJson(newHttpPost(path));
      HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
      post.setEntity(entity);
      HttpResponse response = execute(interceptor, post, expectedStatus);
      consume(response);
      return response.getFirstHeader("Location");
   }

   public <T> T create(RequestInterceptor interceptor, String path, T object, Class<T> entityClass, int expectedStatus) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      HttpPost post = contentTypeJson(newHttpPost(path));
      HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
      post.setEntity(entity);
      HttpResponse response = execute(interceptor, post, expectedStatus);
      return bindObject(response, entityClass);
   }

   public <T> T create(RequestInterceptor interceptor, String path, PBObject object, Class<T> entityClass, int expectedStatus) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      HttpPost post = contentTypeJson(newHttpPost(path));
      HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
      post.setEntity(entity);
      HttpResponse response = execute(interceptor, post, expectedStatus);
//      String content = contentAsString(response);
      if (response != null) {
         return bindObject(response, entityClass);
      } else {
         return null;
      }
   }

   public <T> T create(String path, T object, Class<T> entityClass, int expectedStatus) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return RestClient.this.create(null, path, object, entityClass, expectedStatus);
   }

   public <T> T create(String path, PBObject object, Class<T> entityClass, int expectedStatus) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return RestClient.this.create(null, path, object, entityClass, expectedStatus);
   }

   public Header create(String path, PBObject object, int expectedStatus) throws RestClientException, IOException {
      return RestClient.this.create(null, path, object, expectedStatus);
   }

   public Header create(RequestInterceptor interceptor, String path, PBObject object) throws RestClientException,
           IOException {
      return RestClient.this.create(interceptor, path, object, 201);
   }

   public Header create(String path, PBObject object) throws RestClientException, IOException {
      return RestClient.this.create(null, path, object, 201);
   }

   public Header create(RequestInterceptor interceptor, String path, List<?> data, int expectedStatus)
           throws RestClientException, IOException {
      HttpPost post = contentTypeJson(newHttpPost(path));
      HttpEntity entity = new StringEntity(toJsonArray(data).toString(), Charsets.UTF_8);
      post.setEntity(entity);
      HttpResponse response = execute(interceptor, post, expectedStatus);
      consume(response);
      return response.getFirstHeader("Location");
   }

   public void execute(RequestInterceptor interceptor, String path, int expectedStatus) throws RestClientException, IOException {
      HttpPost post = newHttpPost(path);
      HttpResponse response = execute(interceptor, post, expectedStatus);
      consume(response);
   }

   public void execute(String path, int expectedStatus) throws RestClientException, IOException {
      execute(null, path, expectedStatus);
   }

   public Header create(String path, List<?> data, int expectedStatus) throws RestClientException, IOException {
      return RestClient.this.create(null, path, data, expectedStatus);
   }

   public Header create(RequestInterceptor interceptor, String path, List<?> data) throws RestClientException,
           IOException {
      return RestClient.this.create(interceptor, path, data, 201);
   }

   public Header create(String path, List<?> data) throws RestClientException, IOException {
      return RestClient.this.create(null, path, data, 201);
   }

   public void delete(RequestInterceptor interceptor, String path, int expectedStatus) throws RestClientException,
           IOException {
      HttpDelete delete = newHttpDelete(path);
      consume(execute(interceptor, delete, expectedStatus));
   }

   public void delete(RequestInterceptor interceptor, String path) throws RestClientException, IOException {
      delete(interceptor, path, 200);
   }

   public void delete(String path, int expectedStatus) throws RestClientException, IOException {
      delete(null, path, expectedStatus);
   }

   public void delete(String path) throws RestClientException, IOException {
      delete(null, path, 200);
   }

   public void update(RequestInterceptor interceptor, String path, PBObject object, int expectedStatus)
           throws RestClientException, IOException {

      HttpPut put = contentTypeJson(newHttpPut(path));
      HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
      put.setEntity(entity);
      consume(execute(interceptor, put, expectedStatus));
   }

   public <T> T update(RequestInterceptor interceptor, String path, PBObject object, Class<T> entityClass, int expectedStatus)
           throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      HttpPatch patch = contentTypePartialJson(newHttpPatch(path));
      HttpEntity entity = new StringEntity(toJson(object).toString(), Charsets.UTF_8);
      patch.setEntity(entity);
      HttpResponse response = execute(interceptor, patch, expectedStatus);
//      String content = contentAsString(response);
      if (response != null) {
         return bindObject(response, entityClass);
      } else {
         return null;
      }
   }

   public void update(RequestInterceptor interceptor, String path, PBObject object) throws RestClientException,
           IOException {
      update(interceptor, path, object, 200);
   }

   public void update(String path, PBObject object) throws RestClientException, IOException {
      update(null, path, object, 200);
   }

   public <T> T update(String path, PBObject object, Class<T> entityClass, int expectedStatus) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return update(null, path, object, entityClass, expectedStatus);
   }

   public void update(String path, PBObject object, int expectedStatus) throws RestClientException, IOException {
      update(null, path, object, expectedStatus);
   }

   public void update(RequestInterceptor interceptor, String path, List<?> data, int expectedStatus)
           throws RestClientException, IOException {

      HttpPut put = contentTypeJson(newHttpPut(path));
      HttpEntity entity = new StringEntity(toJsonArray(data).toString(), Charsets.UTF_8);
      put.setEntity(entity);
      consume(execute(interceptor, put, expectedStatus));
   }

   public void update(RequestInterceptor interceptor, String path, List<?> data) throws RestClientException,
           IOException {
      update(interceptor, path, data, 200);
   }

   public void update(String path, List<?> data) throws RestClientException, IOException {
      update(null, path, data, 200);
   }

   public void update(String path, List<?> data, int expectedStatus) throws RestClientException, IOException {
      update(null, path, data, expectedStatus);
   }

   public void consume(HttpResponse response) throws IOException {
      if (response != null && response.getEntity() != null) {
         EntityUtils.consume(response.getEntity());
      }
   }

   /*public String contentAsString(HttpResponse response) throws IOException {
    if (response != null && response.getEntity() != null)
    return IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
    return null;
    }*/
}
