/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
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
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.ionosenterprise.rest.domain;

import java.util.ArrayList;
import java.util.List;

public class Nic extends BaseResource {

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
      private List<String> ips = new ArrayList<>();
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
