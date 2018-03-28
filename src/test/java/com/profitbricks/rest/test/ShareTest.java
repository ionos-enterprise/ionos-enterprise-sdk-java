package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import com.profitbricks.rest.test.resource.CommonResource;
import com.profitbricks.rest.test.resource.DataCenterResource;
import com.profitbricks.rest.test.resource.GroupResource;
import com.profitbricks.rest.test.resource.ShareResource;
import com.profitbricks.sdk.ProfitbricksApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    static String dataCenterId;

    @BeforeClass
    public static void t1_createShare() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter datacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = datacenter.getId();

        waitTillProvisioned(datacenter.getRequestId());

        Group group = profitbricksApi.getGroup().createGroup(GroupResource.getGroup());
        groupId = group.getId();

        waitTillProvisioned(group.getRequestId());
        Share newShare = profitbricksApi.getShare().createShare(groupId, dataCenterId, ShareResource.getShare());
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
    public void t2_testGetAllShares() throws RestClientException, IOException {
        Shares shares = profitbricksApi.getShare().getAllShares(groupId);

        assertNotNull(shares);
        assertTrue(shares.getItems().size() > 0);
    }

    @Test
    public void t3_testGetShare() throws RestClientException, IOException {
        Share share = profitbricksApi.getShare().getShare(groupId, shareId);
        assertNotNull(share);
        assertEquals(share.getProperties().getEditPrivilege(),ShareResource.getShare().getProperties().getEditPrivilege());
        assertEquals(share.getProperties().getSharePrivilege(),ShareResource.getShare().getProperties().getSharePrivilege());
    }

    @Test
    public void t4_testGetShareFail() throws RestClientException, IOException {
        try {
            Share share = profitbricksApi.getShare().getShare(groupId, CommonResource.getBadId());
            assertNotNull(share);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_updateShare() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Share share = profitbricksApi.getShare().getShare(groupId, shareId);
        share.getProperties().setEditPrivilege(false);

        Share shareUpdated = profitbricksApi.getShare().updateShare(groupId, shareId, share.getProperties());
        assertEquals(shareUpdated.getProperties().getEditPrivilege(),ShareResource.getEditShare().getProperties().getEditPrivilege());

    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getShare().deleteShare(groupId, dataCenterId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
        profitbricksApi.getGroup().deleteGroup(groupId);
    }
}
