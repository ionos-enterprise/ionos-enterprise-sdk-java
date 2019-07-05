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

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.LoadBalancer;
import com.ionosenterprise.rest.domain.LoadBalancers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class Loadbalancer extends AbstractBaseAPI {

    public Loadbalancer() {
        super("datacenters/%s/loadbalancers");
    }

    /**
     * Retrieve a list of load balancers within the data center.
     *
     * @param dataCenterId The unique ID of the data center
     * @return LoadBalancers object with a list of LoadBalancer in datacenter.
     */
    public LoadBalancers getAllLoadBalancers(String dataCenterId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat(getDepth()),
                null, LoadBalancers.class);
    }

    /**
     * Retrieves the attributes of a given load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the nic
     * @return LoadBalancer object with properties and metadata
     */
    public LoadBalancer getLoadBalancer(String dataCenterId, String loadBalancerId)
            throws RestClientException, IOException {

        return client.get(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat("/")
                .concat(loadBalancerId).concat(getDepth()), null, LoadBalancer.class);
    }

    /**
     * Creates a load balancer within the data center. LoadBalancers can be
     * used for public or private IP traffic.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancer object has the following properties:
     * <br>
     * <br>
     * name = The name of your loadBalancer.
     * <br>
     * <br>
     * ip = IPv4 address of the load balancer. All attached NICs will inherit
     * this IP.
     * <br>
     * <br>
     * dhcp = Indicates if the load balancer will reserve an IP using DHCP.
     * <br>
     * <br>
     * balancednics = List of NICs taking part in load-balancing. All balanced
     * nics inherit the IP of the load balancer.
     * <br>
     * <br>
     * @return LoadBalancer object with properties and metadata.
     */
    public LoadBalancer createLoadBalancer(String dataCenterId, LoadBalancer loadBalancer)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.create(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))),
                loadBalancer, LoadBalancer.class, 202);
    }

    /**
     * Perform updates to attributes of a load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the LoadBalancer
     * @param loadBalancer
     * <br>
     * <br>
     * name = The name of the load balancer.
     * <br>
     * <br>
     * ip = The IP of the load balancer.
     * <br>
     * <br>
     * dhcp = Indicates if the load balancer will reserve an IP using DHCP.
     * <br>
     * <br>
     * @return LoadBalancer object with properties and metadata.
     */
    public LoadBalancer updateLoadBalancer(String dataCenterId, String loadBalancerId,
                                           LoadBalancer.Properties loadBalancer)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.update(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat("/")
                        .concat(loadBalancerId), loadBalancer, LoadBalancer.class, 202);
    }

    /**
     * Deletes the specified load balancer.
     *
     * @param dataCenterId The unique ID of the data center
     * @param loadBalancerId The unique ID of the LoadBalancer
     */
    public void deleteLoadBalaner(String dataCenterId, String loadBalancerId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat("/")
                .concat(loadBalancerId), 202);
    }
}
