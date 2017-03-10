package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Locations;
import com.profitbricks.sdk.ProfitbricksApi;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author jasmin.gacic
 * */
public class LocationTest {

    private static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    private static Location location;

    @Before
    public void setup() {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

    }

    @Test
    public void listLocations() throws RestClientException, IOException {
        Locations locations = profitbricksApi.getLocationApi().getAllLocations();
        assertNotNull(locations);
        assertTrue(locations.getItems().size() > 0);

        location = locations.getItems().get(0);

        Location loc = profitbricksApi.getLocationApi().getLocation(location.getId());
        assertNotNull(loc);
        assertEquals(loc.getId(), location.getId());
        assertEquals(loc.getProperties().getName(), location.getProperties().getName());
        assertEquals(loc.getProperties().getFeatures(), location.getProperties().getFeatures());
    }
}
