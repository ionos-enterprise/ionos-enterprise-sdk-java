/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
      private boolean isPublic;
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
