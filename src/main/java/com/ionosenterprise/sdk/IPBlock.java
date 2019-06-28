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
import com.ionosenterprise.rest.domain.IPBlocks;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class IPBlock extends BaseAPI {

   public IPBlock() throws Exception {
      super("ipblocks", null);
   }

    /**
     * Retrieve a list of IP Blocks.
     *
     * @return IPBlocks object with a list of IPBlocks in datacenter.
     */
   public IPBlocks getAllIPBlocks() throws RestClientException, IOException {
      return client.get(getUrlBase().concat(resource).concat(depth), null, IPBlocks.class);
   }

    /**
     * Retrieves the attributes of a specific IP Block.
     *
     * @param ipBlockId The unique ID of the IP block.
     * @return IPBlock object with properties and metadata.
     */
   public com.ionosenterprise.rest.domain.IPBlock getIPBlock(String ipBlockId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(resource).concat("/").concat(ipBlockId).concat(depth), null,
              com.ionosenterprise.rest.domain.IPBlock.class);
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
      return client.create(getUrlBase().concat(resource), ipBlock,
              com.ionosenterprise.rest.domain.IPBlock.class, 202);
   }

    /**
     * Deletes the specified IP Block.
     *
     * @param ipBlockId The unique ID of the IP block.
     */
   public void deleteIPBlock(String ipBlockId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(resource).concat("/").concat(ipBlockId), 202);
   }

   /**
    * Update the name of an IP block.
    *
    * @param ipBlock object has the following properties:
    * <br>
    * <br>
    * name = A descriptive name for the IP block
    * <br>
    * <br>
    * @return IPBlock object with properties and metadata.
    */
   public com.ionosenterprise.rest.domain.IPBlock updateIPBlock(String ipBlockId,
                                                                com.ionosenterprise.rest.domain.IPBlock.Properties ipBlock)
           throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
           IllegalArgumentException, InvocationTargetException {
      return client.update(getUrlBase().concat(resource).concat("/").concat(ipBlockId), ipBlock,
              com.ionosenterprise.rest.domain.IPBlock.class, 202);
   }
}
