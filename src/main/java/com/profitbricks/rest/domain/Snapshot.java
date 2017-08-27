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

/**
 * @author jasmin@stackpointcloud.com
 */
public class Snapshot extends ProfitbricksBase {

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
        private String description;
        public String location;
        private Integer size;
        private LicenceType licenceType;
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
        private Boolean secAuthProtection;

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return this.location;
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
         * @return the size
         */
        public Integer getSize() {
            return size;
        }

        /**
         * @param size the size to set
         */
        public void setSize(Integer size) {
            this.size = size;
        }

        /**
         * @return the licenceType
         */
        public LicenceType getLicenceType() {
            return licenceType;
        }

        /**
         * @param licenceType the licenceType to set
         */
        public void setLicenceType(LicenceType licenceType) {
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

        public Boolean getSecAuthProtection() {
            return secAuthProtection;
        }

        public void setSecAuthProtection(Boolean secAuthProtection) {
            this.secAuthProtection = secAuthProtection;
        }
    }

    private Properties properties = new Properties();
}
