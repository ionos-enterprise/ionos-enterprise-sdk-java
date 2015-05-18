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
public enum Protocol {

   TCP, UDP, ICMP, ANY, UNRECOGNIZED;

   public static Protocol fromValue(String value) {
      try {
         return valueOf(value);
      } catch (IllegalArgumentException e) {
         return UNRECOGNIZED;
      }
   }
}
