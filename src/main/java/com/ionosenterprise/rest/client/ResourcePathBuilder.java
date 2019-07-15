package com.ionosenterprise.rest.client;

public class ResourcePathBuilder {

    private static final String DEPTH_10 = "?depth=10";

    private String urlBase;
    private String resourcePath;
    private String pathSegment = "";
    private String depth = "";


    private ResourcePathBuilder(String pathFormat, String urlBase){
        this.urlBase = urlBase;
        this.resourcePath = pathFormat;
    }

    public static ResourcePathBuilder create(String pathFormat, String urlBase) {
        if (pathFormat.startsWith("/")){
            pathFormat = pathFormat.substring(1);
        }
        if (pathFormat.endsWith("/")){
            pathFormat = pathFormat.substring(0, pathFormat.length()-1);
        }
        return new ResourcePathBuilder(pathFormat, urlBase);
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
        return urlBase.concat(resourcePath).concat(pathSegment).concat(depth);
    }
}
