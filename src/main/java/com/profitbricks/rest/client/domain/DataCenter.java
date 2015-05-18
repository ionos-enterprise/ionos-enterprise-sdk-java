/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client.domain;

/**
 *
 * @author jasmin.gacic
 */
public class DataCenter extends ProfitbricksBase {

   public DataCenter() {
      this.entities = new Entities();
   }
   private Properties properties = new Properties();
   private Entities entities;

   /**
    * @return the properties
    */
   public Properties getProperties() {
      return properties;
   }

   /**
    * @param properties the properties to set
    */
   public void setProperties(Properties properties) {
      this.properties = properties;
   }

   /**
    * @return the entities
    */
   public Entities getEntities() {
      return entities;
   }

   /**
    * @param entities the entities to set
    */
   public void setEntities(Entities entities) {
      this.entities = entities;
   }

   public class Properties {

      private String name;
      private String description;
      private Location location;
      private String version;

      /**
       * @return the location
       */
      public Location getLocation() {
         return location;
      }

      /**
       * @param location the location to set
       */
      public void setLocation(String location) {
         this.location.fromValue(location);
      }

      /**
       * @return the name
       */
      public String getName() {
         return name;
      }

      /**
       * @param name the name to set
       */
      public void setName(String name) {
         this.name = name;
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
   }

   public class Entities {

      private Servers servers = new Servers();
      private Volumes volumes = new Volumes();
      private LoadBalancers loadbalancers = new LoadBalancers();
      private Lans lans = new Lans();

      /**
       * @return the servers
       */
      public Servers getServers() {
         return servers;
      }

      /**
       * @param servers the servers to set
       */
      public void setServers(Servers servers) {
         this.servers = servers;
      }

      /**
       * @return the volumes
       */
      public Volumes getVolumes() {
         return volumes;
      }

      /**
       * @param volumes the volumes to set
       */
      public void setVolumes(Volumes volumes) {
         this.volumes = volumes;
      }

      /**
       * @return the loadbalancers
       */
      public LoadBalancers getLoadbalancers() {
         return loadbalancers;
      }

      /**
       * @param loadbalancers the loadbalancers to set
       */
      public void setLoadbalancers(LoadBalancers loadbalancers) {
         this.loadbalancers = loadbalancers;
      }

      /**
       * @return the lans
       */
      public Lans getLans() {
         return lans;
      }

      /**
       * @param lans the lans to set
       */
      public void setLans(Lans lans) {
         this.lans = lans;
      }

   }

}
