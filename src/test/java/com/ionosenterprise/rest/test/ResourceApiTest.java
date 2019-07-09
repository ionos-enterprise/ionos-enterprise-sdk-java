package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.*;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.IpBlockResource;
import com.ionosenterprise.rest.test.resource.SnapshotResource;
import com.ionosenterprise.rest.test.resource.VolumeResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class ResourceApiTest extends BaseTest {

    private static String dataCenterId;
    private static String volumeId;
    private static String snapshotId;
    private static String ipBlockId;

    @BeforeClass
    public static void createResources() throws RestClientException, IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        DataCenter datacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(DataCenterResource.getDataCenter());
        assertNotNull(datacenter);
        dataCenterId = datacenter.getId();
        waitTillProvisioned(datacenter.getRequestId());

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(getImageId());
        Volume newVolume = ionosEnterpriseApi.getVolumeApi().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        volumeId = newVolume.getId();
        waitTillProvisioned(newVolume.getRequestId());

        Snapshot.Properties properties = SnapshotResource.getSnapshot().getProperties();
        Snapshot snapshot = ionosEnterpriseApi.getSnapshotApi().createSnapshot(dataCenterId, volumeId,
                properties.getName(), properties.getDescription());
        assertNotNull(snapshot);
        snapshotId = snapshot.getId();
        waitTillProvisioned(snapshot.getRequestId());

        IPBlock iPBlock = ionosEnterpriseApi.getIpBlockApi().createIPBlock(IpBlockResource.getIpBlock());
        assertNotNull(iPBlock);
        ipBlockId = iPBlock.getId();
    }

    @Test
    public void testGetAllResources() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResourceApi().getAllResources();
        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetAllDatacenters() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResourceApi().getAllByType(Resource.ResourceType.datacenter);
        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetAllSnapshots() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResourceApi().getAllByType(Resource.ResourceType.snapshot);
        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetAllIPBlocks() throws RestClientException, IOException {
        Resources resources = ionosEnterpriseApi.getResourceApi().getAllByType(Resource.ResourceType.ipblock);
        assertNotNull(resources);
        assertTrue(resources.getItems().size() > 0);
    }

    @Test
    public void testGetDatacenter() throws RestClientException, IOException {
        Resource resource = ionosEnterpriseApi.getResourceApi().getByType(Resource.ResourceType.datacenter, dataCenterId);
        assertEquals(resource.getId(), dataCenterId);
    }

    @Test
    public void testGetSnapshot() throws RestClientException, IOException {
        Resource resource = ionosEnterpriseApi.getResourceApi().getByType(Resource.ResourceType.snapshot, snapshotId);
        assertEquals(resource.getId(), snapshotId);
    }

    @Test
    public void testGetIPBlock() throws RestClientException, IOException {
        Resource resource = ionosEnterpriseApi.getResourceApi().getByType(Resource.ResourceType.ipblock, ipBlockId);
        assertEquals(resource.getId(), ipBlockId);
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        ionosEnterpriseApi.getIpBlockApi().deleteIPBlock(ipBlockId);
        ionosEnterpriseApi.getSnapshotApi().deleteSnapshot(snapshotId);
        ionosEnterpriseApi.getVolumeApi().deleteVolume(dataCenterId, volumeId);
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }
}