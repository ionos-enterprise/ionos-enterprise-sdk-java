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
import com.ionosenterprise.util.Constant;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.logging.Logger;

public class IonosEnterpriseApi {

    private static final Logger LOGGER = Logger.getLogger(IonosEnterpriseApi.class.getName());

    private static final String BASIC_AUTHORIZATION_HEADER_PREFIX = "Basic ";

    private RestClient client;

    public IonosEnterpriseApi() {
        initRestClient();

        this.dataCenterApi = new DataCenterApi(client);
        this.serverApi = new ServerApi(client);
        this.volumeApi = new VolumeApi(client);
        this.snapshotApi = new SnapshotApi(client);
        this.loadbalancerApi = new LoadbalancerApi(client);
        this.nicApi = new NicApi(client);
        this.firewallRuleApi = new FirewallRuleApi(client);
        this.imageApi = new ImageApi(client);
        this.ipBlockApi = new IPBlockApi(client);
        this.requestApi = new RequestApi(client);
        this.lanApi = new LanApi(client);
        this.locationApi = new LocationApi(client);
        this.contractApi = new ContractApi(client);
        this.groupApi = new GroupApi(client);
        this.shareApi = new ShareApi(client);
        this.userApi = new UserApi(client);
        this.resourceApi = new ResourceApi(client);
        this.labelApi = new LabelApi(client);
        this.backupUnitApi = new BackupUnitApi(client);
        this.s3KeyApi = new S3KeyApi(client);
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
    private LabelApi labelApi;
    private BackupUnitApi backupUnitApi;
    private S3KeyApi s3KeyApi;

    /**
     * @return the dataCenterApi
     */
    public DataCenterApi getDataCenterApi() {
        return dataCenterApi;
    }

    /**
     * @return the serverApi
     */
    public ServerApi getServerApi() {
        return serverApi;
    }

    /**
     * @return the volumeApi
     */
    public VolumeApi getVolumeApi() {
        return volumeApi;
    }

    /**
     * @return the snapshotApi
     */
    public SnapshotApi getSnapshotApi() {
        return snapshotApi;
    }

    /**
     * @return the loadbalancerApi
     */
    public LoadbalancerApi getLoadbalancerApi() {
        return loadbalancerApi;
    }

    /**
     * @return the nicApi
     */
    public NicApi getNicApi() {
        return nicApi;
    }

    /**
     * @return the firewallRuleApi
     */
    public FirewallRuleApi getFirewallRuleApi() {
        return firewallRuleApi;
    }

    /**
     * @return the imageApi
     */
    public ImageApi getImageApi() {
        return imageApi;
    }

    /**
     * @return the ipBlockApi
     */
    public IPBlockApi getIpBlockApi() {
        return ipBlockApi;
    }

    /**
     * @return the requestApi
     */
    public RequestApi getRequestApi() {
        return requestApi;
    }

    /**
     * @return the lanApi
     */
    public LanApi getLanApi() {
        return lanApi;
    }

    /**
     * @return the locationApi
     */
    public LocationApi getLocationApi() {
        return locationApi;
    }

    /**
     * @return the contractApi
     */
    public ContractApi getContractApi() {
        return contractApi;
    }

    /**
     * @return the groupApi
     */
    public GroupApi getGroupApi() {
        return groupApi;
    }

    /**
     * @return the resourceApi
     */
    public ResourceApi getResourceApi() {
        return resourceApi;
    }

    /**
     * @return the userApi
     */
    public UserApi getUserApi() {
        return userApi;
    }

    /**
     * @return the shareApi
     */
    public ShareApi getShareApi() {
        return shareApi;
    }

    /**
     * @return the labelApi
     */
    public LabelApi getLabelApi() {
        return labelApi;
    }

    /**
     * @return the s3KeyApi
     */
    public S3KeyApi getS3KeyApi() {
        return s3KeyApi;
    }

    /**
     * @return the backupUnitApi
     */
    public BackupUnitApi getBackupUnitApi() {
        return backupUnitApi;
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
        LOGGER.info("!!!!!!! CREDENTIALS !!!!!!!!!!!" + credentials);
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
                    if (System.getenv(Constant.IONOS_ENTERPRISE_USERNAME) != null
                            && System.getenv(Constant.IONOS_ENTERPRISE_PASSWORD) != null) {
                        byte[] bytesEncoded = Base64.encodeBase64((System.getenv(Constant.IONOS_ENTERPRISE_USERNAME)
                                + ":" + System.getenv(Constant.IONOS_ENTERPRISE_PASSWORD)).getBytes());
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

                request.addHeader(HttpHeaders.AUTHORIZATION, BASIC_AUTHORIZATION_HEADER_PREFIX.concat(authorizationCredentials));
            }
        };
    }
}
