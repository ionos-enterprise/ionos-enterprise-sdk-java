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
class Properties {
   String name;
   String description;
   private Location location;

   /**
    * @return the location
    */
   public Location getLocation() {
      return location;
   }

   /**
    * @param location the location to set
    */
   public void setLocation(Location location) {
      this.location = location;
   }
}
