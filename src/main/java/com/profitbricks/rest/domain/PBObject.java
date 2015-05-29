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
public class PBObject {

   private String id;
   private String name;
   private Status vmState;
   private String description;
   private String cores;
   private String ram;
   private AvailabilityZone availabilityZone;
   private Boolean bootVolume;
   private Boolean bootCdrom;
   private String licencetype;
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
    * @return the licencetype
    */
   public String getLicencetype() {
      return licencetype;
   }

   /**
    * @param licencetype the licencetype to set
    */
   public void setLicencetype(String licencetype) {
      this.licencetype = licencetype;
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
}
