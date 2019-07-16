package com.ionosenterprise.rest.domain;

import java.util.ArrayList;
import java.util.List;

public class S3Keys extends BaseResource {

    private List<S3Key> items = new ArrayList<S3Key>();

    /**
     * @return the items
     */
    public List<S3Key> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<S3Key> items) {
        this.items = items;
    }
}
