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
import com.ionosenterprise.rest.test.resource.DataCenterResource;
import com.ionosenterprise.rest.test.resource.ServerResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDRomTest extends BaseTest {

    private static String dataCenterId;
    private static String serverId;
    private static String romId;

    @BeforeClass
    public static void t1_createCDRom() throws Exception {
        DataCenter newDatacenter = ionosEnterpriseApi.getDataCenterApi().createDataCenter(DataCenterResource.getDataCenter());
        assertNotNull(newDatacenter);
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        Server newServer = ionosEnterpriseApi.getServerApi().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        serverId = newServer.getId();
        waitTillProvisioned(newServer.getRequestId());

        CDRom rom = ionosEnterpriseApi.getServerApi().attachCDRom(dataCenterId, serverId, getImageId());
        assertNotNull(rom);
        romId = rom.getId();
        waitTillProvisioned(rom.getRequestId());
    }

    @Test
    public void t2_ListCDRoms() throws RestClientException, IOException {
        CDRoms roms = ionosEnterpriseApi.getServerApi().getAllAttachedCDRoms(dataCenterId, serverId);
        assertNotNull(roms);
        assertTrue(roms.getItems().size() > 0);
    }

    @Test
    public void t3_GetCDRom() throws RestClientException, IOException {
        CDRom rom = ionosEnterpriseApi.getServerApi().getAttachedCDRom(dataCenterId, serverId, romId);
        assertNotNull(rom);
    }

    @Test
    public void t4_DetachCDRom() throws RestClientException, IOException {
        ionosEnterpriseApi.getServerApi().detachCDRom(dataCenterId, serverId, romId);
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getDataCenterApi().deleteDataCenter(dataCenterId);
    }

    protected static String getImageId() throws RestClientException, IOException {
        Images images = ionosEnterpriseApi.getImageApi().getAllImages();
        for (Image image : images.getItems()) {
            if (image.getProperties().getName().toLowerCase().contains("centos".toLowerCase()) && image.getProperties().getLocation().equals("us/las")
                    && image.getProperties().getIsPublic() && image.getProperties().getImageType().equals("CDROM")) {
                return image.getId();
            }
        }
        return "";
    }
}
