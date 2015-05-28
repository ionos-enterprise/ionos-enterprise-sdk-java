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

import java.util.Date;

/**
 *
 * @author jasmin.gacic
 */
public class Metadata {

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
