package com.ionosenterprise.rest.test.resource;

import com.ionosenterprise.rest.domain.Share;

public class ShareResource {
    private static Share share;
    private static Share editShare;

    public static Share getShare() {
        if (share == null) {
            share = new Share();
            share.getProperties().setEditPrivilege(true);
            share.getProperties().setSharePrivilege(true);
        }

        return share;
    }

    public static Share getEditShare() {
        if (editShare == null) {
            editShare = new Share();
            editShare.getProperties().setEditPrivilege(false);
        }

        return editShare;
    }
}
