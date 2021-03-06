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
import com.ionosenterprise.rest.domain.IPBlocks;
import com.ionosenterprise.util.Constant;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class IPBlockApi extends AbstractLabelApi {

   public IPBlockApi(RestClient client) {
      super(client);
   }

   protected String getPathFormat() {
      return Constant.IPBLOCKS_RESOURCE_PATH_TEMPLATE;
   }

   /**
     * Retrieve a list of IP Blocks.
     *
     * @return IPBlocks object with a list of IPBlocks in datacenter.
     */
   public IPBlocks getAllIPBlocks() throws RestClientException, IOException {
      return client.get(getResourcePathBuilder().withDepth().build(), Collections.EMPTY_MAP, IPBlocks.class);
   }

    /**
     * Retrieves the attributes of a specific IP Block.
     *
     * @param ipBlockId The unique ID of the IP block.
     * @return IPBlock object with properties and metadata.
     */
   public com.ionosenterprise.rest.domain.IPBlock getIPBlock(String ipBlockId) throws RestClientException, IOException {
      return client.get(getResourcePathBuilder().appendPathSegment(ipBlockId).withDepth().build(),
              Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.IPBlock.class);
   }

   /**
     * Creates an IP block.
     *
     * @param ipBlock object has the following properties:
     * <br>
     * <br>
     * name = A descriptive name for the IP block
     * <br>
     * <br>
     * location = This must be one of the locations: us/las, de/fra, de/fkb.
     * <br>
     * <br>
     * size = The size of the IP block you want.
     * <br>
     * <br>
     * @return IPBlock object with properties and metadata.
     */
   public com.ionosenterprise.rest.domain.IPBlock createIPBlock(com.ionosenterprise.rest.domain.IPBlock ipBlock)
           throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
           IllegalArgumentException, InvocationTargetException {

      return client.create(getResourcePathBuilder().build(), ipBlock,
              com.ionosenterprise.rest.domain.IPBlock.class, HttpStatus.SC_ACCEPTED);
   }

    /**
     * Deletes the specified IP Block.
     *
     * @param ipBlockId The unique ID of the IP block.
     * @return a String representing the requestId
     */
   public String deleteIPBlock(String ipBlockId) throws RestClientException, IOException {
      return client.delete(getResourcePathBuilder().appendPathSegment(ipBlockId).build(), HttpStatus.SC_ACCEPTED);
   }

   /**
    * Update the name of an IP block.
    *
    * @param ipBlockProperties object has the following properties:
    * <br>
    * <br>
    * name = A descriptive name for the IP block
    * <br>
    * <br>
    * @return IPBlock object with properties and metadata.
    */
   public com.ionosenterprise.rest.domain.IPBlock updateIPBlock(String ipBlockId,
                                                                com.ionosenterprise.rest.domain.IPBlock.Properties ipBlockProperties)
           throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
           IllegalArgumentException, InvocationTargetException {
      return client.update(getResourcePathBuilder().appendPathSegment(ipBlockId).build(), ipBlockProperties,
              com.ionosenterprise.rest.domain.IPBlock.class, 202);
   }
}
