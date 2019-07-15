package com.ionosenterprise.rest.client;

public class ResourcePathBuilder {

    private static final String IONOS_ENTERPRISE_API_URL = "https://api.ionos.com/cloudapi/v5/";
    private static final String DEPTH_10 = "?depth=10";

    private String resourcePath;
    private String pathSegment = "";
    private String depth = "";


    private ResourcePathBuilder(String pathFormat){
        this.resourcePath = pathFormat;
    }

    public static ResourcePathBuilder create(String pathFormat) {
        if (pathFormat.startsWith("/")){
            pathFormat = pathFormat.substring(1);
        }
        if (pathFormat.endsWith("/")){
            pathFormat = pathFormat.substring(0, pathFormat.length()-1);
        }
        return new ResourcePathBuilder(pathFormat);
    }

    public ResourcePathBuilder withDepth() throws IllegalArgumentException {
        this.depth = DEPTH_10;
        return this;
    }

    public ResourcePathBuilder withPathParams(String... params) throws IllegalArgumentException {
        this.resourcePath = String.format(resourcePath, params);
        return this;
    }

    public ResourcePathBuilder appendPathSegment(String pathSegment) throws IllegalArgumentException {
        this.pathSegment = this.pathSegment.concat("/" + pathSegment);
        return this;
    }

    public String build() {
        return getUrlBase().concat(resourcePath).concat(pathSegment).concat(depth);
    }

    private String getUrlBase() {
        String urlBase;
        if (System.getenv("IONOS_ENTERPRISE_API_URL") != null) {
            urlBase = System.getenv("IONOS_ENTERPRISE_API_URL");
            if (!urlBase.endsWith("/")) {
                urlBase += ("/");
            }
        } else {
            urlBase = IONOS_ENTERPRISE_API_URL;
        }

        return urlBase;
    }
}
