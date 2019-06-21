package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.*;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.IpBlockResource;
import com.ionosenterprise.rest.test.resource.SnapshotResource;
import com.ionosenterprise.rest.test.resource.VolumeResource;
import com.ionosenterprise.sdk.IonosEnterpriseApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResourceTest {
    static IonosEnterpriseApi ionosEnterpriseApi;

    static {
        try {
            ionosEnterpriseApi = new IonosEnterpriseApi();
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
        ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));

        //Crate a datacenter
        DataCenter datacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = datacenter.getId();

        waitTillProvisioned(datacenter.getRequestId());

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(VolumeTest.getImageId());

        Volume newVolume = ionosEnterpriseApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        waitTillProvisioned(newVolume.getRequestId());
        volumeId = newVolume.getId();

        Snapshot snapshot = SnapshotResource.getSnapshot();
        Snapshot newSnapshot = ionosEnterpriseApi.getSnapshot().createSnapshot(dataCenterId, volumeId,
                snapshot.getProperties().getName(),
                snapshot.getProperties().getDescription(),
                snapshot.getProperties().getLicenceType().name());
        assertNotNull(newSnapshot);

        snapshotId = newSnapshot.getId();

        waitTillProvisioned(newSnapshot.getRequestId());

        //Create an IP Block
        IPBlock iPBlock = ionosEnterpriseApi.getIpBlock().createIPBlock(IpBlockResource.getIpBlock());
        assertNotNull(iPBlock);

        ipBlockId = iPBlock.getId();

    }

    public static void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException, IOException {
        int counter = 120;
        for (int i = 0; i < counter; i++) {
            ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));
            RequestStatus request = ionosEnterpriseApi.getRequest().getRequestStatus(requestId);
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
        ionosEnterpriseApi.getIpBlock().deleteIPBlock(ipBlockId);
        ionosEnterpriseApi.getSnapshot().deleteSnapshot(snapshotId);
        ionosEnterpriseApi.getVolume().deleteVolume(dataCenterId, volumeId);
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

    @Test
    public void testGetAllResources() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResource().getAllResources();

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetAllDatacenters() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResource().getAllByType(Resource.ResourceType.datacenter);

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    //Unable to test in production at the moment. Images must be uploaded
//    @Test
//    public void testGetAllImages() throws RestClientException, IOException {
//        Resources resources = ionosEnterpriseApi.getResource().getAllByType(Resource.ResourceType.image);
//
//        assertNotNull(resources);
//        assertTrue(resources.getItems().size() > 0);
//    }

    @Test
    public void testGetAllSnapshots() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResource().getAllByType(Resource.ResourceType.snapshot);

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetAllIPBlocks() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResource().getAllByType(Resource.ResourceType.ipblock);

        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetDatacenter() throws RestClientException, IOException {
        Resource resource = ionosEnterpriseApi.getResource().getByType(Resource.ResourceType.datacenter, dataCenterId);

        assertEquals(resource.getId(), dataCenterId);
    }

    //Unable to test in production at the moment. Images must be uploaded
//    @Test
//    public void testGetImage() throws RestClientException, IOException {
//        Resource resource = ionosEnterpriseApi.getResource().getByType(Resource.ResourceType.image,imageId);
//
//        assertEquals(resource.getId(),imageId);
//    }

    @Test
    public void testGetSnapshot() throws RestClientException, IOException {
        Resource resource = ionosEnterpriseApi.getResource().getByType(Resource.ResourceType.snapshot, snapshotId);

        assertEquals(resource.getId(), snapshotId);
    }

    @Test
    public void testGetIPBlock() throws RestClientException, IOException {
        Resource resource = ionosEnterpriseApi.getResource().getByType(Resource.ResourceType.ipblock, ipBlockId);

        assertEquals(resource.getId(), ipBlockId);
    }
}
