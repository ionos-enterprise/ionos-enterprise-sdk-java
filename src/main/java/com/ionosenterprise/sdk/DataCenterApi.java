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
import com.ionosenterprise.rest.domain.DataCenter;
import com.ionosenterprise.rest.domain.DataCenters;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class DataCenterApi extends AbstractLabelApi {

    public DataCenterApi(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return "datacenters";
    }

    /**
     * This will list all data centers you have under your account.
     *
     * @return DataCenter object with properties and metadata.
     */
    public DataCenters getAllDataCenters() throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withDepth().build(), Collections.EMPTY_MAP, DataCenters.class);
    }

     /**
      * Retrieve a single Data Center.
      *
      * @param  id The unique ID of the data center.
      * @return DataCenter object with properties and metadata.
      */
    public DataCenter getDataCenter(String id) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().appendPathSegment(id).withDepth().build(),
                Collections.EMPTY_MAP, DataCenter.class);
    }

     /**
      * Create a single Data Center, you can add child items to trigger a composite provision.
      *
      * @param  datacenter object has the following properties:
      * <br>
      * name= The name of the data center.
      * <br>
      * location= The physical location where the data center will be created.
      *          This will be where all of your servers live.
      * <br>
      * description= A description for the data center, e.g. staging, production.
      * @return DataCenter object with properties and metadata.
      */
    public DataCenter createDataCenter(DataCenter datacenter) throws RestClientException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        return client.create(getResourcePathBuilder().build(), datacenter, DataCenter.class, HttpStatus.SC_ACCEPTED);
    }
    
    /**
     * After retrieving a data center, either by getting it by id, or as a create response object, you can change it's
     * properties and call the update method.
     *
     * @param id  The unique ID of the data center.
     * @param  datacenter DataCenter.Properties has the following properties:
     * <br>
     * name= The name of the data center.
     * <br>
     * description= A description for the data center, e.g. staging, production.
     * @return DataCenter object with properties and metadata.   
     */
    public DataCenter updateDataCenter(String id, DataCenter.Properties datacenter) throws RestClientException,
            IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        return client.update(getResourcePathBuilder().appendPathSegment(id).build(),
                datacenter, DataCenter.class, HttpStatus.SC_ACCEPTED);
    }

     /**
      * This will remove all objects within the data center and remove the data center object itself.
      *
      * @param  id The unique ID of the data center.
      * @return a String representing the requestId
      */
    public String deleteDataCenter(String id) throws RestClientException, IOException {
        return client.delete(getResourcePathBuilder().appendPathSegment(id).build(), HttpStatus.SC_ACCEPTED);
    }
}
