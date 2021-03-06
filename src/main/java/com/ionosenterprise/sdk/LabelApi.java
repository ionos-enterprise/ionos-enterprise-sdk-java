package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClient;
import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Labels;
import com.ionosenterprise.util.Constant;

import java.io.IOException;
import java.util.Collections;

public class LabelApi extends AbstractBaseApi {

    public LabelApi(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return Constant.LABELS_RESOURCE_PATH_TEMPLATE;
    }

    /**
     * Retrieve all labels you have on your account resources.
     *
     * @return Labels object with properties and metadata.
     */
    public Labels getAllLabels() throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withDepth().build(), Collections.EMPTY_MAP, Labels.class);
    }

    /**
     * Retrieve a label by URN.
     *
     * @param  urn The unique URN of the label.
     * @return Label object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Label getLabel(String urn) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().appendPathSegment(urn).withDepth().build(),
                Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.Label.class);
    }
}
