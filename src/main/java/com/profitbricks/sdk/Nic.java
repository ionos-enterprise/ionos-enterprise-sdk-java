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

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.Nics;
import com.profitbricks.rest.domain.PBObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author jasmin@stackpointcloud.com
 */
public class Nic extends ProfitbricksAPIBase {

    public Nic() throws Exception {
        super("nics", "servers");
    }

    /**
     * This will retrieve a list of NICs associated with the load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @return Nics object with a list of Nics datacenter.
     */
    public Nics getAllNics(String dataCenterId, String serverId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
                .concat(parentResource).concat("/").concat(serverId).concat("/")
                .concat(resource).concat(depth), null, Nics.class);
    }

    /**
     * Retrieves the attributes of a given load balanced NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @param nicId The unique ID of the nic
     * @return Nic object with properties and metadata
     */
    public com.profitbricks.rest.domain.Nic getNic(String dataCenterId, String serverId, String nicId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
                .concat(parentResource).concat("/").concat(serverId).concat("/")
                .concat(resource).concat("/").concat(nicId).concat(depth), null, com.profitbricks.rest.domain.Nic.class);
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
    public com.profitbricks.rest.domain.Nic createNic(String dataCenterId, String serverId, com.profitbricks.rest.domain.Nic nic) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
                .concat(parentResource).concat("/").concat(serverId).concat("/")
                .concat(resource), nic, com.profitbricks.rest.domain.Nic.class, 202);
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
    public com.profitbricks.rest.domain.Nic updateNic(String dataCenterId, String serverId, String nicId, Object nic) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.update(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
                .concat(parentResource).concat("/").concat(serverId).concat("/")
                .concat(resource).concat("/").concat(nicId), nic, com.profitbricks.rest.domain.Nic.class, 202);
    }

    /**
     * Deletes the specified NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @param nicId The unique ID of the nic
     */
    public void deleteNic(String dataCenterId, String serverId, String nicId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
                .concat(parentResource).concat("/").concat(serverId).concat("/")
                .concat(resource).concat("/").concat(nicId), 202);
    }

    /**
     * This will associate a NIC to a Load Balancer, enabling the NIC to
     * participate in load-balancing.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @param nicId The unique ID of the nic.
     */
    public com.profitbricks.rest.domain.Nic assignNicToLoadBalancer(String dataCenterId, String loadBalancerId, String nicId) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        PBObject payload = new PBObject();
        payload.setId(nicId);

        return client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
                concat("balancednics"), payload, com.profitbricks.rest.domain.Nic.class, 202);
    }

    /**
     * Removes the association of a NIC with a load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @param nicId The unique ID of the nic.
     */
    public void unassignNicFromLoadBalancer(String dataCenterId, String loadBalancerId, String nicId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
                concat("balancednics").concat("/").concat(nicId), 202);
    }

    /**
     * This will retrieve a list of NICs associated with the load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @return Nics object with list of balanced nics
     */
    public Nics getAllBalancedNics(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
                concat("balancednics").concat(depth), null, Nics.class);
    }

    /**
     * Retrieves the attributes of a given load balanced NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the load balancer.
     * @param serverId The unique ID of the server.
     * @param nicId The unique ID of the nic.
     * @return Nic object with properties and metadata
     */
    public com.profitbricks.rest.domain.Nic getBalancedNic(String dataCenterId, String loadBalancerId, String serverId, String nicId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("loadbalancers").concat("/").concat(loadBalancerId).concat("/").
                concat("balancednics").concat("/").concat(nicId).concat(depth), null, com.profitbricks.rest.domain.Nic.class);
    }
}
