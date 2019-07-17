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
import com.ionosenterprise.rest.domain.FirewallRules;
import com.ionosenterprise.util.Constant;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class FirewallRuleApi extends AbstractBaseApi {

    public FirewallRuleApi(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return Constant.FIREWALLRULES_RESOURCE_PATH_TEMPLATE;
    }

    /**
     * Retrieves a list of firewall rules associated with a particular NIC.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @param nicId The unique ID of the NIC
     * @return FirewallRules object with a list of FirewallRules in a
     * datacenter.
     */
    public FirewallRules getAllFirewallRules(String dataCenterId, String serverId, String nicId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(dataCenterId, serverId, nicId).withDepth().build(),
                Collections.EMPTY_MAP, FirewallRules.class);
    }

    /**
     * Retrieves the attributes of a given firewall rule.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @param nicId The unique ID of the NIC
     * @param firewallRuleId The unique ID of the NIC
     * @return FirewallRule object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.FirewallRule getFirewallRule(String dataCenterId, String serverId,
                                                                        String nicId, String firewallRuleId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(dataCenterId, serverId, nicId)
                .appendPathSegment(firewallRuleId).withDepth().build(),
                Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.FirewallRule.class);
    }

    /**
     * Adds a NIC to the target server.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the data center
     * @param nicId The unique ID of the NIC
     * @param firewallRule object has the following properties:
     * <br>
     * <br>
     * name = The name of the Firewall Rule.
     * <br>
     * <br>
     * protocol = The protocol for the rule: TCP, UDP, ICMP, ANY.
     * <br>
     * <br>
     * sourceMac = Only traffic originating from the respective MAC address is
     * allowed. Valid format: aa:bb:cc:dd:ee:ff. Value null allows all source
     * MAC address.
     * <br>
     * <br>
     * sourceIp = Only traffic originating from the respective IPv4 address is
     * allowed. Value null allows all source IPs.
     * <br>
     * <br>
     * targetIp = In case the target NIC has multiple IP addresses, only traffic
     * directed to the respective IP address of the NIC is allowed. Value null
     * allows all target IPs.
     * <br>
     * <br>
     * portRangeStart = Defines the start range of the allowed port (from 1 to
     * 65534) if protocol TCP or UDP is chosen. Leave portRangeStart and
     * portRangeEnd value null to allow all ports.
     * <br>
     * <br>
     * portRangeEnd = Defines the end range of the allowed port (from 1 to
     * 65534) if the protocol TCP or UDP is chosen. Leave portRangeStart and
     * portRangeEnd null to allow all ports.
     * <br>
     * <br>
     * .icmpType = Defines the allowed type (from 0 to 254) if the protocol ICMP
     * is chosen. Value null allows all types.
     * <br>
     * <br>
     * icmpCode = Defines the allowed code (from 0 to 254) if protocol ICMP is
     * chosen. Value null allows all codes.
     * <br>
     * <br>
     * @return FirewallRule object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.FirewallRule createFirewallRule(String dataCenterId, String serverId,
            String nicId, com.ionosenterprise.rest.domain.FirewallRule firewallRule)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.create(getResourcePathBuilder().withPathParams(dataCenterId, serverId, nicId).build(),
                firewallRule, com.ionosenterprise.rest.domain.FirewallRule.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * Removes the specific firewall rule.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the server
     * @param nicId The unique ID of the nic
     * @param firewallRuleId The unique ID of the firewallRule
     * @return a String representing the requestId
     */
    public String deleteFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId)
            throws RestClientException, IOException {

        return client.delete(getResourcePathBuilder().withPathParams(dataCenterId, serverId, nicId)
                .appendPathSegment(firewallRuleId).build(),HttpStatus.SC_ACCEPTED);
    }

    /**
     * Perform updates to attributes of a firewall rule.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId The unique ID of the data center
     * @param nicId The unique ID of the NIC
     * @param firewallRuleId The unique ID of the NIC
     * @param firewallRule object has the following properties:
     * <br>
     * <br>
     * name = The name of the Firewall Rule.
     * <br>
     * <br>
     * protocol = The protocol for the rule: TCP, UDP, ICMP, ANY.
     * <br>
     * <br>
     * sourceMac = Only traffic originating from the respective MAC address is
     * allowed. Valid format: aa:bb:cc:dd:ee:ff. Value null allows all source
     * MAC address.
     * <br>
     * <br>
     * sourceIp = Only traffic originating from the respective IPv4 address is
     * allowed. Value null allows all source IPs.
     * <br>
     * <br>
     * targetIp = In case the target NIC has multiple IP addresses, only traffic
     * directed to the respective IP address of the NIC is allowed. Value null
     * allows all target IPs.
     * <br>
     * <br>
     * portRangeStart = Defines the start range of the allowed port (from 1 to
     * 65534) if protocol TCP or UDP is chosen. Leave portRangeStart and
     * portRangeEnd value null to allow all ports.
     * <br>
     * <br>
     * portRangeEnd = Defines the end range of the allowed port (from 1 to
     * 65534) if the protocol TCP or UDP is chosen. Leave portRangeStart and
     * portRangeEnd null to allow all ports.
     * <br>
     * <br>
     * .icmpType = Defines the allowed type (from 0 to 254) if the protocol ICMP
     * is chosen. Value null allows all types.
     * <br>
     * <br>
     * icmpCode = Defines the allowed code (from 0 to 254) if protocol ICMP is
     * chosen. Value null allows all codes.
     * <br>
     * <br>
     * @return FirewallRule object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.FirewallRule updateFirewallRule(String dataCenterId, String serverId,
            String nicId, String firewallRuleId, com.ionosenterprise.rest.domain.FirewallRule.Properties firewallRule)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.update(getResourcePathBuilder().withPathParams(dataCenterId, serverId, nicId)
                        .appendPathSegment(firewallRuleId).build(), firewallRule,
                com.ionosenterprise.rest.domain.FirewallRule.class, HttpStatus.SC_ACCEPTED);
    }
}
