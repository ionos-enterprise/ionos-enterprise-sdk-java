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
public enum Location {

   DE_FKB("de/fkb"),
   DE_FRA("de/fra"),
   US_LAS("us/las"),
   US_LAS_DEV("us/lasdev"),
   UNRECOGNIZED("unknown");

   private final String id;

   Location(String id) {
      this.id = id;
   }

   public String value() {
      return id;
   }

   public static Location fromValue(String v) {
      try {
         return valueOf(v);
      } catch (IllegalArgumentException ex) {
         return UNRECOGNIZED;
      }
   }

   public static Location fromId(String id) {
      for (Location location : values())
         if (location.id.equals(id))
            return location;
      return UNRECOGNIZED;
   }
}
