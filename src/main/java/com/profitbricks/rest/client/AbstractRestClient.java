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
package com.profitbricks.rest.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jasmin@stackpointcloud.com
 */
public abstract class AbstractRestClient {

   protected final Logger logger = LoggerFactory.getLogger(getClass());

   protected final HttpClient client;

   protected final ObjectMapper mapper;

   protected final RequestInterceptor interceptor;

   protected AbstractRestClient(RestClientBuilder builder) {
      this.client = builder.client;
      this.mapper = builder.mapper;
      this.interceptor = builder.interceptor;

   }

   public static RestClientBuilder builder() {
      return new RestClientBuilder();
   }

   protected <T> T bindObject(HttpResponse response, Class<T> entityClass) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      String source = contentAsString(response);
      if (source == null) {
         return null;
      }

      T toReturn = mapper.readValue(source, entityClass);
      String location = response.getFirstHeader("Location").getValue();
      Method m = toReturn.getClass().getMethod("setRequestId", String.class);
      if (m == null) {
         return null;
      }

      m.invoke(toReturn, location);

      return toReturn;
   }

   protected <T> T bindObject(String source, Class<T> entityClass) throws IOException {
      return mapper.readValue(source, entityClass);
   }

   protected <T> List<T> bindJsonArray(String source, TypeReference<List<T>> type) throws IOException {
      return mapper.readValue(source, type);
   }

   protected JsonNode toJson(Object object) throws IOException {
      if (object instanceof JsonNode) {
         return (JsonNode) object;
      }
      return mapper.valueToTree(object);
   }

   protected Object WrappWithProperties(Object object){
      PropertiesFix wrapper = new PropertiesFix();
      wrapper.setProperties(object);

      return wrapper;
   }

   protected <T> JsonNode toJsonArray(List<T> data) throws IOException {
      return mapper.convertValue(data, JsonNode.class);
   }

   protected byte[] contentAsBytes(HttpResponse response) throws IOException {
      return IOUtils.toByteArray(response.getEntity().getContent());
   }

   protected HttpResponse execute(RequestInterceptor interceptor, HttpRequestBase request)
           throws IOException {
      request = userAgentHeader(request);
      if (interceptor != null) {
         interceptor.intercept(request);
      } else if (this.interceptor != null) {
         this.interceptor.intercept(request);
      }
      return client.execute(request);
   }

   protected HttpResponse execute(RequestInterceptor interceptor, HttpRequestBase request, int expectedStatus)
           throws RestClientException {

      String method = request.getMethod();
      String path = request.getURI().toString();
      //logger.info("Send -> " + method + " " + path);
      HttpResponse response = null;
      try {
         response = execute(interceptor, request);
      } catch (Exception e) {
         throw new RestClientException(e, response);
      }
      int status = response.getStatusLine().getStatusCode();

      if (expectedStatus != status) {
         String content = "";
         try {
            content = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
         } catch (IOException e) {

         }
         StringBuilder sb = new StringBuilder("Status of " + status);
         sb.append(" not equal to expected value of ").append(expectedStatus);
         if (!content.isEmpty()) {
            sb.append(" not equal to expected value of ").append(expectedStatus).append(" Message: ").append(content);
         }

         throw new RestClientException(sb.toString(), response);
      }
      return response;
   }

   protected int successStatus(String method) {
      if (method.equalsIgnoreCase("POST")) {
         return 201;
      }
      return 200;
   }

   protected String appendParams(String path, Map<String, String> params) {
      if (params != null) {
         return path + queryString(params);
      }
      return path;
   }

   protected String queryString(Map<String, String> params) {
      if (params == null || params.isEmpty()) {
         return "";
      }
      StringBuilder sb = new StringBuilder("?");
      int i = 0;
      for (Map.Entry<String, String> entry : params.entrySet()) {
         String key = entry.getKey();
         String value = entry.getValue();
         if (i > 0) {
            sb.append("&");
         }
         try {
            sb.append(key).append("=").append(URLEncoder.encode(value, Charsets.UTF_8.toString()));
         } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
         }
         i++;
      }
      return sb.toString();
   }

   protected <T extends HttpUriRequest> T contentTypeJson(T request) {
      request.addHeader("Content-Type", "application/json");
      return request;
   }

   protected <T extends HttpUriRequest> T userAgentHeader(T request) {
      request.addHeader("User-Agent", "profitbricks-sdk-java/3.0.0");
      return request;
   }

   protected <T extends HttpUriRequest> T contentTypePartialJson(T request) {
      request.addHeader("Content-Type", "application/json");
      return request;
   }

   protected <T extends HttpUriRequest> T contentTypeUrlEncoded(T request) {
      request.addHeader("Content-Type", "application/x-www-form-urlencoded");
      return request;
   }

   protected HttpPost newHttpPost(String url) {
      return new HttpPost(url);
   }

   protected HttpGet newHttpGet(String url) {
      return new HttpGet(url);
   }

   protected HttpPut newHttpPut(String url) {
      return new HttpPut(url);
   }

   protected HttpPatch newHttpPatch(String url) {
      return new HttpPatch(url);
   }

   protected HttpDelete newHttpDelete(String url) {
      return new HttpDelete(url);
   }

   public String contentAsString(HttpResponse response) throws IOException {
      if (response != null && response.getEntity() != null) {
         return IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
      }
      return null;
   }


   public class PropertiesFix{
      private Object properties;

      public Object getProperties() {
         return properties;
      }

      public void setProperties(Object properties) {
         this.properties = properties;
      }
   }
}
