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
import com.profitbricks.rest.domain.Helper;
import com.profitbricks.rest.domain.raw.FirewallRuleRaw;
import com.profitbricks.rest.domain.raw.FirewallRulesRaw;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class FirewallRuleApi extends ProfitbricksAPIBase {

   public FirewallRuleApi() {
      super("firewallrules", "nics");
   }

   public List<FirewallRule> getAllFirewallRules(String dataCenterId, String serverId, String nicId) throws RestClientException, IOException {
      return Helper.convertFirewallRules(client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat(depth), null, FirewallRulesRaw.class));
   }

   public FirewallRule getFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId) throws RestClientException, IOException {
      return Helper.convertFirewallRule(client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat("/").concat(firewallRuleId)
              .concat(depth), null, FirewallRuleRaw.class));
   }

   public FirewallRule createFirewallRule(String dataCenterId, String serverId, String nicId, FirewallRuleRaw firewallRule) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return Helper.convertFirewallRule(client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource),
              firewallRule, FirewallRuleRaw.class, 202));
   }

   public void deleteFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat("/").concat(firewallRuleId),
              202);
   }

   public FirewallRule updateFirewWallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId, PBObject firewallRule) throws RestClientException, IOException {
      return Helper.convertFirewallRule(client.update(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("servers").concat("/").concat(serverId).concat("/")
              .concat(parentResource).concat("/").concat(nicId).concat("/")
              .concat(resource).concat("/").concat(firewallRuleId),
              firewallRule, FirewallRuleRaw.class, 202));
   }
}
