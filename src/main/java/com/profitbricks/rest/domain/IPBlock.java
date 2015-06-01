 /*
 * Copyright 2015.
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class IPBlock extends ProfitbricksBase {

   private Properties properties = new Properties();

   /**
    * @return the properties
    */
   public Properties getProperties() {
      return properties;
   }

   /**
    * @param properties the properties to set
    */
   public void setProperties(Properties properties) {
      this.properties = properties;
   }

   public static class Properties {

      private List<String> ips;
      private String location;
      private String size;

      /**
       * @return the ips
       */
      public List<String> getIps() {
         return ips;
      }

      /**
       * @param ips the ips to set
       */
      public void setIps(List<String> ips) {
         if (ips == null)
            ips = new ArrayList<String>();
         this.ips = ips;
      }

      /**
       * @return the location
       */
      public String getLocation() {
         return location;
      }

      /**
       * @param location the location to set
       */
      public void setLocation(String location) {
         this.location = location;
      }

      /**
       * @return the size
       */
      public String getSize() {
         return size;
      }

      /**
       * @param size the size to set
       */
      public void setSize(String size) {
         this.size = size;
      }
   }
}
