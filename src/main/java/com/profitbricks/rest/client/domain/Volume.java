/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client.domain;

/**
 *
 * @author jasmin.gacic
 */
public class Volume extends ProfitbricksBase {

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

      private String name;
      private String cores;
      private String ram;
      private AvailabilityZone availabilityZone;
      private Status vmState;
      private String bootVolume;
      private String bootCdrom;

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
   }
   
   private Properties properties = new Properties();
}
