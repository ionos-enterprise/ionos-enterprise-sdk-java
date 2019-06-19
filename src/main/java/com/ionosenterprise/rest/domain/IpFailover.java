package com.ionosenterprise.rest.domain;

public class IpFailover {
    private String ip;
    private String nicUuid;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNicUuid() {
        return nicUuid;
    }

    public void setNicUuid(String nicUuid) {
        this.nicUuid = nicUuid;
    }
}
