package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
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
        //Create a datacenter
        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST DC - Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();

        waitTillProvisioned(newDatacenter.getRequestId());

        assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());

        //Create a volume
        String imageId = VolumeTest.getImageId();
        Volume volume = new Volume();
        volume.getProperties().setName("SDK TEST VOLUME - Volume");
        volume.getProperties().setSize(10);
        volume.getProperties().setImage(imageId); //"Ubuntu-15.04-server-2015-07-01"
        volume.getProperties().setType("HDD");

        List<String> sshkeys = new ArrayList<String>();
        sshkeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDgnV5MWhBqpQLt66KGlMKi/VYtmVPUt6epSVxnxrvjayNto5flG2sH4cGqdI2C0NE9/w7BFNdwWqp0mL2kYynC8l+SejW/qjx37hrEBWIXqdTyumchm0LD/7K7P7/kz14IV5NcHjNAsntPgKjx/fzJlbA1VCQYmnOq9RZeKme44rdHYW0BBfgMzekcEbyGTNDGp51NYhVafZLXsF8MzCKlJ+NCPlDqzD6w0fQe/qtMFO8NbFyS9/Lk4prp4HAWEyLSM26w1iLycYpbpWrHw6oc1U7bNIgbsa0ezDu4+OPkxeHz7aG5TeJ/dn0Wftzdfy2sy5PJy5MnYP3RTuROsOv+chu+AshZNNJ9A4ar5gFXSX40sQ0i4GzxZGrsKhW42ZP4sElzV74gEBQ2BOIOJUh4qGRtnjsQCJHBs7DLgpeVeGUq2B7p5zDAlJBGCXiHuTgIM8aVnpdnNrFwmr9SF66iaTrt7x8HinNOCIIztMU15Fk2AYSxSEuju1d3VcPt/d0= spc@spc");
        volume.getProperties().setSshKeys(sshkeys);

        Volume newVolume = profitbricksApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        waitTillProvisioned(newVolume.getRequestId());

        volumeId = newVolume.getId();

        //Create a snapshot
        Snapshot snapshot = profitbricksApi.getSnapshot().createSnapshot(dataCenterId, volumeId, "SDK TEST SNAPSHOT - Snapshot", "SDK TEST Description");
        snapshotId = snapshot.getId();

        waitTillProvisioned(snapshot.getRequestId());

        //Create an IP Block

        IPBlock ipb = new IPBlock();

        ipb.getProperties().setLocation("us/las");
        List<String> ips = new ArrayList<String>();
        ips.add("123.123.123.123");
        ips.add("123.123.123.124");

        // ipb.getProperties().setIps(ips);
        ipb.getProperties().setSize(1);

        IPBlock iPBlock = profitbricksApi.getIpBlock().createIPBlock(ipb);

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
        Resource resource = profitbricksApi.getResource().getByType(Resource.ResourceType.datacenter,dataCenterId);

        assertEquals(resource.getId(),dataCenterId);
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
        Resource resource = profitbricksApi.getResource().getByType(Resource.ResourceType.snapshot,snapshotId);

        assertEquals(resource.getId(),snapshotId);
    }

    @Test
    public void testGetIPBlock() throws RestClientException, IOException {
        Resource resource = profitbricksApi.getResource().getByType(Resource.ResourceType.ipblock,ipBlockId);

        assertEquals(resource.getId(),ipBlockId);
    }
}
