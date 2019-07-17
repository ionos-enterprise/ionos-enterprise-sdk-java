package com.ionosenterprise.rest.client;

import com.ionosenterprise.util.Constant;

import static com.ionosenterprise.util.Constant.DEPTH_10;
import static com.ionosenterprise.util.Constant.IONOS_ENTERPRISE_API_URL;

public class ResourcePathBuilder {

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
        if (System.getenv(IONOS_ENTERPRISE_API_URL) != null) {
            urlBase = System.getenv(IONOS_ENTERPRISE_API_URL);
            if (!urlBase.endsWith("/")) {
                urlBase += ("/");
            }
        } else {
            urlBase = Constant.IONOS_ENTERPRISE_API_URL_DEFAULT_VALUE;
        }

        return urlBase;
    }
}
