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
public class Nic extends ProfitbricksBase {

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
      private String mac;
      private List<String> ips = new ArrayList<String>();
      private boolean dhcp;
      private String lan;
      private Boolean nat;
      private boolean firewallActive;

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
       * @return the mac
       */
      public String getMac() {
         return mac;
      }

      /**
       * @param mac the mac to set
       */
      public void setMac(String mac) {
         this.mac = mac;
      }

      /**
       * @return the ips
       */
      public List<String> getIps() {
         return ips;
      }

      /**
       * @param ips the ips to set
       */
      public void setIps(List<String> ips) {
         this.ips = ips;
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

      /**
       * @return the lan
       */
      public String getLan() {
         return lan;
      }

      /**
       * @param lan the lan to set
       */
      public void setLan(String lan) {
         this.lan = lan;
      }

      /**
       * @return the firewallActive
       */
      public boolean isFirewallActive() {
         return firewallActive;
      }

      /**
       * @param firewallActive the firewallActive to set
       */
      public void setFirewallActive(boolean firewallActive) {
         this.firewallActive = firewallActive;
      }

      /**
       * @return the nat
       */
      public Boolean getNat() {
         return nat;
      }

      /**
       * @param nat the nat to set
       */
      public void setNat(Boolean nat) {
         this.nat = nat;
      }
   }

   public class Entities {

      private FirewallRules firewallrules = new FirewallRules();

      /**
       * @return the firewallrules
       */
      public FirewallRules getFirewallrules() {
         return firewallrules;
      }

      /**
       * @param firewallrules the firewallrules to set
       */
      public void setFirewallrules(FirewallRules firewallrules) {
         this.firewallrules = firewallrules;
      }
   }
   private Properties properties = new Properties();
   private Entities entities = new Entities();
}
