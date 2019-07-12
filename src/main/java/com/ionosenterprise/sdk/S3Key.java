package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.S3Keys;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class S3Key extends BaseAPI {

    public S3Key() throws Exception {
        super("s3keys", "um/users");
    }

    /**
     * Retrieve the list of S3Keys for a user.
     *
     * @param userId
     * @return
     */
    public S3Keys getAllS3Keys(String userId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(parentResource).concat("/").concat(userId).concat("/").concat(resource)
                .concat(depth), null, S3Keys.class);
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

        return client.get(getUrlBase().concat(parentResource).concat("/").concat(userId).concat("/").concat(resource)
                .concat("/").concat(s3KeyId).concat(depth), null, com.ionosenterprise.rest.domain.S3Key.class);
    }

    /**
     * Create a S3Key for a user.
     *
     * @param userId
     * @return
     */
    public com.ionosenterprise.rest.domain.S3Key creates3Key(String userId) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        return client.create(getUrlBase().concat(parentResource).concat("/").concat(userId).concat("/").concat(resource),
                new com.ionosenterprise.rest.domain.S3Key(), com.ionosenterprise.rest.domain.S3Key.class, 201);
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
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        return client.put(getUrlBase().concat(parentResource).concat("/").concat(userId)
                .concat("/").concat(resource).concat("/").concat(s3KeyId), s3KeyProperties,
                com.ionosenterprise.rest.domain.S3Key.class, 202);

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
        client.delete(getUrlBase().concat(parentResource).concat("/").concat(userId).concat("/").concat(resource)
                .concat("/").concat(s3KeyId), 202);
    }
}
