 /*
 * Copyright (c) <year>, <copyright holder>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.rest.domain.PBObject;
import java.io.IOException;
import org.junit.Test;

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
