/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
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
}
