package com.ionosenterprise.rest.domain;

import java.util.ArrayList;
import java.util.List;

public class BackupUnits extends BaseResource {
    private List<User> items =  new ArrayList<User>();

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}
