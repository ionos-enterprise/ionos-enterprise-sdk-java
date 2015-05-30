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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author jasmin.gacic
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Image extends ProfitbricksBase {

   private Properties properties = new Properties();

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

   public static class Properties {

      private String name;
      private String description;
      private String location;
      private String size;
      private Boolean isPublic;
      private String licenceType;
      private String imageType;

      private Boolean cpuHotPlug;
      private Boolean cpuHotUnplug;
      private Boolean ramHotPlug;
      private Boolean ramHotUnplug;
      private Boolean nicHotPlug;
      private Boolean nicHotUnplug;
      private Boolean discVirtioHotPlug;
      private Boolean discVirtioHotUnplug;
      private Boolean discScsiHotPlug;
      private Boolean discScsiHotUnplug;

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

      /**
       * @return the description
       */
      public String getDescription() {
         return description;
      }

      /**
       * @param description the description to set
       */
      public void setDescription(String description) {
         this.description = description;
      }

      /**
       * @return the location
       */
      public String getLocation() {
         return location;
      }

      /**
       * @param location the location to set
       */
      public void setLocation(String location) {
         this.location = location;
      }

      /**
       * @return the size
       */
      public String getSize() {
         return size;
      }

      /**
       * @param size the size to set
       */
      public void setSize(String size) {
         this.size = size;
      }

      /**
       * @return the isPublic
       */
      @JsonProperty("public")
      public Boolean getIsPublic() {
         return isPublic;
      }

      /**
       * @param isPublic the isPublic to set
       */
      @JsonProperty("public")
      public void setIsPublic(Boolean isPublic) {
         this.isPublic = isPublic;
      }

      /**
       * @return the licenceType
       */
      public String getLicenceType() {
         return licenceType;
      }

      /**
       * @param licenceType the licenceType to set
       */
      public void setLicenceType(String licenceType) {
         this.licenceType = licenceType;
      }

      /**
       * @return the cpuHotPlug
       */
      public Boolean getCpuHotPlug() {
         return cpuHotPlug;
      }

      /**
       * @param cpuHotPlug the cpuHotPlug to set
       */
      public void setCpuHotPlug(Boolean cpuHotPlug) {
         this.cpuHotPlug = cpuHotPlug;
      }

      /**
       * @return the cpuHotUnplug
       */
      public Boolean getCpuHotUnplug() {
         return cpuHotUnplug;
      }

      /**
       * @param cpuHotUnplug the cpuHotUnplug to set
       */
      public void setCpuHotUnplug(Boolean cpuHotUnplug) {
         this.cpuHotUnplug = cpuHotUnplug;
      }

      /**
       * @return the ramHotPlug
       */
      public Boolean getRamHotPlug() {
         return ramHotPlug;
      }

      /**
       * @param ramHotPlug the ramHotPlug to set
       */
      public void setRamHotPlug(Boolean ramHotPlug) {
         this.ramHotPlug = ramHotPlug;
      }

      /**
       * @return the ramHotUnplug
       */
      public Boolean getRamHotUnplug() {
         return ramHotUnplug;
      }

      /**
       * @param ramHotUnplug the ramHotUnplug to set
       */
      public void setRamHotUnplug(Boolean ramHotUnplug) {
         this.ramHotUnplug = ramHotUnplug;
      }

      /**
       * @return the nicHotPlug
       */
      public Boolean getNicHotPlug() {
         return nicHotPlug;
      }

      /**
       * @param nicHotPlug the nicHotPlug to set
       */
      public void setNicHotPlug(Boolean nicHotPlug) {
         this.nicHotPlug = nicHotPlug;
      }

      /**
       * @return the nicHotUnplug
       */
      public Boolean getNicHotUnplug() {
         return nicHotUnplug;
      }

      /**
       * @param nicHotUnplug the nicHotUnplug to set
       */
      public void setNicHotUnplug(Boolean nicHotUnplug) {
         this.nicHotUnplug = nicHotUnplug;
      }

      /**
       * @return the discVirtioHotPlug
       */
      public Boolean getDiscVirtioHotPlug() {
         return discVirtioHotPlug;
      }

      /**
       * @param discVirtioHotPlug the discVirtioHotPlug to set
       */
      public void setDiscVirtioHotPlug(Boolean discVirtioHotPlug) {
         this.discVirtioHotPlug = discVirtioHotPlug;
      }

      /**
       * @return the discVirtioHotUnplug
       */
      public Boolean getDiscVirtioHotUnplug() {
         return discVirtioHotUnplug;
      }

      /**
       * @param discVirtioHotUnplug the discVirtioHotUnplug to set
       */
      public void setDiscVirtioHotUnplug(Boolean discVirtioHotUnplug) {
         this.discVirtioHotUnplug = discVirtioHotUnplug;
      }

      /**
       * @return the discScsiHotPlug
       */
      public Boolean getDiscScsiHotPlug() {
         return discScsiHotPlug;
      }

      /**
       * @param discScsiHotPlug the discScsiHotPlug to set
       */
      public void setDiscScsiHotPlug(Boolean discScsiHotPlug) {
         this.discScsiHotPlug = discScsiHotPlug;
      }

      /**
       * @return the discScsiHotUnplug
       */
      public Boolean getDiscScsiHotUnplug() {
         return discScsiHotUnplug;
      }

      /**
       * @param discScsiHotUnplug the discScsiHotUnplug to set
       */
      public void setDiscScsiHotUnplug(Boolean discScsiHotUnplug) {
         this.discScsiHotUnplug = discScsiHotUnplug;
      }

      /**
       * @return the imageType
       */
      public String getImageType() {
         return imageType;
      }

      /**
       * @param imageType the imageType to set
       */
      public void setImageType(String imageType) {
         this.imageType = imageType;
      }
   }

}
