/*
 * Copyright (c) 2017, ProfitBricks GmbH
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
 * 4. Neither the name of the ProfitBricks nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ProfitBricks GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ProfitBricks GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.profitbricks.sdk;

import org.apache.commons.codec.binary.Base64;

public class ProfitbricksApi {

    private String credentials;

    public ProfitbricksApi() throws Exception {
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

    /**
     * @return the dataCenter
     */
    public Datacenter getDataCenter() {
        this.dataCenter.setCredentials(credentials);
        return dataCenter;
    }

    /**
     * @param dataCenter the dataCenter to set
     */
    public void setDataCenter(Datacenter dataCenter) {
        this.dataCenter = dataCenter;
    }

    /**
     * @return the server
     */
    public Server getServer() {
        this.server.setCredentials(credentials);
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * @return the volume
     */
    public Volume getVolume() {
        this.volume.setCredentials(credentials);
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    /**
     * @return the snapshot
     */
    public Snapshot getSnapshot() {
        this.snapshot.setCredentials(credentials);
        return snapshot;
    }

    /**
     * @param snapshot the snapshot to set
     */
    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * @return the loadbalancer
     */
    public Loadbalancer getLoadbalancer() {
        this.loadbalancer.setCredentials(credentials);
        return loadbalancer;
    }

    /**
     * @param loadbalancer the loadbalancer to set
     */
    public void setLoadbalancer(Loadbalancer loadbalancer) {
        this.loadbalancer = loadbalancer;
    }

    /**
     * @return the nic
     */
    public Nic getNic() {
        this.nic.setCredentials(credentials);
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(Nic nic) {
        this.nic = nic;
    }

    /**
     * @return the firewallRule
     */
    public FirewallRule getFirewallRule() {
        this.firewallRule.setCredentials(credentials);
        return firewallRule;
    }

    /**
     * @param firewallRule the firewallRule to set
     */
    public void setFirewallRule(FirewallRule firewallRule) {
        this.firewallRule = firewallRule;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        this.image.setCredentials(credentials);
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return the ipBlock
     */
    public IPBlock getIpBlock() {
        this.ipBlock.setCredentials(credentials);
        return ipBlock;
    }

    /**
     * @param ipBlock the ipBlock to set
     */
    public void setIpBlock(IPBlock ipBlock) {
        this.ipBlock = ipBlock;
    }

    /**
     * @return the request
     */
    public Request getRequest() {
        this.request.setCredentials(credentials);
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * @return the lan
     */
    public Lan getLan() {
        this.lan.setCredentials(credentials);
        return lan;
    }

    /**
     * @param lan the lan to set
     */
    public void setLan(Lan lan) {
        this.lan = lan;
    }

    /**
     * @param credentials the credentials to set
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public void setCredentials(String username, String password) {
        byte[] bytesEncoded = Base64.encodeBase64((username + ":" + password).getBytes());

        this.credentials = new String(bytesEncoded);
    }

    public Location getLocation() {
        this.location.setCredentials(credentials);
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public void setGroup(Group group) {
        this.group.setCredentials(credentials);
        this.group = group;
    }

    public Resource getResource() {
        this.resource.setCredentials(credentials);
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource.setCredentials(credentials);
        this.resource = resource;
    }

    public User getUser() {
        this.user.setCredentials(credentials);
        return user;
    }

    public void setUser(User user) {
        this.user.setCredentials(credentials);
        this.user = user;
    }

    public Share getShare() {
        this.share.setCredentials(credentials);
        return share;
    }

    public void setShare(Share share) {
        this.share.setCredentials(credentials);
        this.share = share;
    }
}
