/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RequestInterceptor;
import com.profitbricks.rest.client.RestClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpRequestBase;

/**
 *
 * @author jasmin.gacic
 */
public abstract class ProfitbricksAPIBase {

   public String urlBase = "https://spc.profitbricks.com/rest/";
   public String resource;
   public RequestInterceptor authorize;
   public RestClient client;
   public String depth = "?depth=".concat("5");

   String credentials = "ZmFyaWQuc2hhaEBwcm9maXRicmlja3MuY29tOnNwYzIwMTU=";

   public ProfitbricksAPIBase(String resource) {
      this.resource = resource;

      authorize = new RequestInterceptor() {
         @Override
         public void intercept(HttpRequestBase request) {
            request.addHeader("Authorization", "Basic ".concat(credentials));
            if (request.getClass() == HttpPatch.class)
               request.addHeader("Content-Type", "application/vnd.profitbricks.partial-properties+json");
            else
               request.addHeader("Content-Type", "application/vnd.profitbricks.resource+json");
         }
      };

      client = RestClient.builder().requestInterceptor(authorize).build();
   }
}
