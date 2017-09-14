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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author denis@stackpointcloud.com
 */
public class Contract extends ProfitbricksBase {

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


    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Properties {


        private String owner;
        private String status;
        private String contractNumber;
        private ResourceLimits resourceLimits =  new ResourceLimits();


        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getContractNumber() {
            return contractNumber;
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
        }

        public ResourceLimits getResourceLimits() {
            return resourceLimits;
        }

        public void setResourceLimits(ResourceLimits resourceLimits) {
            this.resourceLimits = resourceLimits;
        }
    }


    public static class ResourceLimits {
        private Integer coresPerContract;
        private Integer coresPerServer;
        private Integer coresProvisioned;
        private Long hddLimitPerContract;
        private Long hddLimitPerVolume;
        private Long hddVolumeProvisioned;
        private Integer ramPerContract;
        private Integer ramPerServer;
        private Integer ramProvisioned;
        private Integer reservableIps;
        private Integer reservedIpsInUse;
        private Integer reservedIpsOnContract;
        private Long ssdLimitPerContract;
        private Long ssdLimitPerVolume;
        private Long ssdVolumeProvisioned;

        /**
         * @return coresPerContract the coresPerContract
         */
        public Integer getCoresPerContract() {
            return coresPerContract;
        }

        /**
         * @param coresPerContract the coresPerContract to set
         **/
        public void setCoresPerContract(Integer coresPerContract) {
            this.coresPerContract = coresPerContract;
        }

        public Integer getCoresPerServer() {
            return coresPerServer;
        }

        public void setCoresPerServer(Integer coresPerServer) {
            this.coresPerServer = coresPerServer;
        }

        public Integer getCoresProvisioned() {
            return coresProvisioned;
        }

        public void setCoresProvisioned(Integer coresProvisioned) {
            this.coresProvisioned = coresProvisioned;
        }

        public Long getHddLimitPerContract() {
            return hddLimitPerContract;
        }

        public void setHddLimitPerContract(Long hddLimitPerContract) {
            this.hddLimitPerContract = hddLimitPerContract;
        }

        public Long getHddLimitPerVolume() {
            return hddLimitPerVolume;
        }

        public void setHddLimitPerVolume(Long hddLimitPerVolume) {
            this.hddLimitPerVolume = hddLimitPerVolume;
        }

        public Long getHddVolumeProvisioned() {
            return hddVolumeProvisioned;
        }

        public void setHddVolumeProvisioned(Long hddVolumeProvisioned) {
            this.hddVolumeProvisioned = hddVolumeProvisioned;
        }

        public Integer getRamPerContract() {
            return ramPerContract;
        }

        public void setRamPerContract(Integer ramPerContract) {
            this.ramPerContract = ramPerContract;
        }

        public Integer getRamPerServer() {
            return ramPerServer;
        }

        public void setRamPerServer(Integer ramPerServer) {
            this.ramPerServer = ramPerServer;
        }

        public Integer getRamProvisioned() {
            return ramProvisioned;
        }

        public void setRamProvisioned(Integer ramProvisioned) {
            this.ramProvisioned = ramProvisioned;
        }

        public Integer getReservableIps() {
            return reservableIps;
        }

        public void setReservableIps(Integer reservableIps) {
            this.reservableIps = reservableIps;
        }

        public Integer getReservedIpsInUse() {
            return reservedIpsInUse;
        }

        public void setReservedIpsInUse(Integer reservedIpsInUse) {
            this.reservedIpsInUse = reservedIpsInUse;
        }

        public Integer getReservedIpsOnContract() {
            return reservedIpsOnContract;
        }

        public void setReservedIpsOnContract(Integer reservedIpsOnContract) {
            this.reservedIpsOnContract = reservedIpsOnContract;
        }

        public Long getSsdLimitPerContract() {
            return ssdLimitPerContract;
        }

        public void setSsdLimitPerContract(Long ssdLimitPerContract) {
            this.ssdLimitPerContract = ssdLimitPerContract;
        }

        public Long getSsdLimitPerVolume() {
            return ssdLimitPerVolume;
        }

        public void setSsdLimitPerVolume(Long ssdLimitPerVolume) {
            this.ssdLimitPerVolume = ssdLimitPerVolume;
        }

        public Long getSsdVolumeProvisioned() {
            return ssdVolumeProvisioned;
        }

        public void setSsdVolumeProvisioned(Long ssdVolumeProvisioned) {
            this.ssdVolumeProvisioned = ssdVolumeProvisioned;
        }

    }
}
