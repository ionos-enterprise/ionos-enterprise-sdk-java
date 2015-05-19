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
public class DataCenter extends ProfitbricksBase {

   public DataCenter() {
      this.entities = new Entities();
      this.properties = new Properties();
   }
   
   public Properties properties;
   public Entities entities;

   public class Properties {

      public String name;
      public String description;
      public Location location;
      public String version;

      /**
       * @param location the location to set
       */
      public void setLocation(String location) {
         this.location.fromValue(location);
      }
      
      public String getLocation(){
         return this.location.value();
      }
   }

   public class Entities {

      public Servers servers = new Servers();
      public Volumes volumes = new Volumes();
      public LoadBalancers loadbalancers = new LoadBalancers();
      public Lans lans = new Lans();
   }
}
