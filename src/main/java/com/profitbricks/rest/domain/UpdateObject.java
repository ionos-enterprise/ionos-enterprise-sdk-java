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
public class UpdateObject {

   public String name;
   public Status vmState;
   public String description;
   public String cores;
   public String ram;
   public AvailabilityZone availabilityZone;
   public Boolean bootVolume;
   public Boolean bootCdrom;
   public String licencetype;
}
