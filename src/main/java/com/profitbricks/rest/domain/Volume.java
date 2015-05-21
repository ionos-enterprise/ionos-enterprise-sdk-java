/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author jasmin.gacic
 */
public class Volume extends ProfitbricksBase {

   @JsonIgnoreProperties(ignoreUnknown = true)
   public class Properties {

      public String name;
      public String cores;
      public String ram;
      public AvailabilityZone availabilityZone;
      public Status vmState;
      public String bootVolume;
      public String bootCdrom;
      public String size;
      public BusType bus;
      public String image;
      public String licenceType;
   }

   public Properties properties = new Properties();
}
