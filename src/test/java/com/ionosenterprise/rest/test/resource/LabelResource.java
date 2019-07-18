package com.ionosenterprise.rest.test.resource;

import com.ionosenterprise.rest.domain.Label;

public class LabelResource {

    private static Label label;
    private static Label labelEdit;

    public static Label getLabel() {
        if (label == null){
            label = new Label();
            label.getProperties().setKey("environment");
            label.getProperties().setValue("production");
        }
        return label;
    }

    public static Label getLabelEdit() {
        if (labelEdit == null) {
            labelEdit = new Label();
            labelEdit.getProperties().setValue("qa");
        }
        return labelEdit;
    }
}
