package com.profitbricks.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Hello world!
 *
 */
public class ProfitbricksApi {
   //http://spc.profitbricks.com/rest/datacenters

   public static void main(String[] args) {
      try {

         DefaultHttpClient httpClient = new DefaultHttpClient();

         //HttpGet getRequest = (HttpGet) createRequest(Verbs.GET);

         /**/
         HttpResponse response = null;//= httpClient.execute(createRequest(Verbs.GET));

         if (response.getStatusLine().getStatusCode() != 200)
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());

         BufferedReader br = new BufferedReader(
                 new InputStreamReader((response.getEntity().getContent())));

         String output;
         StringBuilder builder = new StringBuilder();
         System.out.println("Output from Server .... \n");
         while ((output = br.readLine()) != null) {
            System.out.println(output);
            builder.append(output);
         }

         Object outer = JSONObject.toBean(JSONObject.fromObject(builder.toString()));

         httpClient.getConnectionManager().shutdown();

      } catch (ClientProtocolException e) {

         e.printStackTrace();

      } catch (IOException e) {

         e.printStackTrace();
      }

   }

   private HttpRequestBase createRequest(Verbs verb) {
      switch (verb) {
         case GET:
            HttpGet request = new HttpGet(
                    "https://spc.profitbricks.com/rest/datacenters");
            break;
      }
      HttpGet request = new HttpGet(
              "https://spc.profitbricks.com/rest/datacenters");

      request.addHeader("Authorization", "Basic ZmFyaWQuc2hhaEBwcm9maXRicmlja3MuY29tOnNwYzIwMTU=");
      request.addHeader("Content-Type", "application/vnd.profitbricks.resource+json");

      return request;
   }
}
