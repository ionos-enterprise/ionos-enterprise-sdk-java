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
public class Server extends ProfitbricksBase {

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

    /**
     * @return the entities
     */
    public Entities getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public class Properties {

        private String name;
        private Integer cores;
        private Integer ram;
        private AvailabilityZone availabilityZone;
        private Status vmState;
        private Image bootVolume;
        private Image bootCdrom;
        private String cpuFamily;

        /**
         * @return the availabilityZone
         */
        public AvailabilityZone getAvailabilityZone() {
            return availabilityZone;
        }

        /**
         * @param availabilityZone the availabilityZone to set
         */
        public void setAvailabilityZone(String availabilityZone) {
            this.availabilityZone.fromValue(availabilityZone);
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
         * @return the cores
         */
        public Integer getCores() {
            return cores;
        }

        /**
         * @param cores the cores to set
         */
        public void setCores(Integer cores) {
            this.cores = cores;
        }

        /**
         * @return the ram
         */
        public Integer getRam() {
            return ram;
        }

        /**
         * @param ram the ram to set
         */
        public void setRam(Integer ram) {
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
        public Image getBootVolume() {
            return bootVolume;
        }

        /**
         * @param bootVolume the bootVolume to set
         */
        public void setBootVolume(Image bootVolume) {
            this.bootVolume = bootVolume;
        }

        /**
         * @return the bootCdrom
         */
        public Image getBootCdrom() {
            return bootCdrom;
        }

        /**
         * @param bootCdrom the bootCdrom to set
         */
        public void setBootCdrom(Image bootCdrom) {
            this.bootCdrom = bootCdrom;
        }

        /**
         * @return the cpuFamily
         */
        public String getCpuFamily() {
            return cpuFamily;
        }

        /**
         * @param cpuFamily the cpuFamily to set
         */
        public void setCpuFamily(String cpuFamily) {
            this.cpuFamily = cpuFamily;
        }

    }

    public class Entities {

        private Nics nics = new Nics();
        private Volumes volumes = new Volumes();
        private Images cdroms = new Images();

        /**
         * @return the nics
         */
        public Nics getNics() {
            return nics;
        }

        /**
         * @param nics the nics to set
         */
        public void setNics(Nics nics) {
            this.nics = nics;
        }

        /**
         * @return the volumes
         */
        public Volumes getVolumes() {
            return volumes;
        }

        /**
         * @param volumes the volumes to set
         */
        public void setVolumes(Volumes volumes) {
            this.volumes = volumes;
        }

        /**
         * @return the cdroms
         */
        public Images getCdroms() {
            return cdroms;
        }

        /**
         * @param cdroms the cdroms to set
         */
        public void setCdroms(Images cdroms) {
            this.cdroms = cdroms;
        }
    }

    private Properties properties = new Properties();
    private Entities entities = new Entities();

}
