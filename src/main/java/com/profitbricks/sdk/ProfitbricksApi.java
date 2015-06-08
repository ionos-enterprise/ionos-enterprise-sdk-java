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

public class ProfitbricksApi {

   private String credentials;

   public ProfitbricksApi() {
      this.dataCenterApi = new DatacenterApi();
      this.serverApi = new ServerApi();
      this.volumeApi = new VolumeApi();
      this.snapshotApi = new SnapshotApi();
      this.loadbalancerApi = new LoadbalancerApi();
      this.nicApi = new NicApi();
      this.firewallRuleApi = new FirewallRuleApi();
      this.imageApi = new ImageApi();
      this.ipBlockApi = new IPBlockApi();
      this.requestApi = new RequestApi();
      this.lanApi = new LanApi();
   }

   private DatacenterApi dataCenterApi;
   private ServerApi serverApi;
   private VolumeApi volumeApi;
   private SnapshotApi snapshotApi;
   private LoadbalancerApi loadbalancerApi;
   private NicApi nicApi;
   private FirewallRuleApi firewallRuleApi;
   private ImageApi imageApi;
   private IPBlockApi ipBlockApi;
   private RequestApi requestApi;
   private LanApi lanApi;

   /**
    * @return the dataCenterApi
    */
   public DatacenterApi getDataCenterApi() {
      this.dataCenterApi.setCredentials(credentials);
      return dataCenterApi;
   }

   /**
    * @param dataCenterApi the dataCenterApi to set
    */
   public void setDataCenterApi(DatacenterApi dataCenterApi) {
      this.dataCenterApi = dataCenterApi;
   }

   /**
    * @return the serverApi
    */
   public ServerApi getServerApi() {
      this.serverApi.setCredentials(credentials);
      return serverApi;
   }

   /**
    * @param serverApi the serverApi to set
    */
   public void setServerApi(ServerApi serverApi) {
      this.serverApi = serverApi;
   }

   /**
    * @return the volumeApi
    */
   public VolumeApi getVolumeApi() {
      this.volumeApi.setCredentials(credentials);
      return volumeApi;
   }

   /**
    * @param volumeApi the volumeApi to set
    */
   public void setVolumeApi(VolumeApi volumeApi) {
      this.volumeApi = volumeApi;
   }

   /**
    * @return the snapshotApi
    */
   public SnapshotApi getSnapshotApi() {
      this.snapshotApi.setCredentials(credentials);
      return snapshotApi;
   }

   /**
    * @param snapshotApi the snapshotApi to set
    */
   public void setSnapshotApi(SnapshotApi snapshotApi) {
      this.snapshotApi = snapshotApi;
   }

   /**
    * @return the loadbalancerApi
    */
   public LoadbalancerApi getLoadbalancerApi() {
      this.loadbalancerApi.setCredentials(credentials);
      return loadbalancerApi;
   }

   /**
    * @param loadbalancerApi the loadbalancerApi to set
    */
   public void setLoadbalancerApi(LoadbalancerApi loadbalancerApi) {
      this.loadbalancerApi = loadbalancerApi;
   }

   /**
    * @return the nicApi
    */
   public NicApi getNicApi() {
      this.nicApi.setCredentials(credentials);
      return nicApi;
   }

   /**
    * @param nicApi the nicApi to set
    */
   public void setNicApi(NicApi nicApi) {
      this.nicApi = nicApi;
   }

   /**
    * @return the firewallRuleApi
    */
   public FirewallRuleApi getFirewallRuleApi() {
      this.firewallRuleApi.setCredentials(credentials);
      return firewallRuleApi;
   }

   /**
    * @param firewallRuleApi the firewallRuleApi to set
    */
   public void setFirewallRuleApi(FirewallRuleApi firewallRuleApi) {
      this.firewallRuleApi = firewallRuleApi;
   }

   /**
    * @return the imageApi
    */
   public ImageApi getImageApi() {
      this.imageApi.setCredentials(credentials);
      return imageApi;
   }

   /**
    * @param imageApi the imageApi to set
    */
   public void setImageApi(ImageApi imageApi) {
      this.imageApi = imageApi;
   }

   /**
    * @return the ipBlockApi
    */
   public IPBlockApi getIpBlockApi() {
      this.ipBlockApi.setCredentials(credentials);
      return ipBlockApi;
   }

   /**
    * @param ipBlockApi the ipBlockApi to set
    */
   public void setIpBlockApi(IPBlockApi ipBlockApi) {
      this.ipBlockApi = ipBlockApi;
   }

   /**
    * @return the requestApi
    */
   public RequestApi getRequestApi() {
      this.requestApi.setCredentials(credentials);
      return requestApi;
   }

   /**
    * @param requestApi the requestApi to set
    */
   public void setRequestApi(RequestApi requestApi) {
      this.requestApi = requestApi;
   }

   /**
    * @return the lanApi
    */
   public LanApi getLanApi() {
      return lanApi;
   }

   /**
    * @param lanApi the lanApi to set
    */
   public void setLanApi(LanApi lanApi) {
      this.lanApi.setCredentials(credentials);
      this.lanApi = lanApi;
   }

   /**
    * @param credentials the credentials to set
    */
   public void setCredentials(String credentials) {
      this.credentials = credentials;
   }
}
