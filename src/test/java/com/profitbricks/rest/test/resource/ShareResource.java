package com.profitbricks.rest.test.resource;

import com.profitbricks.rest.domain.Share;

public class ShareResource {
    private static Share share;
    private static Share badShare;
    private static Share editShare;

    public static Share getShare() {
        if (share == null) {
            share = new Share();
            share.getProperties().setEditPrivilege(true);
            share.getProperties().setSharePrivilege(true);
        }

        return share;
    }

    public static Share getBadShare() {
        if (badShare == null) {
            badShare = new Share();
        }

        return badShare;
    }

    public static Share getEditShare() {
        if (editShare == null) {
            editShare = new Share();
            editShare.getProperties().setEditPrivilege(false);
        }

        return editShare;
    }
}
