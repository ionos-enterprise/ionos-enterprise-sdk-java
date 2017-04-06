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
public class LanTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String dataCenterId;
    private static String lanId;

    @BeforeClass
    public static void createDataCenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {

        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST Lan - Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
        dataCenterId = newDatacenter.getId();
        assertEquals(newDatacenter.getProperties().getName(), datacenter.getProperties().getName());
        waitTillProvisioned(newDatacenter.getRequestId());

        Lan lan = new Lan();

        lan.getProperties().setName("SDK TEST Lan - Lan");
        lan.getProperties().setIsPublic(false);

        Lan newLan = profitbricksApi.getLan().createLan(dataCenterId, lan);
        lanId = newLan.getId();
        assertNotNull(newLan);
        waitTillProvisioned(newLan.getRequestId());
    }

    @Test
    public void getAllLans() throws RestClientException, IOException {
        Lans lans = profitbricksApi.getLan().getAllLans(dataCenterId);
        assertNotNull(lans);
    }

    @Test
    public void getLan() throws RestClientException, IOException {
        Lan lan = profitbricksApi.getLan().getLan(dataCenterId, lanId);
        assertNotNull(lan);
        assertEquals(lan.getId(), lanId);
    }

    @Test
    public void updateLan() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Lan updatedLan = profitbricksApi.getLan().updateLan(dataCenterId, lanId, Boolean.TRUE);
        assertEquals(updatedLan.getProperties().isIsPublic(), true);
        waitTillProvisioned(updatedLan.getRequestId());

    }

    @Test
    public void createLanComposite() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RestClientException, IOException, InterruptedException {
        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST DC - Composite Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        Lan lan = new Lan();

        lan.getProperties().setName("SDK TEST Lan - Lan");
        lan.getProperties().setIsPublic(false);

        Lans lans = new Lans();
        List<Lan> lanList = new ArrayList<Lan>();
        lanList.add(lan);
        lans.setItems(lanList);
        datacenter.getEntities().setLans(lans);

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);

        waitTillProvisioned(newDatacenter.getRequestId());

        profitbricksApi.getDataCenter().deleteDataCenter(newDatacenter.getId());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
