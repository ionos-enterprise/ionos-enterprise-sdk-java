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
public enum AvailabilityZone {

   AUTO, ZONE_1, ZONE_2, UNRECOGNIZED;

   public String value() {
      return name();
   }

   public static AvailabilityZone fromValue(String v) {
      try {
         return valueOf(v);
      } catch (Exception ex) {
         return UNRECOGNIZED;
      }
   }
}
