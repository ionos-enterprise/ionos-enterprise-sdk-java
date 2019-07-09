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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.lang.reflect.Constructor;

public class RestClientBuilder {

   protected HttpClient client;

   protected ObjectMapper mapper;

   protected RequestInterceptor interceptor;

   protected Class<? extends RestClient> clazz;

   protected RestClientBuilder() {
   }

   public RestClientBuilder httpClient(HttpClient client) {
      this.client = client;
      return this;
   }

   public RestClientBuilder objectMapper(ObjectMapper mapper) {
      this.mapper = mapper;
      return this;
   }

   public RestClientBuilder requestInterceptor(RequestInterceptor interceptor) {
      this.interceptor = interceptor;
      return this;
   }

   public RestClientBuilder restClientClass(Class<? extends RestClient> clazz) {
      this.clazz = clazz;
      return this;
   }

   public RestClient build() {
      if (clazz == null)
         clazz = RestClient.class;
      if (mapper == null) {
         mapper = new ObjectMapper();
         mapper.setSerializationInclusion(Include.NON_NULL);
      }
      if (client == null)
         client = HttpClientBuilder.create().useSystemProperties().build();
      return createRestClient(this, clazz);
   }

   protected <T extends RestClient> T createRestClient(RestClientBuilder builder, Class<T> restClientClass) {
      try {
         Constructor<T> constructor = restClientClass.getDeclaredConstructor(RestClientBuilder.class);
         constructor.setAccessible(true);
         return constructor.newInstance(builder);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

}
