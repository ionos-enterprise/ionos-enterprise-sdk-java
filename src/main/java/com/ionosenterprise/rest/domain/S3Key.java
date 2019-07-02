package com.ionosenterprise.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class S3Key extends BaseResource {

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

        private String secretKey;
        private Boolean active;

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
    }
}
