/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client.domain;

import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class Entities {

   private List<Server> servers;
   private List<Volume> volumes;
   private List<LoadBalancers> loadbalancers;
   private List<Lans> lans;

   /**
    * @return the servers
    */
   public List<Server> getServers() {
      return servers;
   }

   /**
    * @param servers the servers to set
    */
   public void setServers(List<Server> servers) {
      this.servers = servers;
   }

   /**
    * @return the volumes
    */
   public List<Volume> getVolumes() {
      return volumes;
   }

   /**
    * @param volumes the volumes to set
    */
   public void setVolumes(List<Volume> volumes) {
      this.volumes = volumes;
   }

   /**
    * @return the loadbalancers
    */
   public List<LoadBalancers> getLoadbalancers() {
      return loadbalancers;
   }

   /**
    * @param loadbalancers the loadbalancers to set
    */
   public void setLoadbalancers(List<LoadBalancers> loadbalancers) {
      this.loadbalancers = loadbalancers;
   }

   /**
    * @return the lans
    */
   public List<Lans> getLans() {
      return lans;
   }

   /**
    * @param lans the lans to set
    */
   public void setLans(List<Lans> lans) {
      this.lans = lans;
   }
}
