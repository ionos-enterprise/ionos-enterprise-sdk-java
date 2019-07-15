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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionosenterprise.rest.client.RequestInterceptor;
import com.ionosenterprise.rest.client.RestClient;
import com.ionosenterprise.rest.client.RestClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class IonosEnterpriseApi {

    private RestClient client;

    public IonosEnterpriseApi() {
        initRestClient();

        this.dataCenter = new Datacenter(client);
        this.server = new Server(client);
        this.volume = new Volume(client);
        this.snapshot = new Snapshot(client);
        this.loadbalancer = new Loadbalancer(client);
        this.nic = new Nic(client);
        this.firewallRule = new FirewallRule(client);
        this.image = new Image(client);
        this.ipBlock = new IPBlock(client);
        this.request = new Request(client);
        this.lan = new Lan(client);
        this.location = new Location(client);
        this.contract = new Contract(client);
        this.group = new Group(client);
        this.share = new Share(client);
        this.user = new User(client);
        this.resource = new Resource(client);
        this.label = new Label(client);
        this.backupUnit = new BackupUnit(client);
        this.s3Key = new S3Key(client);
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
    private Label label;
    private BackupUnit backupUnit;
    private S3Key s3Key;

    /**
     * @return the dataCenter
     */
    public Datacenter getDataCenter() {
        return dataCenter;
    }

    /**
     * @return the server
     */
    public Server getServer() {
        return server;
    }

    /**
     * @return the volume
     */
    public Volume getVolume() {
        return volume;
    }

    /**
     * @return the snapshot
     */
    public Snapshot getSnapshot() {
        return snapshot;
    }

    /**
     * @return the loadbalancer
     */
    public Loadbalancer getLoadbalancer() {
        return loadbalancer;
    }

    /**
     * @return the nic
     */
    public Nic getNic() {
        return nic;
    }

    /**
     * @return the firewallRule
     */
    public FirewallRule getFirewallRule() {
        return firewallRule;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return the ipBlock
     */
    public IPBlock getIpBlock() {
        return ipBlock;
    }

    /**
     * @return the request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * @return the lan
     */
    public Lan getLan() {
        return lan;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return the contract
     */
    public Contract getContract() {
        return contract;
    }

    /**
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * @return the resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the share
     */
    public Share getShare() {
        return share;
    }

    /**
     * @return the label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * @return the s3Key
     */
    public S3Key getS3Key() {
        return s3Key;
    }

    /**
     * @return the backupUnit
     */
    public BackupUnit getBackupUnit() {
        return backupUnit;
    }

    /**
     * Set the credentials for the rest client.
     *
     * @param username the username to be set
     * @param password the password to be set
     */
    public void setCredentials(String username, String password) {
        byte[] bytesEncoded = Base64.encodeBase64((username + ":" + password).getBytes());

        String credentials = new String(bytesEncoded);
        RequestInterceptor authorize = getAuthorizeRequestInterceptor(credentials);
        client.setHttpClientInterceptor(authorize);
    }

    private void initRestClient() {
        RequestInterceptor interceptor = getAuthorizeRequestInterceptor(null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
        RestClientUtil restClientUtil = new RestClientUtil(httpClient, interceptor, objectMapper);
        this.client = new RestClient(restClientUtil);
    }

    private RequestInterceptor getAuthorizeRequestInterceptor(final String credentials) {
        return new RequestInterceptor() {
            @Override
            public void intercept(HttpRequestBase request) {
                String authorizationCredentials = credentials;
                if (authorizationCredentials == null) {
                    if (System.getenv("IONOS_ENTERPRISE_USERNAME") != null
                            && System.getenv("IONOS_ENTERPRISE_PASSWORD") != null) {
                        byte[] bytesEncoded = Base64.encodeBase64((System.getenv("IONOS_ENTERPRISE_USERNAME")
                                + ":" + System.getenv("IONOS_ENTERPRISE_PASSWORD")).getBytes());
                        authorizationCredentials = new String(bytesEncoded);
                    }
                }

                if (authorizationCredentials == null) {
                    try {
                        throw new Exception("Credentials not set.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                request.addHeader("Authorization", "Basic ".concat(authorizationCredentials));
            }
        };
    }
}
