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

import java.util.ArrayList;
import java.util.List;

/**
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Properties {

        private String name;
        private String type;
        private String size;
        private AvailabilityZone availabilityZone;
        private Bus bus;
        private String licenceType;
        private String image;
        private List<String> sshKeys = new ArrayList<String>();
        private String imagePassword;
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
        public Bus getBus() {
            return bus;
        }

        /**
         * @param bus the bus to set
         */
        public void setBus(Bus bus) {
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
         * @return the sshKeys
         */
        public List<String> getSshKeys() {
            return sshKeys;
        }

        /**
         * @param sshKeys the sshKeys to set
         */
        public void setSshKeys(List<String> sshKeys) {
            this.sshKeys = sshKeys;
        }

        public String getImagePassword() {
            return imagePassword;
        }

        public void setImagePassword(String imagePassword) {
            this.imagePassword = imagePassword;
        }

        public Boolean getCpuHotPlug() {
            return cpuHotPlug;
        }

        public void setCpuHotPlug(Boolean cpuHotPlug) {
            this.cpuHotPlug = cpuHotPlug;
        }

        public Boolean getCpuHotUnplug() {
            return cpuHotUnplug;
        }

        public void setCpuHotUnplug(Boolean cpuHotUnplug) {
            this.cpuHotUnplug = cpuHotUnplug;
        }

        public Boolean getRamHotPlug() {
            return ramHotPlug;
        }

        public void setRamHotPlug(Boolean ramHotPlug) {
            this.ramHotPlug = ramHotPlug;
        }

        public Boolean getRamHotUnplug() {
            return ramHotUnplug;
        }

        public void setRamHotUnplug(Boolean ramHotUnplug) {
            this.ramHotUnplug = ramHotUnplug;
        }

        public Boolean getNicHotPlug() {
            return nicHotPlug;
        }

        public void setNicHotPlug(Boolean nicHotPlug) {
            this.nicHotPlug = nicHotPlug;
        }

        public Boolean getNicHotUnplug() {
            return nicHotUnplug;
        }

        public void setNicHotUnplug(Boolean nicHotUnplug) {
            this.nicHotUnplug = nicHotUnplug;
        }

        public Boolean getDiscVirtioHotPlug() {
            return discVirtioHotPlug;
        }

        public void setDiscVirtioHotPlug(Boolean discVirtioHotPlug) {
            this.discVirtioHotPlug = discVirtioHotPlug;
        }

        public Boolean getDiscVirtioHotUnplug() {
            return discVirtioHotUnplug;
        }

        public void setDiscVirtioHotUnplug(Boolean discVirtioHotUnplug) {
            this.discVirtioHotUnplug = discVirtioHotUnplug;
        }

        public Boolean getDiscScsiHotPlug() {
            return discScsiHotPlug;
        }

        public void setDiscScsiHotPlug(Boolean discScsiHotPlug) {
            this.discScsiHotPlug = discScsiHotPlug;
        }

        public Boolean getDiscScsiHotUnplug() {
            return discScsiHotUnplug;
        }

        public void setDiscScsiHotUnplug(Boolean discScsiHotUnplug) {
            this.discScsiHotUnplug = discScsiHotUnplug;
        }
    }

    private Properties properties = new Properties();
}
