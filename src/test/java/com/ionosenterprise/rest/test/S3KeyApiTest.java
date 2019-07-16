package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.S3Key;
import com.ionosenterprise.rest.domain.S3Keys;
import com.ionosenterprise.rest.domain.User;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.UserResource;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class S3KeyApiTest extends BaseTest {

    private static String userId;
    private static String s3KeyId;

    @BeforeClass
    public static void t1_createS3Key() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException, InterruptedException {

        User user = UserResource.getUser();
        User newUser = ionosEnterpriseApi.getUserApi().createUser(user);
        assertNotNull(newUser);
        userId = newUser.getId();
        waitTillProvisioned(newUser.getRequestId());

        S3Key newS3Key = ionosEnterpriseApi.getS3KeyApi().creates3Key(userId);
        assertNotNull(newS3Key);
        assertNotNull(newS3Key.getProperties().getSecretKey());
        assertEquals(newS3Key.getProperties().getActive(), true);
        s3KeyId = newS3Key.getId();
        waitTillProvisioned(newS3Key.getRequestId());
    }

    @Test
    public void t2_testGetAllS3Keys() throws RestClientException, IOException {
        S3Keys s3Keys = ionosEnterpriseApi.getS3KeyApi().getAllS3Keys(userId);
        assertNotNull(s3Keys);
        assertTrue(s3Keys.getItems().size() > 0);
    }

    @Test
    public void t3_testGetS3Key() throws RestClientException, IOException {
        S3Key s3Key = ionosEnterpriseApi.getS3KeyApi().getS3Key(userId, s3KeyId);
        assertNotNull(s3Key);
        assertNotNull(s3Key.getProperties().getSecretKey());
        assertEquals(s3Key.getProperties().getActive(), true);
    }

    @Test
    public void t4_testGetS3KeyFail() throws IOException {
        try {
            ionosEnterpriseApi.getS3KeyApi().getS3Key(userId, CommonResource.getBadId());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t5_testUpdateS3Key() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        S3Key s3Key = new S3Key();
        s3Key.getProperties().setActive(false);

        S3Key updatedS3Key = ionosEnterpriseApi.getS3KeyApi().updateS3Key(userId, s3KeyId, s3Key.getProperties());
        assertEquals(updatedS3Key.getProperties().getActive(), s3Key.getProperties().getActive());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getS3KeyApi().deleteS3Key(userId, s3KeyId);
        ionosEnterpriseApi.getUserApi().deleteUser(userId);
    }

}
