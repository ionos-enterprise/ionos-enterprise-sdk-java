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

   public ProfitbricksApi() {
      this.dataCenterApi = new DatacenterApi();
      this.serverApi = new ServerApi();
      this.volumeApi = new VolumeApi();
      this.snapshotApi = new SnapshotApi();
      this.loadbalancerApi = new LoadbalancerApi();
      this.nicApi = new NicApi();
      this.firewallRuleApi = new FirewallRuleApi();
   }

   public DatacenterApi dataCenterApi;
   public ServerApi serverApi;
   public VolumeApi volumeApi;
   public SnapshotApi snapshotApi;
   public LoadbalancerApi loadbalancerApi;
   public NicApi nicApi;
   public FirewallRuleApi firewallRuleApi;

}
