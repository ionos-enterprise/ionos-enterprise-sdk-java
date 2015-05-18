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
public class CDRom extends ProfitbricksBase {

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

      String name;

      public class Image {

         private String id;
         private String type;
         private String href;

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
   }

   private Properties properties = new Properties();
}
