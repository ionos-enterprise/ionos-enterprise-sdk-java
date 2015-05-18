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
public class Lans extends ProfitbricksBase {

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
