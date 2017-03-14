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

/**
 *
 * @author jasmin.gacic
 */
public class DataCenter extends ProfitbricksBase {

    private Properties properties = new Properties();
    private Entities entities;

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
        if (entities == null) {
            entities = new Entities();
        }
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Properties {

        private String name;
        private String description;
        private String location;
        private String version;

        /**
         * @param location the location to set
         */
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
         * @return the version
         */
        public String getVersion() {
            return version;
        }

        /**
         * @param version the version to set
         */
        public void setVersion(String version) {
            this.version = version;
        }
    }

    public class Entities {

        private Servers servers = new Servers();
        private Volumes volumes = new Volumes();
        private LoadBalancers loadbalancers = new LoadBalancers();
        private Lans lans = new Lans();

        /**
         * @return the servers
         */
        public Servers getServers() {
            return servers;
        }

        /**
         * @param servers the servers to set
         */
        public void setServers(Servers servers) {
            this.servers = servers;
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
         * @return the loadbalancers
         */
        public LoadBalancers getLoadbalancers() {
            return loadbalancers;
        }

        /**
         * @param loadbalancers the loadbalancers to set
         */
        public void setLoadbalancers(LoadBalancers loadbalancers) {
            this.loadbalancers = loadbalancers;
        }

        /**
         * @return the lans
         */
        public Lans getLans() {
            return lans;
        }

        /**
         * @param lans the lans to set
         */
        public void setLans(Lans lans) {
            this.lans = lans;
        }
    }
}
