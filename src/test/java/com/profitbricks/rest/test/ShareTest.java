package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import com.profitbricks.sdk.ProfitbricksApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ShareTest {
    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String groupId;
    static String shareId;
    static  String dataCenterId;

    @BeforeClass
    public static void createShare() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        //Create a datacenter
        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST DC - Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();

        waitTillProvisioned(newDatacenter.getRequestId());

        assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());

        //Create a group
        Group group = new Group();

        group.getProperties().setName("Java SDK Test");
        group.getProperties().setCreateDataCenter(true);
        group.getProperties().setCreateSnapshot(true);
        group.getProperties().setReserveIp(true);
        group.getProperties().setAccessActivityLog(true);

        Group newGroup = profitbricksApi.getGroup().createGroup(group);
        groupId = newGroup.getId();

        waitTillProvisioned(newGroup.getRequestId());

        assertEquals(newGroup.getProperties().getName(), group.getProperties().getName());


        Share share = new Share();

        share.getProperties().setEditPrivilege(true);
        share.getProperties().setSharePrivilege(true);

        Share newShare =  profitbricksApi.getShare().createShare(groupId,dataCenterId,share);
        shareId = newShare.getId();
    }

    public static void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException, IOException {
        int counter = 120;
        for (int i = 0; i < counter; i++) {
            profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
            RequestStatus request = profitbricksApi.getRequest().getRequestStatus(requestId);
            TimeUnit.SECONDS.sleep(1);
            if (request.getMetadata().getStatus().equals("DONE")) {
                break;
            }
            if (request.getMetadata().getStatus().equals("FAILED")) {
                throw new IOException("The request execution has failed with following message: " + request.getMetadata().getMessage());
            }
        }
    }

    @Test
    public void testGetAllShares() throws RestClientException, IOException {
        Shares shares = profitbricksApi.getShare().getAllShares(groupId);

        assertNotNull(shares);
        assertTrue(shares.getItems().size() > 0);
    }

    @Test
    public void testGetShare() throws RestClientException, IOException {
        Share share = profitbricksApi.getShare().getShare(groupId,shareId);
        assertNotNull(share);
        assertTrue(share.getProperties().getEditPrivilege());
        assertTrue(share.getProperties().getSharePrivilege());
    }

    @Test
    public void testGetShareFail() throws RestClientException, IOException {
        try {
            Share share = profitbricksApi.getShare().getShare(groupId,"00000000-0000-0000-0000-000000000000");
            assertNotNull(share);
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void updateShare() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Share share = profitbricksApi.getShare().getShare(groupId,shareId);
        share.getProperties().setEditPrivilege(false);

        Share shareUpdated = profitbricksApi.getShare().updateShare(groupId,shareId, share.getProperties());
        assertFalse(shareUpdated.getProperties().getEditPrivilege());

    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getShare().deleteShare(groupId,dataCenterId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
        profitbricksApi.getGroup().deleteGroup(groupId);
    }
}
