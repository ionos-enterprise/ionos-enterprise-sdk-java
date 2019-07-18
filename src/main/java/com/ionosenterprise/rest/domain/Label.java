package com.ionosenterprise.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Label extends BaseResource {

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

        private String key;
        private String value;
        private String resourceId;
        private String resourceType;
        private String resourceHref;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public String getResourceHref() {
            return resourceHref;
        }

        public void setResourceHref(String resourceHref) {
            this.resourceHref = resourceHref;
        }
    }
}
