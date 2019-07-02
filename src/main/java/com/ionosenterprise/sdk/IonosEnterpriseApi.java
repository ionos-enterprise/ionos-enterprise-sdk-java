/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.ionosenterprise.sdk;

import org.apache.commons.codec.binary.Base64;

public class IonosEnterpriseApi {

    private String credentials;

    public IonosEnterpriseApi() throws Exception {
        this.dataCenter = new Datacenter();
        this.server = new Server();
        this.volume = new Volume();
        this.snapshot = new Snapshot();
        this.loadbalancer = new Loadbalancer();
        this.nic = new Nic();
        this.firewallRule = new FirewallRule();
        this.image = new Image();
        this.ipBlock = new IPBlock();
        this.request = new Request();
        this.lan = new Lan();
        this.location = new Location();
        this.contract = new Contract();
        this.group = new Group();
        this.share = new Share();
        this.user = new User();
        this.resource = new Resource();
        this.s3Key = new S3Key();
    }

    private Datacenter dataCenter;
    private Server server;
    private Volume volume;
    private Snapshot snapshot;
    private Loadbalancer loadbalancer;
    private Nic nic;
    private FirewallRule firewallRule;
    private Image image;
    private IPBlock ipBlock;
    private Request request;
    private Lan lan;
    private Location location;
    private Contract contract;
    private Group group;
    private Share share;
    private User user;
    private Resource resource;
    private S3Key s3Key;

    /**
     * @return the dataCenter
     */
    public Datacenter getDataCenter() {
        this.dataCenter.setCredentials(credentials);
        return dataCenter;
    }

    /**
     * @return the server
     */
    public Server getServer() {
        this.server.setCredentials(credentials);
        return server;
    }

    /**
     * @return the volume
     */
    public Volume getVolume() {
        this.volume.setCredentials(credentials);
        return volume;
    }

    /**
     * @return the snapshot
     */
    public Snapshot getSnapshot() {
        this.snapshot.setCredentials(credentials);
        return snapshot;
    }

    /**
     * @return the loadbalancer
     */
    public Loadbalancer getLoadbalancer() {
        this.loadbalancer.setCredentials(credentials);
        return loadbalancer;
    }

    /**
     * @return the nic
     */
    public Nic getNic() {
        this.nic.setCredentials(credentials);
        return nic;
    }

    /**
     * @return the firewallRule
     */
    public FirewallRule getFirewallRule() {
        this.firewallRule.setCredentials(credentials);
        return firewallRule;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        this.image.setCredentials(credentials);
        return image;
    }

    /**
     * @return the ipBlock
     */
    public IPBlock getIpBlock() {
        this.ipBlock.setCredentials(credentials);
        return ipBlock;
    }

    /**
     * @return the request
     */
    public Request getRequest() {
        this.request.setCredentials(credentials);
        return request;
    }

    /**
     * @return the lan
     */
    public Lan getLan() {
        this.lan.setCredentials(credentials);
        return lan;
    }

    /**
     * @param username the username to be set
     * @param password the password to be set
     */
    public void setCredentials(String username, String password) {
        byte[] bytesEncoded = Base64.encodeBase64((username + ":" + password).getBytes());

        this.credentials = new String(bytesEncoded);
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        this.location.setCredentials(credentials);
        return location;
    }

    /**
     * @return the contract
     */
    public Contract getContract() {
        this.contract.setCredentials(credentials);
        return contract;
    }

    /**
     * @return the group
     */
    public Group getGroup() {
        this.group.setCredentials(credentials);
        return group;
    }

    /**
     * @return the resource
     */
    public Resource getResource() {
        this.resource.setCredentials(credentials);
        return resource;
    }

    /**
     * @return the user
     */
    public User getUser() {
        this.user.setCredentials(credentials);
        return user;
    }

    /**
     * @return the share
     */
    public Share getShare() {
        this.share.setCredentials(credentials);
        return share;
    }

    /**
     * @return the s3Key
     */
    public S3Key getS3Key() {
        return s3Key;
    }
}
