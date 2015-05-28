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
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;

/**
 *
 * @author jasmin.gacic
 */
public class ServerApi extends ProfitbricksAPIBase {

   public ServerApi() {
      super("servers", "datacenters");
   }

   public Servers getAllServers(String dataCenterId) throws RestClientException, IOException {
      return client.get(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource)
              .concat(depth), null, Servers.class);
   }

   public Server getServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      return client.get(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId)
              .concat(depth), null, Server.class);
   }

   public Server createServer(String dataCenterId, Server server) throws RestClientException, IOException {
      return client.create(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), server, Server.class, 202);
   }

   public void deleteServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.delete(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId), 202);

   }

   public Server updateServer(String dataCenterId, String serverId, PBObject server) throws RestClientException, IOException {
      return client.update(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId), server, Server.class, 202);

   }

   public void rebootServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.execute(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId).concat("/").concat("reboot"), 202);
   }

   public void startServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.execute(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId).concat("/").concat("start"), 202);
   }

   public void stopServer(String dataCenterId, String serverId) throws RestClientException, IOException {
      client.execute(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(serverId).concat("/").concat("stop"), 202);
   }
}
