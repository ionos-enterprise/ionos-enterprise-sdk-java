package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import com.profitbricks.rest.test.resource.DataCenterResource;
import com.profitbricks.rest.test.resource.IpBlockResource;
import com.profitbricks.rest.test.resource.SnapshotResource;
import com.profitbricks.rest.test.resource.VolumeResource;
import com.profitbricks.sdk.ProfitbricksApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ResourceTest {
    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dataCenterId;
    static String volumeId;
    static String snapshotId;
    static String ipBlockId;

    @BeforeClass
    public static void createResources() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        //Crate a datacenter
        DataCenter datacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = datacenter.getId();

        waitTillProvisioned(datacenter.getRequestId());

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(VolumeTest.getImageId()); //"Ubuntu-15.04-server-2015-07-01"

        Volume newVolume = profitbricksApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        waitTillProvisioned(newVolume.getRequestId());
        volumeId = newVolume.getId();

        //Create a snapshot
        Snapshot snapshot = profitbricksApi.getSnapshot().createSnapshot(dataCenterId, volumeId, SnapshotResource.getSnapshot().getProperties().getName(), SnapshotResource.getSnapshot().getProperties().getDescription());
        snapshotId = snapshot.getId();

        waitTillProvisioned(snapshot.getRequestId());

        //Create an IP Block
        IPBlock iPBlock = profitbricksApi.getIpBlock().createIPBlock(IpBlockResource.getIpBlock());

        assertNotNull(iPBlock);

        ipBlockId = iPBlock.getId();

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

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        profitbricksApi.getIpBlock().deleteIPBlock(ipBlockId);
        profitbricksApi.getSnapshot().deleteSnapshot(snapshotId);
        profitbricksApi.getVolume().deleteVolume(dataCenterId, volumeId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

    @Test
    public void testGetAllResources() throws RestClientException, IOException {
        Resources resources = profitbricksApi.getResource().getAllResources();

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetAllDatacenters() throws RestClientException, IOException {
        Resources resources = profitbricksApi.getResource().getAllByType(Resource.ResourceType.datacenter);

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    //Unable to test in production at the moment. Images must be uploaded
//    @Test
//    public void testGetAllImages() throws RestClientException, IOException {
//        Resources resources = profitbricksApi.getResource().getAllByType(Resource.ResourceType.image);
//
//        assertNotNull(resources);
//        assertTrue(resources.getItems().size() > 0);
//    }

    @Test
    public void testGetAllSnapshots() throws RestClientException, IOException {
        Resources resources = profitbricksApi.getResource().getAllByType(Resource.ResourceType.snapshot);

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetAllIPBlocks() throws RestClientException, IOException {
        Resources resources = profitbricksApi.getResource().getAllByType(Resource.ResourceType.ipblock);

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetDatacenter() throws RestClientException, IOException {
        Resource resource = profitbricksApi.getResource().getByType(Resource.ResourceType.datacenter, dataCenterId);

        assertEquals(resource.getId(), dataCenterId);
    }

    //Unable to test in production at the moment. Images must be uploaded
//    @Test
//    public void testGetImage() throws RestClientException, IOException {
//        Resource resource = profitbricksApi.getResource().getByType(Resource.ResourceType.image,imageId);
//
//        assertEquals(resource.getId(),imageId);
//    }

    @Test
    public void testGetSnapshot() throws RestClientException, IOException {
        Resource resource = profitbricksApi.getResource().getByType(Resource.ResourceType.snapshot, snapshotId);

        assertEquals(resource.getId(), snapshotId);
    }

    @Test
    public void testGetIPBlock() throws RestClientException, IOException {
        Resource resource = profitbricksApi.getResource().getByType(Resource.ResourceType.ipblock, ipBlockId);

        assertEquals(resource.getId(), ipBlockId);
    }
}
