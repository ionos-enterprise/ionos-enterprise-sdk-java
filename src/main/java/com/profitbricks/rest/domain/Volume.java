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
public class Volume extends ProfitbricksBase {

   private String cores;
   private String ram;
   private AvailabilityZone availabilityZone;
   private Status vmState;
   private String bootVolume;
   private String bootCdrom;
   private String size;
   private BusType bus;
   private String image;
   private String licenceType;

   /**
    * @return the cores
    */
   public String getCores() {
      return cores;
   }

   /**
    * @param cores the cores to set
    */
   public void setCores(String cores) {
      this.cores = cores;
   }

   /**
    * @return the ram
    */
   public String getRam() {
      return ram;
   }

   /**
    * @param ram the ram to set
    */
   public void setRam(String ram) {
      this.ram = ram;
   }

   /**
    * @return the availabilityZone
    */
   public AvailabilityZone getAvailabilityZone() {
      return availabilityZone;
   }

   /**
    * @param availabilityZone the availabilityZone to set
    */
   public void setAvailabilityZone(AvailabilityZone availabilityZone) {
      this.availabilityZone = availabilityZone;
   }

   /**
    * @return the vmState
    */
   public Status getVmState() {
      return vmState;
   }

   /**
    * @param vmState the vmState to set
    */
   public void setVmState(Status vmState) {
      this.vmState = vmState;
   }

   /**
    * @return the bootVolume
    */
   public String getBootVolume() {
      return bootVolume;
   }

   /**
    * @param bootVolume the bootVolume to set
    */
   public void setBootVolume(String bootVolume) {
      this.bootVolume = bootVolume;
   }

   /**
    * @return the bootCdrom
    */
   public String getBootCdrom() {
      return bootCdrom;
   }

   /**
    * @param bootCdrom the bootCdrom to set
    */
   public void setBootCdrom(String bootCdrom) {
      this.bootCdrom = bootCdrom;
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
    * @return the bus
    */
   public BusType getBus() {
      return bus;
   }

   /**
    * @param bus the bus to set
    */
   public void setBus(BusType bus) {
      this.bus = bus;
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
}
