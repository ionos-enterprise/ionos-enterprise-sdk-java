/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
