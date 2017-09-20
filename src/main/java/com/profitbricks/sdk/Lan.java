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
import com.profitbricks.rest.domain.Lans;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class Lan extends ProfitbricksAPIBase {

    public Lan() throws Exception {
        super("lans", "datacenters");
    }

    /**
     * Retrieve a list of LANs within the data center.
     *
     * @param dataCenterId The unique ID of the data center
     * @return Lans object with a list of Lans datacenter.
     */
    public Lans getAllLans(String dataCenterId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat(depth), null, Lans.class);
    }

    /**
     * Retrieves the attributes of a given LAN.
     *
     * @param dataCenterId The unique ID of the data center
     * @param lanId The unique ID of the nic
     * @return Lan object with properties and metadata
     */
    public com.profitbricks.rest.domain.Lan getLan(String dataCenterId, String lanId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId).concat(depth), null, com.profitbricks.rest.domain.Lan.class);
    }

    /**
     * Creates a LAN within a data center.
     *
     * @param dataCenterId The unique ID of the data center
     * @param lan object has the following properties:
     * <br>
     * <br>
     * name = The name of your LAN.
     * <br>
     * <br>
     * isPublic = Boolean indicating if the LAN faces the public Internet or not.
     * <br>
     * <br>
     * nics = A collection of NICs associated with the LAN.
     * <br>
     * <br>
     * @return Lan object with properties and metadata.
     */
    public com.profitbricks.rest.domain.Lan createLan(String dataCenterId, com.profitbricks.rest.domain.Lan lan) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource), lan, com.profitbricks.rest.domain.Lan.class, 202);
    }

    /**
     * Perform updates to attributes of a LAN.
     *
     * @param dataCenterId The unique ID of the data center.
     * @param lanId The unique ID of the data center.
     * @param isPublic Boolean indicating if the LAN faces the public Internet
     * or not.
     * @return Lan object with properties and metadata.
     */
    public com.profitbricks.rest.domain.Lan updateLan(String dataCenterId, String lanId, Boolean isPublic) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.profitbricks.rest.domain.Lan.Properties pbObject = new com.profitbricks.rest.domain.Lan().new Properties();
        pbObject.setIsPublic(isPublic);
        return client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId), pbObject, com.profitbricks.rest.domain.Lan.class, 202);
    }

    /**
     * Perform updates to attributes of a LAN.
     *
     * @param dataCenterId The unique ID of the data center.
     * @param lanId The unique ID of the data center.
     * @param name The name of the LAN
     * @param isPublic Boolean indicating if the LAN faces the public Internet
     * or not.
     * @return Lan object with properties and metadata.
     */
    public com.profitbricks.rest.domain.Lan updateLan(String dataCenterId, String lanId,String name, Boolean isPublic) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.profitbricks.rest.domain.Lan.Properties pbObject = new com.profitbricks.rest.domain.Lan().new Properties();
        pbObject.setIsPublic(isPublic);
        pbObject.setName(name);
        return client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId), pbObject, com.profitbricks.rest.domain.Lan.class, 202);
    }

    /**
     * Perform updates to attributes of a LAN.
     *
     * @param dataCenterId The unique ID of the data center.
     * @param lanId The unique ID of the data center.
     * @param isPublic Boolean indicating if the LAN faces the public Internet
     * or not.
     * @param ipFailover Attributes related to IP failover groups.
     * @return Lan object with properties and metadata.
     */
    public com.profitbricks.rest.domain.Lan updateLan(String dataCenterId, String lanId, Boolean isPublic, List<com.profitbricks.rest.domain.Lan.IpFailover> ipFailover) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.profitbricks.rest.domain.Lan.Properties pbObject = new com.profitbricks.rest.domain.Lan().new Properties();
        pbObject.setIsPublic(isPublic);
        pbObject.setIpFailover(ipFailover);
        return client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId), pbObject, com.profitbricks.rest.domain.Lan.class, 202);
    }

    /**
     * Retrieve a list of LANs within the data center.
     *
     * @param dataCenterId The unique ID of the data center
     * @param lanId The unique ID of the Lan
     */
    public void deleteLan(String dataCenterId, String lanId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId).concat("/").concat(resource).concat("/").concat(lanId), 202);
    }
}
