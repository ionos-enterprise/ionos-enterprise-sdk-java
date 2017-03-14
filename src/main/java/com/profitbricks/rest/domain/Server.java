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
 * @author jasmin.gacic
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
        private String cores;
        private String ram;
        private AvailabilityZone availabilityZone;
        private Status vmState;
        private Image bootVolume;
        private Image bootCdrom;
        private LicenceType licencetype;
        private String cpuFamily;

        /**
         * @return the licencetype
         */
        public LicenceType getLicencetype() {
            return licencetype;
        }

        /**
         * @param licencetype the licencetype to set
         */
        public void setLicencetype(String licencetype) {
            this.licencetype.fromValue(licencetype);
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
