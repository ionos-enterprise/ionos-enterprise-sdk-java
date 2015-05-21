/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.DataCenters;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.Servers;
import com.profitbricks.rest.domain.UpdateObject;
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

   public Server updateServer(String dataCenterId, String serverId, UpdateObject server) throws RestClientException, IOException {
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
