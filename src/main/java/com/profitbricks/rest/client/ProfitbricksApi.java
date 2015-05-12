package com.profitbricks.rest.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import http.rest.RequestInterceptor;
import http.rest.RestClient;
import java.util.List;
import org.apache.http.client.methods.HttpUriRequest;
import com.profitbricks.rest.client.domain.DataCenter;
import com.profitbricks.rest.client.domain.DataCenters;
import http.rest.RestClientException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Hello world!
 *
 */
public class ProfitbricksApi {

   public ProfitbricksApi() {
   }

   public DatacenterApi dataCenterApi = new DatacenterApi();

   //http://spc.profitbricks.com/rest/datacenters
  /* public static void main(String[] args) {

    String url = "https://spc.profitbricks.com/rest/datacenters";
    DataCenters node = new DataCenters();
    try {
    node = client.get(url, null, DataCenters.class);
    int nr = 0;
    } catch (RestClientException ex) {
    Logger.getLogger(ProfitbricksApi.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
    Logger.getLogger(ProfitbricksApi.class.getName()).log(Level.SEVERE, null, ex);
    }

    }*/
}
