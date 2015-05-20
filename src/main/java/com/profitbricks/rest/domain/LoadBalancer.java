/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.domain;

/**
 *
 * @author jasmin.gacic
 */
public class LoadBalancer extends ProfitbricksBase {

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

   public class Properties {

      private String name;
      private String ip;
      private boolean dhcp;

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
       * @return the ip
       */
      public String getIp() {
         return ip;
      }

      /**
       * @param ip the ip to set
       */
      public void setIp(String ip) {
         this.ip = ip;
      }

      /**
       * @return the dhcp
       */
      public boolean isDhcp() {
         return dhcp;
      }

      /**
       * @param dhcp the dhcp to set
       */
      public void setDhcp(boolean dhcp) {
         this.dhcp = dhcp;
      }
   }

   public class Entities {

      private Balancednics balancednics = new Balancednics();

      /**
       * @return the balancednics
       */
      public Balancednics getBalancednics() {
         return balancednics;
      }

      /**
       * @param balancednics the balancednics to set
       */
      public void setBalancednics(Balancednics balancednics) {
         this.balancednics = balancednics;
      }
   }

   private Entities entities = new Entities();
   private Properties properties = new Properties();
}
