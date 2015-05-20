package com.profitbricks.sdk;

/**
 * Hello world!
 *
 */
public class ProfitbricksApi {

   public ProfitbricksApi() {
      this.serverApi = new ServerApi();
      this.dataCenterApi = new DatacenterApi();
   }

   public DatacenterApi dataCenterApi;
   public ServerApi serverApi;
}
