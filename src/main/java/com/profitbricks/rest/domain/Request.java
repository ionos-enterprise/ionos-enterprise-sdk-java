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
public class Request {

   private String id;
   private String type;
   private String href;
   private MetaData metadata;

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

   /**
    * @return the metadata
    */
   public MetaData getMetadata() {
      return metadata;
   }

   /**
    * @param metadata the metadata to set
    */
   public void setMetadata(MetaData metadata) {
      this.metadata = metadata;
   }

   public static class MetaData {

      private String status;
      private String message;
      private String etag;
      private List<Data> targets = new ArrayList<Data>();

      /**
       * @return the status
       */
      public String getStatus() {
         return status;
      }

      /**
       * @param status the status to set
       */
      public void setStatus(String status) {
         this.status = status;
      }

      /**
       * @return the message
       */
      public String getMessage() {
         return message;
      }

      /**
       * @param message the message to set
       */
      public void setMessage(String message) {
         this.message = message;
      }

      /**
       * @return the etag
       */
      public String getEtag() {
         return etag;
      }

      /**
       * @param etag the etag to set
       */
      public void setEtag(String etag) {
         this.etag = etag;
      }

      /**
       * @return the targets
       */
      public List<Data> getTargets() {
         return targets;
      }

      /**
       * @param targets the targets to set
       */
      public void setTargets(List<Data> targets) {
         this.targets = targets;
      }

      public static class Data {

         private Target target;
         private String status;

         /**
          * @return the target
          */
         public Target getTarget() {
            return target;
         }

         /**
          * @param target the target to set
          */
         public void setTarget(Target target) {
            this.target = target;
         }

         /**
          * @return the status
          */
         public String getStatus() {
            return status;
         }

         /**
          * @param status the status to set
          */
         public void setStatus(String status) {
            this.status = status;
         }
      }

      public static class Target {

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

}
