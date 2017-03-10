package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.Locations;

import java.io.IOException;

/**
 * @author jasmin.gacic
 * */
public class LocationApi extends ProfitbricksAPIBase {

    public LocationApi() {
        super("locations", "");
    }

    public Locations getAllLocations() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat(depth), null, Locations.class);
    }

    public Location getLocation(String id) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(id).concat(depth), null, Location.class);
    }
}
