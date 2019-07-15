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
import com.ionosenterprise.rest.domain.Location;
import com.ionosenterprise.rest.domain.Locations;
import com.ionosenterprise.rest.test.resource.CommonResource;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author jasmin@stackpointcloud.com
 */
public class LocationTest extends BaseTest {

    @Test
    public void listLocations() throws RestClientException, IOException {
        Locations locations = ionosEnterpriseApi.getLocation().getAllLocations();
        assertNotNull(locations);
        assertTrue(locations.getItems().size() > 0);

        Location location = locations.getItems().get(0);

        Location loc = ionosEnterpriseApi.getLocation().getLocation(location.getId());
        assertNotNull(loc);
        assertEquals(loc.getId(), location.getId());
        assertEquals(loc.getProperties().getName(), location.getProperties().getName());
        assertEquals(loc.getProperties().getFeatures(), location.getProperties().getFeatures());
    }

    @Test
    public void getLocation() throws RestClientException, IOException {
        Location location = ionosEnterpriseApi.getLocation().getLocation("us/las");
        assertNotNull(location);
    }

    @Test
    public void getLocationFail() throws IOException {
        try {
            ionosEnterpriseApi.getLocation().getLocation(CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }
}
