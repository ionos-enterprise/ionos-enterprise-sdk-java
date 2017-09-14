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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.profitbricks.sdk.ProfitbricksApi;

import java.util.Date;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class ProfitbricksBase {

   private String id;
   private String type;
   private String href;
   private Metadata metadata;
   private String requestId;

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
    * @return the requestId
    */
   public String getRequestId() {
      return requestId;
   }

   /**
    * @param requestId the requestId to set
    */
   public void setRequestId(String requestId) {
      this.requestId = requestId;
   }

   public RequestStatus getStatus() throws Exception {
      if (requestId == null)
         return null;

      ProfitbricksApi profitbricksApi = new ProfitbricksApi();
      return profitbricksApi.getRequest().getRequestStatus(requestId);
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class Metadata {

      private Date lastModifiedDate;
      private String lastModifiedBy;
      private Date createdDate;
      private String createdBy;
      private State state;
      private String etag;

      /**
       * @return the lastModifiedDate
       */
      public Date getLastModifiedDate() {
         return lastModifiedDate;
      }

      /**
       * @param lastModifiedDate the lastModifiedDate to set
       */
      public void setLastModifiedDate(Date lastModifiedDate) {
         this.lastModifiedDate = lastModifiedDate;
      }

      /**
       * @return the lastModifiedBy
       */
      public String getLastModifiedBy() {
         return lastModifiedBy;
      }

      /**
       * @param lastModifiedBy the lastModifiedBy to set
       */
      public void setLastModifiedBy(String lastModifiedBy) {
         this.lastModifiedBy = lastModifiedBy;
      }

      /**
       * @return the createdDate
       */
      public Date getCreatedDate() {
         return createdDate;
      }

      /**
       * @param createdDate the createdDate to set
       */
      public void setCreatedDate(Date createdDate) {
         this.createdDate = createdDate;
      }

      /**
       * @return the createdBy
       */
      public String getCreatedBy() {
         return createdBy;
      }

      /**
       * @param createdBy the createdBy to set
       */
      public void setCreatedBy(String createdBy) {
         this.createdBy = createdBy;
      }

      /**
       * @return the state
       */
      public State getState() {
         return state;
      }

      /**
       * @param state the state to set
       */
      public void setState(State state) {
         this.state = state;
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

   }
}

