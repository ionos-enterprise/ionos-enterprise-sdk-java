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
public class Group  extends ProfitbricksBase
{
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
        private Boolean createDataCenter;
        private  Boolean createSnapshot;
        private Boolean reserveIp;
        private  Boolean accessActivityLog;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getCreateDataCenter() {
            return createDataCenter;
        }

        public void setCreateDataCenter(Boolean createDataCenter) {
            this.createDataCenter = createDataCenter;
        }

        public Boolean getCreateSnapshot() {
            return createSnapshot;
        }

        public void setCreateSnapshot(Boolean createSnapshot) {
            this.createSnapshot = createSnapshot;
        }

        public Boolean getReserveIp() {
            return reserveIp;
        }

        public void setReserveIp(Boolean reserveIp) {
            this.reserveIp = reserveIp;
        }

        public Boolean getAccessActivityLog() {
            return accessActivityLog;
        }

        public void setAccessActivityLog(Boolean accessActivityLog) {
            this.accessActivityLog = accessActivityLog;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Entities {
        private Users users;
        private  Resources resources;

        public Users getUsers() {
            return users;
        }

        public void setUsers(Users users) {
            this.users = users;
        }

        public Resources getResources() {
            return resources;
        }

        public void setResources(Resources resources) {
            this.resources = resources;
        }
    }
}
