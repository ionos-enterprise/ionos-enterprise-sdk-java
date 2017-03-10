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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.profitbricks.rest.domain.Image;
import com.profitbricks.rest.domain.Volume;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class PBObject {

   private String id;
   private String name;
   private Status vmState;
   private String description;
   private String cores;
   private String ram;
   private AvailabilityZone availabilityZone;
   private BootObject bootVolume;
   private BootObject bootCdrom;
   private String licenceType;
   private String sourceMac;
   private String sourceIp;
   private String targetIp;
   private String portRangeStart;
   private String portRangeEnd;
   private String icmpType;
   private String icmpCode;
   private List<String> ips;
   private Boolean dhcp;
   private String lan;
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
   @JsonProperty("public")
   private Boolean isPublic;

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
    * @return the bootVolume
    */
   public BootObject getBootVolume() {
      return bootVolume;
   }

   /**
    * @param bootVolume the bootVolume to set
    */
   public void setBootVolume(BootObject bootVolume) {
      this.bootVolume = bootVolume;
   }

   /**
    * @return the bootCdrom
    */
   public BootObject getBootCdrom() {
      return bootCdrom;
   }

   /**
    * @param bootCdrom the bootCdrom to set
    */
   public void setBootCdrom(BootObject bootCdrom) {
      this.bootCdrom = bootCdrom;
   }

   /**
    * @return the licenceType
    */
   public String getLicenceType() {
      return licenceType;
   }

   /**
    * @param licencetype the licenceType to set
    */
   public void setLicenceType(String licencetype) {
      this.licenceType = licencetype;
   }

   /**
    * @return the sourceMac
    */
   public String getSourceMac() {
      return sourceMac;
   }

   /**
    * @param sourceMac the sourceMac to set
    */
   public void setSourceMac(String sourceMac) {
      this.sourceMac = sourceMac;
   }

   /**
    * @return the sourceIp
    */
   public String getSourceIp() {
      return sourceIp;
   }

   /**
    * @param sourceIp the sourceIp to set
    */
   public void setSourceIp(String sourceIp) {
      this.sourceIp = sourceIp;
   }

   /**
    * @return the targetIp
    */
   public String getTargetIp() {
      return targetIp;
   }

   /**
    * @param targetIp the targetIp to set
    */
   public void setTargetIp(String targetIp) {
      this.targetIp = targetIp;
   }

   /**
    * @return the portRangeStart
    */
   public String getPortRangeStart() {
      return portRangeStart;
   }

   /**
    * @param portRangeStart the portRangeStart to set
    */
   public void setPortRangeStart(String portRangeStart) {
      this.portRangeStart = portRangeStart;
   }

   /**
    * @return the portRangeEnd
    */
   public String getPortRangeEnd() {
      return portRangeEnd;
   }

   /**
    * @param portRangeEnd the portRangeEnd to set
    */
   public void setPortRangeEnd(String portRangeEnd) {
      this.portRangeEnd = portRangeEnd;
   }

   /**
    * @return the icmpType
    */
   public String getIcmpType() {
      return icmpType;
   }

   /**
    * @param icmpType the icmpType to set
    */
   public void setIcmpType(String icmpType) {
      this.icmpType = icmpType;
   }

   /**
    * @return the icmpCode
    */
   public String getIcmpCode() {
      return icmpCode;
   }

   /**
    * @param icmpCode the icmpCode to set
    */
   public void setIcmpCode(String icmpCode) {
      this.icmpCode = icmpCode;
   }

   /**
    * @return the ips
    */
   public List<String> getIps() {
      return ips;
   }

   /**
    * @param ips the ips to set
    */
   public void setIps(List<String> ips) {
      if (ips == null)
         ips = new ArrayList<String>();
      this.ips = ips;
   }

   /**
    * @return the dhcp
    */
   public Boolean getDhcp() {
      return dhcp;
   }

   /**
    * @param dhcp the dhcp to set
    */
   public void setDhcp(Boolean dhcp) {
      this.dhcp = dhcp;
   }

   /**
    * @return the lan
    */
   public String getLan() {
      return lan;
   }

   /**
    * @param lan the lan to set
    */
   public void setLan(String lan) {
      this.lan = lan;
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
    * @return the isPublic
    */
   public Boolean getIsPublic() {
      return isPublic;
   }

   /**
    * @param isPublic the isPublic to set
    */
   public void setIsPublic(Boolean isPublic) {
      this.isPublic = isPublic;
   }
   
   public class BootObject{
      private String id;

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
   }
}
