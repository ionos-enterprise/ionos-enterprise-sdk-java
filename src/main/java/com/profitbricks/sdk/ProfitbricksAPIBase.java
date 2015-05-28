/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RequestInterceptor;
import com.profitbricks.rest.client.RestClient;
import org.apache.http.client.methods.HttpRequestBase;

/**
 *
 * @author jasmin.gacic
 */
public abstract class ProfitbricksAPIBase {

   public String urlBase = "https://api.profitbricks.com/rest/";
   public String resource;
   public String parentResource;

   public RequestInterceptor authorize;
   public RestClient client;
   public String depth = "?depth=".concat("5");

   String credentials = "amFzbWluQHN0YWNrcG9pbnRjbG91ZC5jb206TEB4dTZFZjh6dw==";

   public ProfitbricksAPIBase(String resource, String parentResource) {
      this.resource = resource;
      this.parentResource = parentResource;

      authorize = new RequestInterceptor() {
         @Override
         public void intercept(HttpRequestBase request) {

            request.addHeader("Authorization", "Basic ".concat(credentials));
         }
      };

      client = RestClient.builder().requestInterceptor(authorize).build();
   }
}
