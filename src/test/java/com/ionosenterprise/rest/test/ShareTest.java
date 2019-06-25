package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.*;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.GroupResource;
import com.ionosenterprise.rest.test.resource.ShareResource;
import com.ionosenterprise.sdk.IonosEnterpriseApi;
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
public class ShareTest extends BaseTest {

    private static String groupId;
    private static String shareId;
    private static String dataCenterId;

    @BeforeClass
    public static void t1_createShare() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        DataCenter datacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        assertNotNull(datacenter);
        dataCenterId = datacenter.getId();
        waitTillProvisioned(datacenter.getRequestId());

        Group group = ionosEnterpriseApi.getGroup().createGroup(GroupResource.getGroup());
        assertNotNull(group);
        groupId = group.getId();
        waitTillProvisioned(group.getRequestId());

        Share newShare = ionosEnterpriseApi.getShare().createShare(groupId, dataCenterId, ShareResource.getShare());
        assertNotNull(newShare);
        shareId = newShare.getId();
    }

    @Test
    public void t2_testGetAllShares() throws RestClientException, IOException {
        Shares shares = ionosEnterpriseApi.getShare().getAllShares(groupId);
        assertNotNull(shares);
        assertTrue(shares.getItems().size() > 0);
    }

    @Test
    public void t3_testGetShare() throws RestClientException, IOException {
        Share share = ionosEnterpriseApi.getShare().getShare(groupId, shareId);
        assertNotNull(share);
        Share.Properties properties = ShareResource.getShare().getProperties();
        assertEquals(share.getProperties().getEditPrivilege(), properties.getEditPrivilege());
        assertEquals(share.getProperties().getSharePrivilege(), properties.getSharePrivilege());
    }

    @Test
    public void t4_testGetShareFail() throws IOException {
        try {
            ionosEnterpriseApi.getShare().getShare(groupId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t5_updateShare() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Share share = ionosEnterpriseApi.getShare().getShare(groupId, shareId);
        share.getProperties().setEditPrivilege(false);
        Share shareUpdated = ionosEnterpriseApi.getShare().updateShare(groupId, shareId, share.getProperties());
        assertEquals(shareUpdated.getProperties().getEditPrivilege(),ShareResource.getEditShare().getProperties().getEditPrivilege());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getShare().deleteShare(groupId, dataCenterId);
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
        ionosEnterpriseApi.getGroup().deleteGroup(groupId);
    }
}
