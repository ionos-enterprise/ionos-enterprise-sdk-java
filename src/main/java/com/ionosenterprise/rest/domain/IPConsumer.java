package com.ionosenterprise.rest.domain;

/**
 * The {@link IPConsumer} class represent the details for a consumer of an {@link IPBlock}.
 *
 * @author maxim.domentii@cloud.ionos.com
 */
public class IPConsumer {

    private String ip;
    private String mac;
    private String nicId;
    private String serverId;
    private String serverName;
    private String datacenterId;
    private String datacenterName;

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to be set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to be set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return the nicId
     */
    public String getNicId() {
        return nicId;
    }

    /**
     * @param nicId the nicId to be set
     */
    public void setNicId(String nicId) {
        this.nicId = nicId;
    }

    /**
     * @return the serverId
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * @param serverId the serverId to be set
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param serverName the serverName to be set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * @return the datacenterId
     */
    public String getDatacenterId() {
        return datacenterId;
    }

    /**
     * @param datacenterId the datacenterId to be set
     */
    public void setDatacenterId(String datacenterId) {
        this.datacenterId = datacenterId;
    }

    /**
     * @return the datacenterName
     */
    public String getDatacenterName() {
        return datacenterName;
    }

    /**
     * @param datacenterName the datacenterName to be set
     */
    public void setDatacenterName(String datacenterName) {
        this.datacenterName = datacenterName;
    }
}
