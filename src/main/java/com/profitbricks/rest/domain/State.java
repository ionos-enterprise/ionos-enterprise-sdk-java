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
public enum State {

   BUSY, AVAILABLE, INACTIVE, INPROCESS, DELETED, ERROR, UNRECOGNIZED;

   public static State fromValue(String value) {
      try {
         return valueOf(value);
      } catch (IllegalArgumentException e) {
         return UNRECOGNIZED;
      }
   }
}
