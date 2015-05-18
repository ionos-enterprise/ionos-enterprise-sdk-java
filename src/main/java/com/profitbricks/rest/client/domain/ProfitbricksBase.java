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
public class ProfitbricksBase {

   public ProfitbricksBase() {

   }
   private String id;
   private String type;
   private String href;
   private Metadata metadata = new Metadata();

   /**
    * @return the id
    */
   public String getId() {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * @return the type
    */
   public String getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(String type) {
      this.type = type;
   }

   /**
    * @return the metadata
    */
   public Metadata getMetadata() {
      return metadata;
   }

   /**
    * @param metadata the metadata to set
    */
   public void setMetadata(Metadata metadata) {
      this.metadata = metadata;
   }

   /**
    * @return the href
    */
   public String getHref() {
      return href;
   }

   /**
    * @param href the href to set
    */
   public void setHref(String href) {
      this.href = href;
   }

 
}
