package com.profitbricks.sdk;

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
