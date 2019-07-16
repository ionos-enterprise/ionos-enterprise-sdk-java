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

import com.ionosenterprise.rest.client.RestClient;
import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Nics;
import com.ionosenterprise.rest.domain.PBObject;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class NicApi extends AbstractBaseApi {

    public NicApi(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return "datacenters/%s/servers/%s/nics";
    }

    /**
     * This will retrieve a list of NICs associated with the load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @return Nics object with a list of Nics datacenter.
     */
    public Nics getAllNics(String dataCenterId, String serverId) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withPathParams(dataCenterId, serverId).withDepth().build(),
                Collections.EMPTY_MAP, Nics.class);
    }

    /**
     * Retrieves the attributes of a given load balanced NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @param nicId The unique ID of the nic
     * @return Nic object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.Nic getNic(String dataCenterId, String serverId, String nicId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(dataCenterId, serverId).appendPathSegment(nicId)
                .withDepth().build(), Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.Nic.class);
    }

    /**
     * Adds a NIC to the target server.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the data center
     * @param nic object has the following properties:
     * <br>
     * <br>
     * name = The name of the NIC.
     * <br>
     * <br>
     * ips = IPs assigned to the NIC. This can be a collection.
     * <br>
     * <br>
     * dhcp = Set to FALSE if you wish to disable DHCP on the NIC. Default:
     * TRUE.
     * <br>
     * <br>
     * lan = The LAN ID the NIC will sit on. If the LAN ID does not exist it
     * will be created. exist.
     * <br>
     * <br>
     * nat = Indicates the private IP address has outbound access to the public
     * internet.
     * <br>
     * <br>
     * firewallActive = Once you add a firewall rule this will reflect a true
     * value.
     * <br>
     * <br>
     * @return Nic object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Nic createNic(String dataCenterId, String serverId,
                                                         com.ionosenterprise.rest.domain.Nic nic)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.create(getResourcePathBuilder().withPathParams(dataCenterId, serverId).build(), nic,
                com.ionosenterprise.rest.domain.Nic.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * You can update -- in full or partially -- various attributes on the NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the data center
     * @param nic object has the following properties:
     * <br>
     * <br>
     * name = The name of the NIC.
     * <br>
     * <br>
     * ips = IPs assigned to the NIC. This can be a collection.
     * <br>
     * <br>
     * dhcp = Set to FALSE if you wish to disable DHCP on the NIC. Default:
     * TRUE.
     * <br>
     * <br>
     * lan = The LAN ID the NIC will sit on. If the LAN ID does not exist it
     * will be created. exist.
     * <br>
     * <br>
     * nat = Indicates the private IP address has outbound access to the public
     * internet.
     * <br>
     * <br>
     * @return Nic object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Nic updateNic(String dataCenterId, String serverId, String nicId, Object nic)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.update(getResourcePathBuilder().withPathParams(dataCenterId, serverId).appendPathSegment(nicId)
                .build(), nic, com.ionosenterprise.rest.domain.Nic.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * Deletes the specified NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @param nicId The unique ID of the nic
     * @return a String representing the requestId
     */
    public String deleteNic(String dataCenterId, String serverId, String nicId) throws RestClientException, IOException {
        return client.delete(getResourcePathBuilder().withPathParams(dataCenterId, serverId).appendPathSegment(nicId)
                .build(), HttpStatus.SC_ACCEPTED);
    }

    /**
     * This will associate a NIC to a Load Balancer, enabling the NIC to
     * participate in load-balancing.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @param nicId The unique ID of the nic.
     */
    public com.ionosenterprise.rest.domain.Nic assignNicToLoadBalancer(String dataCenterId, String loadBalancerId,
                                                                       String nicId)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        PBObject payload = new PBObject();
        payload.setId(nicId);

        return client.create(getResourcePathBuilder("datacenters/%s/loadbalancers/%s/balancednics")
                .withPathParams(dataCenterId, loadBalancerId).build(), payload,
                com.ionosenterprise.rest.domain.Nic.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * Removes the association of a NIC with a load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @param nicId The unique ID of the nic.
     * @return a String representing the requestId
     */
    public String unassignNicFromLoadBalancer(String dataCenterId, String loadBalancerId, String nicId)
            throws RestClientException, IOException {

        return client.delete(getResourcePathBuilder("datacenters/%s/loadbalancers/%s/balancednics")
                .withPathParams(dataCenterId, loadBalancerId).appendPathSegment(nicId).build(), HttpStatus.SC_ACCEPTED);
    }

    /**
     * This will retrieve a list of NICs associated with the load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @return Nics object with list of balanced nics
     */
    public Nics getAllBalancedNics(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder("datacenters/%s/loadbalancers/%s/balancednics")
                .withPathParams(dataCenterId, loadBalancerId).withDepth().build(), Collections.EMPTY_MAP, Nics.class);
    }

    /**
     * Retrieves the attributes of a given load balanced NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @param nicId The unique ID of the nic.
     * @return Nic object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.Nic getBalancedNic(String dataCenterId, String loadBalancerId, String nicId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder("datacenters/%s/loadbalancers/%s/balancednics")
                        .withPathParams(dataCenterId, loadBalancerId).appendPathSegment(nicId).withDepth().build(),
                Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.Nic.class);
    }
}
