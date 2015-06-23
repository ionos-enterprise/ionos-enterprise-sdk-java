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
package com.profitbricks.rest.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class DataCenter extends ProfitbricksBase {

   private String description;
   private String location;
   private String version;
   private List<Server> servers = new ArrayList<Server>();
   private List<Volume> volumes = new ArrayList<Volume>();
   private List<LoadBalancer> loadbalancers = new ArrayList<LoadBalancer>();
   private List<Lan> lans = new ArrayList<Lan>();

   /**
    * @param location the location to set
    */
   public void setLocation(String location) {
      this.location = location;
   }

   public String getLocation() {
      return this.location;
   }

   /**
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * @return the version
    */
   public String getVersion() {
      return version;
   }

   /**
    * @param version the version to set
    */
   public void setVersion(String version) {
      this.version = version;
   }

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
   public List<LoadBalancer> getLoadbalancers() {
      return loadbalancers;
   }

   /**
    * @param loadbalancers the loadbalancers to set
    */
   public void setLoadbalancers(List<LoadBalancer> loadbalancers) {
      this.loadbalancers = loadbalancers;
   }

   /**
    * @return the lans
    */
   public List<Lan> getLans() {
      return lans;
   }

   /**
    * @param lans the lans to set
    */
   public void setLans(List<Lan> lans) {
      this.lans = lans;
   }
}
