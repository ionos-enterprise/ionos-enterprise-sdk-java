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
