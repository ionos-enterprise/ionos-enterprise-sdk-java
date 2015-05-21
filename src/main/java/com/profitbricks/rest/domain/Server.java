/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author jasmin.gacic
 */
public class Server extends ProfitbricksBase {

   public class Properties {

      public String name;
      public String cores;
      public String ram;
      public AvailabilityZone availabilityZone;
      public Status vmState;
      public Boolean bootVolume;
      public Boolean bootCdrom;
      public LicenceType licencetype;

      /**
       * @return the licencetype
       */
      public LicenceType getLicencetype() {
         return licencetype;
      }

      /**
       * @param licencetype the licencetype to set
       */
      public void setLicencetype(String licencetype) {
         this.licencetype.fromValue(licencetype);
      }
      
   }

   public class Entities {

      public Nics nics = new Nics();
      public Volumes volumes = new Volumes();
      public CDRoms cdroms = new CDRoms();
   }

   public Properties properties = new Properties();
   public Entities entities = new Entities();

}
