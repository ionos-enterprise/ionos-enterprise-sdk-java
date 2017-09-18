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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.util.List;

/**
 *
 * @author jasmin@stackpointcloud.com
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
      private Float size;
      private Boolean isPublic;
      private String licenceType;
      private String imageType;
      private String type;
      private String image;
      private String imagePassword;
      private String bus;
      private String deviceNumber;
      private List<String> imageAliases;

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
      public Float getSize() {
         return size;
      }

      /**
       * @param size the size to set
       */
      public void setSize(Float size) {
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
       * @return the image
       */
      public String getImage() {
         return image;
      }

      /**
       * @param image the image to set
       */
      public void setImage(String image) {
         this.image = image;
      }

      /**
       * @return the imagePassword
       */
      public String getImagePassword() {
         return imagePassword;
      }

      /**
       * @param imagePassword the imagePassword to set
       */
      public void setImagePassword(String imagePassword) {
         this.imagePassword = imagePassword;
      }

      /**
       * @return the bus
       */
      public String getBus() {
         return bus;
      }

      /**
       * @param bus the bus to set
       */
      public void setBus(String bus) {
         this.bus = bus;
      }

      /**
       * @return the deviceNumber
       */
      public String getDeviceNumber() {
         return deviceNumber;
      }

      /**
       * @param deviceNumber the deviceNumber to set
       */
      public void setDeviceNumber(String deviceNumber) {
         this.deviceNumber = deviceNumber;
      }

      public List<String> getImageAliases() {
         return imageAliases;
      }

      public void setImageAliases(List<String> imageAliases) {
         this.imageAliases = imageAliases;
      }
   }

}
