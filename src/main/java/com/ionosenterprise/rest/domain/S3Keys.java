package com.ionosenterprise.rest.domain;

import java.util.ArrayList;
import java.util.List;

public class S3Keys extends BaseResource {

    private List<Volume> items = new ArrayList<Volume>();

    /**
     * @return the items
     */
    public List<Volume> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Volume> items) {
        this.items = items;
    }
}
