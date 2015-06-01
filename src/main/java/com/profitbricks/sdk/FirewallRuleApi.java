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

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.FirewallRule;
import com.profitbricks.rest.domain.FirewallRules;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin.gacic
 */
public class FirewallRuleApi extends ProfitbricksAPIBase {

   public FirewallRuleApi() {
      super("firewallrules", "nics");
   }

   public FirewallRules getAllFirewallRules(String dataCenterId, String serverId, String nicId) throws RestClientException, IOException {
      return client.get(urlBase.concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat(depth), null, FirewallRules.class);
   }

   public FirewallRule getFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId) throws RestClientException, IOException {
      return client.get(urlBase.concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat("/").concat(firewallRuleId)
              .concat(depth), null, FirewallRule.class);
   }

   public FirewallRule createFirewallRule(String dataCenterId, String serverId, String nicId, FirewallRule firewallRule) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.create(urlBase.concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource),
              firewallRule, FirewallRule.class, 202);
   }

   public void deleteFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId) throws RestClientException, IOException {
      client.delete(urlBase.concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat("/").concat(firewallRuleId), 
              202);
   }
   
   public FirewallRule updateFirewWallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId, PBObject firewallRule) throws RestClientException, IOException {
      return client.update(urlBase.concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat("/").concat(firewallRuleId), 
              firewallRule, FirewallRule.class, 202);
   }
}
