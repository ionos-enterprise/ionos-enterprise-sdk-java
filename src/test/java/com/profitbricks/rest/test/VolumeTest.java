/*
 * Copyright (c) 2017, ProfitBricks GmbH
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
 * 4. Neither the name of the ProfitBricks nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ProfitBricks GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ProfitBricks GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;
import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jasmin@stackpointcloud.com
 */
public class VolumeTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dataCenterId;
    static String serverId;
    static String volumeId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        String imageId = getImageId();
        DataCenter datacenter = new DataCenter();
        datacenter.getProperties().setName("SDK TEST VOLUME - Data Center 28/7 1153");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server server = new Server();
        server.getProperties().setName("SDK TEST VOLUME - Server");
        server.getProperties().setRam(1024);
        server.getProperties().setCores(1);

        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, server);

        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        serverId = newServer.getId();

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

    }

    public static String getImageId() throws RestClientException, IOException {
        Images images = profitbricksApi.getImage().getAllImages();
        for (Image image : images.getItems()) {
            if (image.getProperties().getName().toLowerCase().contains("ubuntu-16".toLowerCase()) && image.getProperties().getLocation().equals("us/las")
                    && image.getProperties().getIsPublic() && image.getProperties().getImageType().equals("HDD")) {
                return image.getId();
            }
        }
        return "";
    }

    @AfterClass
    public static void cleanUp() throws RestClientException, IOException {
        profitbricksApi.getVolume().deleteVolume(dataCenterId, volumeId);
        profitbricksApi.getServer().deleteServer(dataCenterId, serverId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

    @Test
    public void orderedTest() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        testGetAllVolumes();
        testGetVolume();
        testUpdateVolume();
        testAttachVolume();
        testGetAllAttachedVolumes();
        testDetachVolume();
        testFailVolumeCreate();
    }

    private void testFailVolumeCreate() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        try {
            Volume volume = new Volume();
            volume.getProperties().setName("SDK TEST VOLUME - Volume");
            volume.getProperties().setSize(10);
            volume.getProperties().setImage("123"); //"Ubuntu-15.04-server-2015-07-01"
            volume.getProperties().setType("HDD");

            List<String> sshkeys = new ArrayList<String>();
            sshkeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDgnV5MWhBqpQLt66KGlMKi/VYtmVPUt6epSVxnxrvjayNto5flG2sH4cGqdI2C0NE9/w7BFNdwWqp0mL2kYynC8l+SejW/qjx37hrEBWIXqdTyumchm0LD/7K7P7/kz14IV5NcHjNAsntPgKjx/fzJlbA1VCQYmnOq9RZeKme44rdHYW0BBfgMzekcEbyGTNDGp51NYhVafZLXsF8MzCKlJ+NCPlDqzD6w0fQe/qtMFO8NbFyS9/Lk4prp4HAWEyLSM26w1iLycYpbpWrHw6oc1U7bNIgbsa0ezDu4+OPkxeHz7aG5TeJ/dn0Wftzdfy2sy5PJy5MnYP3RTuROsOv+chu+AshZNNJ9A4ar5gFXSX40sQ0i4GzxZGrsKhW42ZP4sElzV74gEBQ2BOIOJUh4qGRtnjsQCJHBs7DLgpeVeGUq2B7p5zDAlJBGCXiHuTgIM8aVnpdnNrFwmr9SF66iaTrt7x8HinNOCIIztMU15Fk2AYSxSEuju1d3VcPt/d0= spc@spc");
            volume.getProperties().setSshKeys(sshkeys);

            Volume newVolume = profitbricksApi.getVolume().createVolume(dataCenterId, volume);
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }

    public void testGetAllVolumes() throws RestClientException, IOException {
        Volumes volumes = profitbricksApi.getVolume().getAllVolumes(dataCenterId);
        assertNotNull(volumes);
    }

    public void testGetAllAttachedVolumes() throws RestClientException, IOException {
        Volumes volumes = profitbricksApi.getVolume().getAllVolumes(dataCenterId, serverId);
        assertNotNull(volumes);
    }

    public void testGetVolume() throws RestClientException, IOException, InterruptedException {
        Volume volume = profitbricksApi.getVolume().getVolume(dataCenterId, volumeId);
        assertNotNull(volume);
    }

    public void testUpdateVolume() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RestClientException, IOException {
        Volume.Properties properties = new Volume().new Properties();
        properties.setName("new name");

        Volume after = profitbricksApi.getVolume().updateVolume(dataCenterId, volumeId, properties);

        assertEquals(after.getProperties().getName(), properties.getName());
    }

    public void testAttachVolume() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Volume attachedVolume = profitbricksApi.getVolume().attachVolume(dataCenterId, serverId, volumeId);
        assertNotNull(attachedVolume);
        waitTillProvisioned(attachedVolume.getRequestId());

    }

    public void testDetachVolume() throws RestClientException, IOException, InterruptedException {
        profitbricksApi.getVolume().detachVolume(dataCenterId, serverId, volumeId);
    }
}
