/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.*;
import com.ionosenterprise.rest.test.resource.CommonResource;
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.ServerResource;
import com.ionosenterprise.rest.test.resource.VolumeResource;
import static com.ionosenterprise.rest.test.DatacenterTest.waitTillProvisioned;

import com.ionosenterprise.sdk.IonosEnterpriseApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VolumeTest {

    static IonosEnterpriseApi ionosEnterpriseApi;

    static {
        try {
            ionosEnterpriseApi = new IonosEnterpriseApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dataCenterId;
    static String serverId;
    static String volumeId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        ionosEnterpriseApi.setCredentials(System.getenv("IONOS_ENTERPRISE_USERNAME"), System.getenv("IONOS_ENTERPRISE_PASSWORD"));

        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server newServer = ionosEnterpriseApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        serverId = newServer.getId();

        Volume volume = VolumeResource.getVolume();
        volume.getProperties().setImage(getImageId());
        Volume newVolume = ionosEnterpriseApi.getVolume().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        waitTillProvisioned(newVolume.getRequestId());

        volumeId = newVolume.getId();

    }

    public static String getImageId() throws RestClientException, IOException {
        Images images = ionosEnterpriseApi.getImage().getAllImages();
        for (Image image : images.getItems()) {
            if (image.getProperties().getName().toLowerCase().contains("ubuntu".toLowerCase()) && image.getProperties().getLocation().equals("us/las")
                    && image.getProperties().getIsPublic() && image.getProperties().getImageType().equals("HDD")) {
                return image.getId();
            }
        }
        return "";
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        ionosEnterpriseApi.getVolume().deleteVolume(dataCenterId, volumeId);
        ionosEnterpriseApi.getServer().deleteServer(dataCenterId, serverId);
        ionosEnterpriseApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

    @Test
    public void t1_testGetAllVolumes() throws RestClientException, IOException {
        Volumes volumes = ionosEnterpriseApi.getVolume().getAllVolumes(dataCenterId);
        assertNotNull(volumes);
        assertTrue(volumes.getItems().size() > 0);
    }

    @Test
    public void t2_testGetVolume() throws RestClientException, IOException, InterruptedException {
        Volume volume = ionosEnterpriseApi.getVolume().getVolume(dataCenterId, volumeId);
        assertNotNull(volume);
        assertEquals(volume.getProperties().getName(), VolumeResource.getVolume().getProperties().getName());
        assertEquals(volume.getProperties().getSize(), VolumeResource.getVolume().getProperties().getSize());
        assertEquals(volume.getProperties().getType(), VolumeResource.getVolume().getProperties().getType());
        assertEquals(volume.getProperties().getAvailabilityZone(), VolumeResource.getVolume().getProperties().getAvailabilityZone());
    }

    @Test
    public void t3_testUpdateVolume() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        Volume after = ionosEnterpriseApi.getVolume().updateVolume(dataCenterId, volumeId, VolumeResource.getEditVolume().getProperties());

        assertEquals(after.getProperties().getName(), VolumeResource.getEditVolume().getProperties().getName());
        assertEquals(after.getProperties().getSize(), VolumeResource.getEditVolume().getProperties().getSize());
    }

    @Test
    public void t4_testAttachVolume() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Volume attachedVolume = ionosEnterpriseApi.getVolume().attachVolume(dataCenterId, serverId, volumeId);
        assertNotNull(attachedVolume);
        waitTillProvisioned(attachedVolume.getRequestId());

    }

    @Test
    public void t5_testGetAllAttachedVolumes() throws RestClientException, IOException {
        Volumes volumes = ionosEnterpriseApi.getVolume().getAllVolumes(dataCenterId, serverId);
        assertNotNull(volumes);
    }

    @Test
    public void t6_testDetachVolume() throws RestClientException, IOException, InterruptedException {
        ionosEnterpriseApi.getVolume().detachVolume(dataCenterId, serverId, volumeId);
    }

    @Test
    public void t7_testFailVolumeCreate() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        try {

            Volume newVolume = ionosEnterpriseApi.getVolume().createVolume(dataCenterId, VolumeResource.getBadVolume());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    @Test
    public void t8_testGetFailVolume() throws RestClientException, IOException, InterruptedException {
        try {
            Volume volume = ionosEnterpriseApi.getVolume().getVolume(dataCenterId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }
}
