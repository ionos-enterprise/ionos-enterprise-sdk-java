/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
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
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jasmin@stackpointcloud.com
 */
public class Volume extends BaseResource {

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
    public class Properties extends LvsProperties {

        private String name;
        private String type;
        private Integer size;
        private AvailabilityZone availabilityZone;
        private Bus bus;
        private String image;
        private List<String> sshKeys;
        private String imagePassword;
        private String imageAlias;

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

            if (this.sshKeys == null) {
                this.sshKeys = new ArrayList<String>();
            }
            this.sshKeys = sshKeys;
        }

        public String getImagePassword() {
            return imagePassword;
        }

        public void setImagePassword(String imagePassword) {
            this.imagePassword = imagePassword;
        }

        public String getImageAlias() {
            return imageAlias;
        }

        public void setImageAlias(String imageAlias) {
            this.imageAlias = imageAlias;
        }
    }

    private Properties properties = new Properties();
}
