package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.*;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.GroupResource;
import com.ionosenterprise.rest.test.resource.ShareResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShareApiTest extends BaseTest {

    private static String groupId;
    private static String shareId;
    private static String dataCenterId;

    @BeforeClass
    public static void t1_createShare() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        DataCenter datacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(DataCenterResource.getDataCenter());
        assertNotNull(datacenter);
        dataCenterId = datacenter.getId();
        waitTillProvisioned(datacenter.getRequestId());

        Group group = ionosEnterpriseApi.getGroupApi().createGroup(GroupResource.getGroup());
        assertNotNull(group);
        groupId = group.getId();
        waitTillProvisioned(group.getRequestId());

        Share newShare = ionosEnterpriseApi.getShareApi().createShare(groupId, dataCenterId, ShareResource.getShare());
        assertNotNull(newShare);
        shareId = newShare.getId();
    }

    @Test
    public void t2_testGetAllShares() throws RestClientException, IOException {
        Shares shares = ionosEnterpriseApi.getShareApi().getAllShares(groupId);
        assertNotNull(shares);
        assertTrue(shares.getItems().size() > 0);
    }

    @Test
    public void t3_testGetShare() throws RestClientException, IOException {
        Share share = ionosEnterpriseApi.getShareApi().getShare(groupId, shareId);
        assertNotNull(share);
        Share.Properties properties = ShareResource.getShare().getProperties();
        assertEquals(share.getProperties().getEditPrivilege(), properties.getEditPrivilege());
        assertEquals(share.getProperties().getSharePrivilege(), properties.getSharePrivilege());
    }

    @Test
    public void t4_testGetShareFail() throws IOException {
        try {
            ionosEnterpriseApi.getShareApi().getShare(groupId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_updateShare() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Share share = ionosEnterpriseApi.getShareApi().getShare(groupId, shareId);
        share.getProperties().setEditPrivilege(false);
        Share shareUpdated = ionosEnterpriseApi.getShareApi().updateShare(groupId, shareId, share.getProperties());
        assertEquals(shareUpdated.getProperties().getEditPrivilege(),ShareResource.getEditShare().getProperties().getEditPrivilege());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getShareApi().deleteShare(groupId, dataCenterId);
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
        ionosEnterpriseApi.getGroupApi().deleteGroup(groupId);
    }
}
