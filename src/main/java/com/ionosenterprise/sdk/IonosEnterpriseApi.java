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
        this.dataCenterApi = new DataCenterApi();
        this.serverApi = new ServerApi();
        this.volumeApi = new VolumeApi();
        this.snapshotApi = new SnapshotApi();
        this.loadbalancerApi = new LoadbalancerApi();
        this.nicApi = new NicApi();
        this.firewallRuleApi = new FirewallRuleApi();
        this.imageApi = new ImageApi();
        this.ipBlockApi = new IPBlockApi();
        this.requestApi = new RequestApi();
        this.lanApi = new LanApi();
        this.locationApi = new LocationApi();
        this.contractApi = new ContractApi();
        this.groupApi = new GroupApi();
        this.shareApi = new ShareApi();
        this.userApi = new UserApi();
        this.resourceApi = new ResourceApi();
    }

    private DataCenterApi dataCenterApi;
    private ServerApi serverApi;
    private VolumeApi volumeApi;
    private SnapshotApi snapshotApi;
    private LoadbalancerApi loadbalancerApi;
    private NicApi nicApi;
    private FirewallRuleApi firewallRuleApi;
    private ImageApi imageApi;
    private IPBlockApi ipBlockApi;
    private RequestApi requestApi;
    private LanApi lanApi;
    private LocationApi locationApi;
    private ContractApi contractApi;
    private GroupApi groupApi;
    private ShareApi shareApi;
    private UserApi userApi;
    private ResourceApi resourceApi;

    /**
     * @return the dataCenterApi
     */
    public DataCenterApi getDataCenterApi() {
        this.dataCenterApi.setCredentials(credentials);
        return dataCenterApi;
    }

    /**
     * @param dataCenterApi the dataCenterApi to set
     */
    public void setDataCenterApi(DataCenterApi dataCenterApi) {
        this.dataCenterApi = dataCenterApi;
    }

    /**
     * @return the serverApi
     */
    public ServerApi getServerApi() {
        this.serverApi.setCredentials(credentials);
        return serverApi;
    }

    /**
     * @param serverApi the serverApi to set
     */
    public void setServerApi(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    /**
     * @return the volumeApi
     */
    public VolumeApi getVolumeApi() {
        this.volumeApi.setCredentials(credentials);
        return volumeApi;
    }

    /**
     * @param volumeApi the volumeApi to set
     */
    public void setVolumeApi(VolumeApi volumeApi) {
        this.volumeApi = volumeApi;
    }

    /**
     * @return the snapshotApi
     */
    public SnapshotApi getSnapshotApi() {
        this.snapshotApi.setCredentials(credentials);
        return snapshotApi;
    }

    /**
     * @param snapshotApi the snapshotApi to set
     */
    public void setSnapshotApi(SnapshotApi snapshotApi) {
        this.snapshotApi = snapshotApi;
    }

    /**
     * @return the loadbalancerApi
     */
    public LoadbalancerApi getLoadbalancerApi() {
        this.loadbalancerApi.setCredentials(credentials);
        return loadbalancerApi;
    }

    /**
     * @param loadbalancerApi the loadbalancerApi to set
     */
    public void setLoadbalancerApi(LoadbalancerApi loadbalancerApi) {
        this.loadbalancerApi = loadbalancerApi;
    }

    /**
     * @return the nicApi
     */
    public NicApi getNicApi() {
        this.nicApi.setCredentials(credentials);
        return nicApi;
    }

    /**
     * @param nicApi the nicApi to set
     */
    public void setNicApi(NicApi nicApi) {
        this.nicApi = nicApi;
    }

    /**
     * @return the firewallRuleApi
     */
    public FirewallRuleApi getFirewallRuleApi() {
        this.firewallRuleApi.setCredentials(credentials);
        return firewallRuleApi;
    }

    /**
     * @param firewallRuleApi the firewallRuleApi to set
     */
    public void setFirewallRuleApi(FirewallRuleApi firewallRuleApi) {
        this.firewallRuleApi = firewallRuleApi;
    }

    /**
     * @return the image
     */
    public ImageApi getImageApi() {
        this.imageApi.setCredentials(credentials);
        return imageApi;
    }

    /**
     * @param imageApi the imageApi to set
     */
    public void setImageApi(ImageApi imageApi) {
        this.imageApi = imageApi;
    }

    /**
     * @return the ipBlockApi
     */
    public IPBlockApi getIpBlockApi() {
        this.ipBlockApi.setCredentials(credentials);
        return ipBlockApi;
    }

    /**
     * @param ipBlockApi the ipBlockApi to set
     */
    public void setIpBlockApi(IPBlockApi ipBlockApi) {
        this.ipBlockApi = ipBlockApi;
    }

    /**
     * @return the requestApi
     */
    public RequestApi getRequestApi() {
        this.requestApi.setCredentials(credentials);
        return requestApi;
    }

    /**
     * @param requestApi the requestApi to set
     */
    public void setRequestApi(RequestApi requestApi) {
        this.requestApi = requestApi;
    }

    /**
     * @return the lanApi
     */
    public LanApi getLanApi() {
        this.lanApi.setCredentials(credentials);
        return lanApi;
    }

    /**
     * @param lanApi the lanApi to set
     */
    public void setLanApi(LanApi lanApi) {
        this.lanApi = lanApi;
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

    public LocationApi getLocationApi() {
        this.locationApi.setCredentials(credentials);
        return locationApi;
    }

    public void setLocationApi(LocationApi locationApi) {
        this.locationApi = locationApi;
    }

    /**
     * @return the contractApi
     */
    public ContractApi getContractApi() {
        this.contractApi.setCredentials(credentials);
        return contractApi;
    }

    /**
     * @return the groupApi
     */
    public GroupApi getGroupApi() {
        this.groupApi.setCredentials(credentials);
        return groupApi;
    }

    public void setGroupApi(GroupApi groupApi) {
        this.groupApi.setCredentials(credentials);
        this.groupApi = groupApi;
    }

    public ResourceApi getResourceApi() {
        this.resourceApi.setCredentials(credentials);
        return resourceApi;
    }

    public void setResourceApi(ResourceApi resourceApi) {
        this.resourceApi.setCredentials(credentials);
        this.resourceApi = resourceApi;
    }

    public UserApi getUserApi() {
        this.userApi.setCredentials(credentials);
        return userApi;
    }

    public void setUserApi(UserApi userApi) {
        this.userApi.setCredentials(credentials);
        this.userApi = userApi;
    }

    public ShareApi getShareApi() {
        this.shareApi.setCredentials(credentials);
        return shareApi;
    }

    public void setShareApi(ShareApi shareApi) {
        this.shareApi.setCredentials(credentials);
        this.shareApi = shareApi;
    }
}
