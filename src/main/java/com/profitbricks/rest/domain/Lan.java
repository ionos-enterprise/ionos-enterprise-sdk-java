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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author jasmin.gacic
 */
public class Lan extends ProfitbricksBase {

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

   public class Properties {

      @JsonProperty("public")
      private Boolean isPublic;
      private String name;

      /**
       * @return the isPublic
       */
      public boolean isIsPublic() {
         return isPublic;
      }

      /**
       * @param isPublic the isPublic to set
       */
      public void setIsPublic(boolean isPublic) {
         this.isPublic = isPublic;
      }

      /**
       * @return the name
       */
      public String getName() {
         return name;
      }

      /**
       * @param name the name to set
       */
      public void setName(String name) {
         this.name = name;
      }
   }

   public class Entities {

      private Nics nics = new Nics();

      /**
       * @return the nics
       */
      public Nics getNics() {
         return nics;
      }

      /**
       * @param nics the nics to set
       */
      public void setNics(Nics nics) {
         this.nics = nics;
      }
   }
   private Entities entities = new Entities();

   private Properties properties = new Properties();

   /**
    * @return the entities
    */
   public Entities getEntities() {
      return entities;
   }

   /**
    * @param entities the entities to set
    */
   public void setEntities(Entities entities) {
      this.entities = entities;
   }
}
