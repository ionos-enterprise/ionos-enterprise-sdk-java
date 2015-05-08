/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client;

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

   private String urlBase = "https://spc.profitbricks.com/rest/";
   private String resource;

   public ProfitbricksAPIBase(String resource) {
      this.resource = resource;
   }

   private HttpRequestBase createRequest(Verbs verb) {
      HttpRequestBase request = null;

      switch (verb) {
         case GET:
            request = new HttpGet(urlBase.concat(resource));
            break;
         case POST:
            request = new HttpPost(urlBase.concat(resource));
            break;
         case PUT:
            request = new HttpPut(urlBase.concat(resource));
            break;
         case DELETE:
            request = new HttpDelete(urlBase.concat(resource));
            break;
      }

      if (request != null) {
         request.addHeader("Authorization", "Basic ZmFyaWQuc2hhaEBwcm9maXRicmlja3MuY29tOnNwYzIwMTU=");
         request.addHeader("Content-Type", "application/vnd.profitbricks.resource+json");
         return request;
      } else
         throw new ExceptionInInitializerError("Unknown type");
   }
}
