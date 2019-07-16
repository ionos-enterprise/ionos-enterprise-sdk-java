package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClient;
import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.S3Keys;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class S3KeyApi extends AbstractBaseApi {

    public S3KeyApi(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return "um/users/%s/s3keys";
    }

    /**
     * Retrieve the list of S3Keys for a user.
     *
     * @param userId
     * @return
     */
    public S3Keys getAllS3Keys(String userId) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withPathParams(userId).withDepth().build(), Collections.EMPTY_MAP,
                S3Keys.class);
    }

    /**
     * Retrieves a specified S3Key of a user.
     *
     * @param userId
     * @param s3KeyId
     * @return
     */
    public com.ionosenterprise.rest.domain.S3Key getS3Key(String userId, String s3KeyId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(userId).appendPathSegment(s3KeyId).withDepth()
                .build(), Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.S3Key.class);
    }

    /**
     * Create a S3Key for a user.
     *
     * @param userId
     * @return
     */
    public com.ionosenterprise.rest.domain.S3Key creates3Key(String userId) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        return client.create(getResourcePathBuilder().withPathParams(userId).build(),
                new com.ionosenterprise.rest.domain.S3Key(), com.ionosenterprise.rest.domain.S3Key.class,
                HttpStatus.SC_CREATED);
    }

    /**
     * Activate/deactivate a S3Key of a user.
     *
     * @param userId
     * @param s3KeyId
     * @param s3KeyProperties
     * @return
     */
    public com.ionosenterprise.rest.domain.S3Key updateS3Key(String userId, String s3KeyId,
            com.ionosenterprise.rest.domain.S3Key.Properties s3KeyProperties)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RestClientException,
            IOException {

        return client.put(getResourcePathBuilder().withPathParams(userId).appendPathSegment(s3KeyId).build(),
                s3KeyProperties, com.ionosenterprise.rest.domain.S3Key.class, HttpStatus.SC_ACCEPTED);

    }

    /**
     * Delete a S3Key of a user.
     *
     * @param userId
     * @param s3KeyId
     * @throws RestClientException
     * @throws IOException
     */
    public void deleteS3Key(String userId, String s3KeyId) throws RestClientException, IOException {
        client.delete(getResourcePathBuilder().withPathParams(userId).appendPathSegment(s3KeyId).build(),
                HttpStatus.SC_ACCEPTED);
    }
}
