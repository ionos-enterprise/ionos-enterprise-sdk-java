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
import com.profitbricks.rest.domain.Helper;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.raw.ServersRaw;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.raw.ServerRaw;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class ServerApi extends ProfitbricksAPIBase {

   public ServerApi() {
      super("servers", "datacenters");
   }

   public List<Server> getAllServers(String dataCenterId) throws RestClientException, IOException {
      return Helper.convertServers(client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource)
              .concat(depth), null, ServersRaw.class));
   }

   public Server getServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      return Helper.convertServer(client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId)
              .concat(depth), null, ServerRaw.class));
   }

   public Server createServer(String dataCenterId, ServerRaw server) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return Helper.convertServer(client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), server, ServerRaw.class, 202));
   }

   public void deleteServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId), 202);

   }

   public Server updateServer(String dataCenterId, String serverId, PBObject server) throws RestClientException, IOException {
      return Helper.convertServer(client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId), server, ServerRaw.class, 202));

   }

   public void rebootServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.execute(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId).concat("/").concat("reboot"), 202);
   }

   public void startServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.execute(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId).concat("/").concat("start"), 202);
   }

   public void stopServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.execute(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId).concat("/").concat("stop"), 202);
   }
}
