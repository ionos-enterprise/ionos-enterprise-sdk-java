package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Label;
import com.ionosenterprise.rest.domain.Labels;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AbstractLabelAPI extends AbstractBaseAPI {

    public AbstractLabelAPI(String pathFormat) {
        super(pathFormat);
    }

    /**
     * Returns the rest response containing all labels for single resource.
     *
     * @param pathParams the path params with the resource IDs.
     * @return the Labels object containing a list of Label objects.
     * @throws RestClientException
     * @throws IOException
     */
    public Labels getAllLabels(List<String> pathParams) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(getParentResourcePath(pathParams))
                .concat("/").concat(getResourceId(pathParams)).concat("/")
                .concat("labels").concat(getDepth()), null, Labels.class);
    }

    /**
     * Returns the rest response containing a label fetching using resource id and label key.
     *
     * @param pathParams the path params with the resource IDs.
     * @param labelKey the key of the label.
     * @return the Label object with properties and metadata.
     * @throws RestClientException
     * @throws IOException
     */
    public Label getLabel(List<String> pathParams, String labelKey) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(getParentResourcePath(pathParams))
                .concat("/").concat(getResourceId(pathParams)).concat("/")
                .concat("labels").concat("/").concat(labelKey).concat(getDepth()), null, Label.class);
    }

    /**
     * Create label on a particular resource.
     *
     * @param pathParams the path params with the resource IDs.
     * @param label object to be created.
     * @return created label object.
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws RestClientException
     * @throws IOException
     */
    public Label createLabel(List<String> pathParams, Label label) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        return client.create(getUrlBase().concat(getParentResourcePath(pathParams)).concat("/")
                .concat(getResourceId(pathParams))
                .concat("/").concat("labels"), label, Label.class, 201);
    }

    /**
     * Update the value of the label.
     *
     * @param pathParams the path params with the resource IDs.
     * @param labelKey the key of the label.
     * @param labelProperties the label Properties object that has the following properties:
     * <br>
     * value = The value of the label key.
     * @return updated label object.
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws RestClientException
     * @throws IOException
     */
    public Label updateLabel(List<String> pathParams, String labelKey, Label.Properties labelProperties)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException {

        return client.put(getUrlBase().concat(getParentResourcePath(pathParams))
                .concat("/").concat(getResourceId(pathParams)).concat("/")
                .concat("labels").concat("/").concat(labelKey), labelProperties, Label.class, 200);
    }

    /**
     * Delete a label form a particular resource.
     *
     * @param pathParams the path params with the resource IDs.
     * @param labelKey the key of the label.
     * @throws RestClientException
     * @throws IOException
     */
    public void deleteLabel(List<String> pathParams, String labelKey) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(getParentResourcePath(pathParams)).concat("/")
                .concat(getResourceId(pathParams)).concat("/")
                .concat("labels").concat("/").concat(labelKey), 200);
    }

    private String getResourceId(List<String> pathParams) {
        return pathParams.get(pathParams.size()-1);
    }

    private String getParentResourcePath(List<String> pathParams) {
        return getResourcePath(pathParams.subList(0, pathParams.size()-1));
    }
}
