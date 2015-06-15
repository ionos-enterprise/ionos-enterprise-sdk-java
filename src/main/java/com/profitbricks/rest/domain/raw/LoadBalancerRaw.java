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
package com.profitbricks.rest.domain.raw;

/**
 *
 * @author jasmin.gacic
 */
public class LoadBalancerRaw extends ProfitbricksBaseRaw {

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
      if (entities == null)
         this.entities = new Entities();
      
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

   public static class Properties {

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

      private BalancednicsRaw balancednics = new BalancednicsRaw();

      /**
       * @return the balancednics
       */
      public BalancednicsRaw getBalancednics() {
         return balancednics;
      }

      /**
       * @param balancednics the balancednics to set
       */
      public void setBalancednics(BalancednicsRaw balancednics) {
         this.balancednics = balancednics;
      }
   }

   private Entities entities = new Entities();
   private Properties properties = new Properties();
}
