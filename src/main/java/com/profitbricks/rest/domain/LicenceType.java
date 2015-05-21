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
public enum LicenceType {

   WINDOWS, LINUX, OTHER, UNRECOGNIZED;

   public static LicenceType fromValue(String v) {
      try {
         return valueOf(v);
      } catch (IllegalArgumentException ex) {
         return UNRECOGNIZED;
      }
   }

}
