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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Map;

public class RestClientUtil {

   private HttpClient httpClient;
   private ObjectMapper mapper;
   private RequestInterceptor interceptor;

   public RestClientUtil(HttpClient httpClient, RequestInterceptor interceptor, ObjectMapper mapper){
       this.httpClient = httpClient;
       this.interceptor = interceptor;
       this.mapper = mapper;
   }

   public <T> T bindObject(HttpResponse response, Class<T> entityClass) throws IOException, NoSuchMethodException,
           IllegalAccessException, IllegalArgumentException, InvocationTargetException {

       String source = contentAsString(response);
       if (source == null) {
          return null;
       }

       T toReturn = mapper.readValue(source, entityClass);
       String location = response.getFirstHeader("Location") != null
               ? response.getFirstHeader("Location").getValue() : null;
       Method m = toReturn.getClass().getMethod("setRequestId", String.class);
       if (m != null) {
          m.invoke(toReturn, location);
       }

       return toReturn;
   }

    public <T> T bindObject(String source, Class<T> entityClass) throws IOException {
        return mapper.readValue(source, entityClass);
    }

    public JsonNode toJson(Object object) {
        if (object instanceof JsonNode) {
            return (JsonNode) object;
        }
        return mapper.valueToTree(object);
    }

    public Object wrappWithProperties(Object object){
        PropertiesFix wrapper = new PropertiesFix();
        wrapper.setProperties(object);

        return wrapper;
    }

    public HttpResponse execute(HttpRequestBase request, int expectedStatus)
           throws RestClientException {

        HttpResponse response = null;
        try {
           response = execute(request);
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

    public String appendParams(String path, Map<String, String> params) {
        return path + queryString(params);
    }

    public String queryString(Map<String, String> params) {
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

    public <T extends HttpUriRequest> T contentTypeJson(T request) {
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        return request;
    }

    public <T extends HttpUriRequest> T contentTypeUrlEncoded(T request) {
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
        return request;
    }

    public HttpPost newHttpPost(String url) {
        return new HttpPost(url);
    }

    public HttpGet newHttpGet(String url) {
        return new HttpGet(url);
    }

    public HttpPut newHttpPut(String url) {
        return new HttpPut(url);
    }

    public HttpPatch newHttpPatch(String url) {
        return new HttpPatch(url);
    }

    public HttpDelete newHttpDelete(String url) {
        return new HttpDelete(url);
    }

    public String contentAsString(HttpResponse response) throws IOException {
        if (response != null && response.getEntity() != null) {
           return IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
        }
        return null;
    }

    public void setInterceptor(RequestInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public class PropertiesFix {
        private Object properties;

        public Object getProperties() {
         return properties;
      }

        public void setProperties(Object properties) {
         this.properties = properties;
      }
    }

    private HttpResponse execute(HttpRequestBase request)
            throws IOException {
        request = userAgentHeader(request);

        if (this.interceptor != null) {
            this.interceptor.intercept(request);
        }

        return httpClient.execute(request);
    }

    private <T extends HttpUriRequest> T userAgentHeader(T request) {
        String artifactId = getClass().getPackage().getImplementationTitle();
        String version = getClass().getPackage().getImplementationVersion();
        request.addHeader(HttpHeaders.USER_AGENT, artifactId + "/" + version);
        return request;
    }
}
