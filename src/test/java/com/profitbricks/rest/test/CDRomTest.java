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
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class CDRomTest {


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
    static String romId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter datacenter = new DataCenter();
        datacenter.getProperties().setName("SDK TEST SERVER - Server");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        waitTillProvisioned(newDatacenter.getRequestId());
        dataCenterId = newDatacenter.getId();

        Server server = new Server();
        server.getProperties().setName("SDK TEST SERVER - Server");
        server.getProperties().setRam(1024);
        server.getProperties().setCores(1);

        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, server);
        waitTillProvisioned(newServer.getRequestId());

        assertNotNull(newServer);

        serverId = newServer.getId();

        CDRom rom = profitbricksApi.getServer().attachCDRom(dataCenterId, serverId, getImageId());

        assertNotNull(rom);
        waitTillProvisioned(rom.getRequestId());
        romId = rom.getId();
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getServer().deleteServer(dataCenterId, serverId);
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }

    @Test
    public void orderedTests() throws RestClientException, IOException {
        testListCDRoms();
        testGetCDRom();

        testDetachCDRom();
    }

    private void testDetachCDRom() throws RestClientException, IOException {
        profitbricksApi.getServer().detachCDRom(dataCenterId, serverId, romId);
    }

    public void testListCDRoms() throws RestClientException, IOException {
        CDRoms roms = profitbricksApi.getServer().getAllAttachedCDRoms(dataCenterId, serverId);

        assertNotNull(roms);
        assertTrue(roms.getItems().size() > 0);
    }


    public void testGetCDRom() throws RestClientException, IOException {
        CDRom rom = profitbricksApi.getServer().getAttachedCDRom(dataCenterId, serverId, romId);
        System.out.println(rom.getId());
    }

    public static String getImageId() throws RestClientException, IOException {
        Images images = profitbricksApi.getImage().getAllImages();
        for (Image image : images.getItems()) {
            if (image.getProperties().getName().toLowerCase().contains("centos".toLowerCase()) && image.getProperties().getLocation().equals("us/las")
                    && image.getProperties().getIsPublic() && image.getProperties().getImageType().equals("CDROM")) {
                return image.getId();
            }
        }
        return "";
    }
}
