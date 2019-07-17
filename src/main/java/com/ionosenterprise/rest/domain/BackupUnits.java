package com.ionosenterprise.rest.domain;

import java.util.ArrayList;
import java.util.List;

public class BackupUnits extends BaseResource {
    private List<BackupUnit> items =  new ArrayList<>();

    public List<BackupUnit> getItems() {
        return items;
    }

    public void setItems(List<BackupUnit> items) {
        this.items = items;
    }
}
