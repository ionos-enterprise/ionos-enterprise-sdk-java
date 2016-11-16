/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.raw.ServerRaw;
import com.profitbricks.rest.domain.raw.VolumeRaw;

import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;

import com.profitbricks.sdk.ProfitbricksApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin.gacic
 */
public class VolumeTest {

    static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    static String dataCenterId;
    static String serverId;
    static String volumeId;
    private static String imageId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        imageId = getImageId("ubuntu-16", Location.US_LAS.value());
        DataCenterRaw datacenter = new DataCenterRaw();
        datacenter.getProperties().setName("SDK TEST VOLUME - Data Center 28/7 1153");
        datacenter.getProperties().setLocation(Location.US_LAS.value());
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        ServerRaw server = new ServerRaw();
        server.getProperties().setName("SDK TEST VOLUME - Server");
        server.getProperties().setRam("1024");
        server.getProperties().setCores("1");

        Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);

        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        serverId = newServer.getId();

        VolumeRaw volume = new VolumeRaw();
        volume.getProperties().setName("SDK TEST VOLUME - Volume");
        volume.getProperties().setSize("10");
        volume.getProperties().setImage(imageId); //"Ubuntu-15.04-server-2015-07-01"
        volume.getProperties().setType("HDD");

        List<String> sshkeys = new ArrayList<String>();
        sshkeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDgnV5MOhBqpQLt66KGlMKi/VYtmVPUt6epSVxnxrvjayNto5flG2sH4cGqdI2C0NE9/w7BFNdwWqp0mL2kYynC8l+SejW/qjx37hrEBWIXqdTyumchm0LD/7K7P7/kz14IV5NcHjNAsntPgKjx/fzJlbA1VCQYmnOq9RZeKme44rdHYW0BBfgMzekcEbyGTNDGp51NYhVafZLXsF8MzCKlJ+NCPlDqzD6w0fQe/qtMFO8NbFyS9/Lk4prp4HAWEyLSM26w1iLycYpbpWrHw6oc1U7bNIgbsa0ezDu4+OPkxeHz7aG5TeJ/dn0Wftzdfy2sy5PJy5MnYP3RTuROsOv+chu+AshZNNJ9A4ar5gFXSX40sQ0i4GzxZGrsKhW42ZP4sElzV74gEBQ2BOIOJUh4qGRtnjsQCJHBs7DLgpeVeGUq2B7p5zDAlJBGCXiHuTgIM8aVnpdnNrFwmr9SF66iaTrt7x8HinNOCIIztMU15Fk2AYSxSEuju1d3VcPt/d0= jasmingacic@Jasmins-MBP");
        volume.getProperties().setSshKeys(sshkeys);

        Volume newVolume = profitbricksApi.getVolumeApi().createVolume(dataCenterId, volume);
        assertNotNull(newVolume);
        waitTillProvisioned(newVolume.getRequestId());

        volumeId = newVolume.getId();

    }

    public static String getImageId(String imageName, String location) throws RestClientException, IOException {
        List<Image> images = profitbricksApi.getImageApi().getAllImages();
        for (Image image : images) {
            if (image.getName().toLowerCase().contains(imageName.toLowerCase()) && image.getLocation().equals(location)
                    && image.getIsPublic()) {
                return image.getId();
            }
        }
        return "";
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        profitbricksApi.getVolumeApi().deleteVolume(dataCenterId, volumeId);
        profitbricksApi.getServerApi().deleteServer(dataCenterId, serverId);
        profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }

    @Test
    public void orderedTest() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        testGetAllVolumes();
        testGetVolume();
        testAttachVolume();
        testGetAllAttachedVolumes();
        testDetachVolume();
    }

    public void testGetAllVolumes() throws RestClientException, IOException {
        List<Volume> volumes = profitbricksApi.getVolumeApi().getAllVolumes(dataCenterId);
        assertNotNull(volumes);
    }

    public void testGetAllAttachedVolumes() throws RestClientException, IOException {
        List<Volume> volumes = profitbricksApi.getVolumeApi().getAllVolumes(dataCenterId, serverId);
        assertNotNull(volumes);
    }

    public void testGetVolume() throws RestClientException, IOException, InterruptedException {
        Volume volume = profitbricksApi.getVolumeApi().getVolume(dataCenterId, volumeId);
        assertNotNull(volume);
    }

    public void testAttachVolume() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Volume attachedVolume = profitbricksApi.getVolumeApi().attachVolume(dataCenterId, serverId, volumeId);
        assertNotNull(attachedVolume);
        waitTillProvisioned(attachedVolume.getRequestId());

    }

    public void testDetachVolume() throws RestClientException, IOException, InterruptedException {
        profitbricksApi.getVolumeApi().detachVolume(dataCenterId, serverId, volumeId);
    }
}
