package com.ionosenterprise.rest.domain;

import java.util.ArrayList;
import java.util.List;

public class Labels extends BaseResource {
    private List<Label> items =  new ArrayList<>();

    public List<Label> getItems() {
        return items;
    }

    public void setItems(List<Label> items) {
        this.items = items;
    }
}
