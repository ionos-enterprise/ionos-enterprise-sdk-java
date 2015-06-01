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

/**
 *
 * @author jasmin.gacic
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
}
