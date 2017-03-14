package com.profitbricks.rest.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasmingacic on 13/03/2017.
 */
public class Locations extends ProfitbricksBase {

    private List<Location> items = new ArrayList<Location>();

    /**
     * @return the items
     */
    public List<Location> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Location> items) {
        this.items = items;
    }

}