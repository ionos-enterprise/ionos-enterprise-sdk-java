/*
 * Copyright (c) 2017, ProfitBricks GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the ProfitBricks nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ProfitBricks GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ProfitBricks GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.profitbricks.rest.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class RequestStatus {

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
