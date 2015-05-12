/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client;

import http.rest.RequestInterceptor;
import http.rest.RestClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
   String credentials = "ZmFyaWQuc2hhaEBwcm9maXRicmlja3MuY29tOnNwYzIwMTU=";

   public ProfitbricksAPIBase(String resource) {
      this.resource = resource;

      authorize = new RequestInterceptor() {
         @Override
         public void intercept(HttpRequestBase request) {
            request.addHeader("Authorization", "Basic ".concat(credentials));
         }
      };

      client = RestClient.builder().requestInterceptor(authorize).build();
   }
}
