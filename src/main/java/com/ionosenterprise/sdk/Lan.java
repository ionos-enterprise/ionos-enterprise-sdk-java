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
import com.ionosenterprise.rest.domain.Lans;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class Lan extends AbstractBaseAPI {

    public Lan() {
        super("datacenters/%s/lans");
    }

    /**
     * Retrieve a list of LANs within the data center.
     *
     * @param dataCenterId The unique ID of the data center
     * @return Lans object with a list of DataCenter LANs .
     */
    public Lans getAllLans(String dataCenterId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat(getDepth()),
                null, Lans.class);
    }

    /**
     * Retrieves the attributes of a given LAN.
     *
     * @param dataCenterId The unique ID of the data center
     * @param lanId The unique ID of the LAN
     * @return Lan object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.Lan getLan(String dataCenterId, String lanId)
            throws RestClientException, IOException {

        return client.get(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat("/").concat(lanId)
                        .concat(getDepth()), null, com.ionosenterprise.rest.domain.Lan.class);
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
    public com.ionosenterprise.rest.domain.Lan createLan(String dataCenterId, com.ionosenterprise.rest.domain.Lan lan)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.create(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))), lan,
                com.ionosenterprise.rest.domain.Lan.class, 202);
    }

    /**
     * Perform updates to attributes of a LAN.
     *
     * @param dataCenterId The unique ID of the data center.
     * @param lanId The unique ID of the LAN
     * @param  lan lan properties object that has the following properties:
     * <br>
     * isPublic = Boolean indicating if the LAN faces the public Internet or not.
     * <br>
     * name = The name of the LAN.
     * <br>
     * ipFailover = Attributes related to IP failover groups.
     * @return Lan object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Lan updateLan(String dataCenterId, String lanId,
                                                         com.ionosenterprise.rest.domain.Lan.Properties lan)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.update(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat("/")
                        .concat(lanId), lan, com.ionosenterprise.rest.domain.Lan.class, 202);
    }

    /**
     * Deletion of a LAN within the data center.
     *
     * @param dataCenterId The unique ID of the data center
     * @param lanId The unique ID of the Lan
     * @return a String representing the requestId
     */
    public String deleteLan(String dataCenterId, String lanId) throws RestClientException, IOException {
        return client.delete(getUrlBase().concat(getResourcePath(Arrays.asList(dataCenterId))).concat("/")
                .concat(lanId), 202);
    }
}
