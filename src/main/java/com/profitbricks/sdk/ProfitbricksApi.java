package com.profitbricks.sdk;

public class ProfitbricksApi {

   public ProfitbricksApi() {
      this.dataCenterApi = new DatacenterApi();
      this.serverApi = new ServerApi();
      this.volumeApi = new VolumeApi();
   }

   public DatacenterApi dataCenterApi;
   public ServerApi serverApi;
   public VolumeApi volumeApi;

}
