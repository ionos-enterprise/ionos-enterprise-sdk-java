/*
 * Copyright 2015 jasmin.gacic.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
   public Entities entities;

}
