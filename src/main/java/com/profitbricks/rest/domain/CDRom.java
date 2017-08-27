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

import java.util.List;

/**
 * @author jasmin@stackpointcloud.com
 */
public class CDRom extends ProfitbricksBase {

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
        private String location;
        private Float size;
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
        private String licenceType;
        private String imageType;
        @JsonProperty("public")
        private Boolean isPublic;
        private List<String> imageAliases;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Float getSize() {
            return size;
        }

        public void setSize(Float size) {
            this.size = size;
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

        public String getLicenceType() {
            return licenceType;
        }

        public void setLicenceType(String licenceType) {
            this.licenceType = licenceType;
        }

        public String getImageType() {
            return imageType;
        }

        public void setImageType(String imageType) {
            this.imageType = imageType;
        }

        public Boolean getPublic() {
            return isPublic;
        }

        public void setPublic(Boolean aPublic) {
            isPublic = aPublic;
        }

        public List<String> getImageAliases() {
            return imageAliases;
        }

        public void setImageAliases(List<String> imageAliases) {
            this.imageAliases = imageAliases;
        }
    }

    private Properties properties = new Properties();
}
