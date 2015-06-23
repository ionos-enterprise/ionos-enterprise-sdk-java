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
public class Server extends ProfitbricksBase {

   private String cores;
   private String ram;
   private AvailabilityZone availabilityZone;
   private Status vmState;
   private Boolean bootVolume;
   private Boolean bootCdrom;
   private LicenceType licencetype;
   private List<Nic> nics = new ArrayList<Nic>();
   private List<Volume> volumes = new ArrayList<Volume>();
   private List<CDRom> cdroms = new ArrayList<CDRom>();

   /**
    * @return the licencetype
    */
   public LicenceType getLicencetype() {
      return licencetype;
   }

   /**
    * @param licencetype the licencetype to set
    */
   public void setLicencetype(LicenceType licencetype) {
      this.licencetype = licencetype;
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
   public Boolean getBootVolume() {
      return bootVolume;
   }

   /**
    * @param bootVolume the bootVolume to set
    */
   public void setBootVolume(Boolean bootVolume) {
      this.bootVolume = bootVolume;
   }

   /**
    * @return the bootCdrom
    */
   public Boolean getBootCdrom() {
      return bootCdrom;
   }

   /**
    * @param bootCdrom the bootCdrom to set
    */
   public void setBootCdrom(Boolean bootCdrom) {
      this.bootCdrom = bootCdrom;
   }

   /**
    * @return the nics
    */
   public List<Nic> getNics() {
      return nics;
   }

   /**
    * @param nics the nics to set
    */
   public void setNics(List<Nic> nics) {
      this.nics = nics;
   }

   /**
    * @return the volumes
    */
   public List<Volume> getVolumes() {
      return volumes;
   }

   /**
    * @param volumes the volumes to set
    */
   public void setVolumes(List<Volume> volumes) {
      this.volumes = volumes;
   }

   /**
    * @return the cdroms
    */
   public List<CDRom> getCdroms() {
      return cdroms;
   }

   /**
    * @param cdroms the cdroms to set
    */
   public void setCdroms(List<CDRom> cdroms) {
      this.cdroms = cdroms;
   }
}
