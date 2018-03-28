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
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Volumes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class Volume extends ProfitbricksAPIBase {

   public Volume() throws Exception {
      super("volumes", "datacenters");
   }

   /**
    * Retrieve a list of volumes within the datacenter.
    *
    * @param dataCenterId
    * @return
    * @throws RestClientException
    * @throws IOException
    */
   public Volumes getAllVolumes(String dataCenterId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource)
              .concat(depth), null, Volumes.class);
   }

   /**
    * Retrieve a list of volumes attached to the server
    *
    * @param dataCenterId
    * @param serverId
    * @return
    * @throws RestClientException
    * @throws IOException
    */
   public Volumes getAllVolumes(String dataCenterId, String serverId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat("servers").concat("/").concat(serverId).concat("/").concat(resource)
              .concat(depth), null, Volumes.class);
   }

   /**
    * Retrieves a specified volume.
    *
    * @param dataCenterId
    * @param volumeId
    * @return
    * @throws RestClientException
    * @throws IOException
    */
   public com.profitbricks.rest.domain.Volume getVolume(String dataCenterId, String volumeId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId)
              .concat(depth), null, com.profitbricks.rest.domain.Volume.class);
   }

   /**
    * Creates a volume within a given data center.
    *
    * @param dataCenterId
    * @param volume
    * @return
    * @throws RestClientException
    * @throws IOException
    */
   public com.profitbricks.rest.domain.Volume createVolume(String dataCenterId, com.profitbricks.rest.domain.Volume volume) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), volume, com.profitbricks.rest.domain.Volume.class, 202);
   }

   /**
    * Attaches a pre-existing storage volume to the server.
    *
    * @param dataCenterId
    * @param serverId
    * @param volumeId
    * @return
    * @throws RestClientException
    * @throws IOException
    */
   public com.profitbricks.rest.domain.Volume attachVolume(String dataCenterId, String serverId, String volumeId) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      PBObject object = new PBObject();
      object.setId(volumeId);
      return client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat("servers").concat("/").concat(serverId).concat("/").concat(resource), object, com.profitbricks.rest.domain.Volume.class, 202);
   }

   public com.profitbricks.rest.domain.Volume updateVolume(String dataCenterId, String volumeId, Object volume) throws RestClientException, IOException,NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.update(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId), volume, com.profitbricks.rest.domain.Volume.class, 202);
   }

   /**
    * Detaches a pre-existing storage volume to the server.
    *
    * @param dataCenterId
    * @param serverId
    * @param volumeId
    * @throws RestClientException
    * @throws IOException
    */
   public void detachVolume(String dataCenterId, String serverId, String volumeId) throws RestClientException, IOException {
      PBObject object = new PBObject();
      object.setId(volumeId);
      client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat("servers").concat("/").concat(serverId).concat("/").concat(resource).concat("/").concat(volumeId), 202);
   }

    /**
    * Deletes the specified volume. This will result in the volume being removed from your data center. Use this with caution.
    *
    * @param dataCenterId
    * @param volumeId
    * @return
    * @throws RestClientException
    * @throws IOException
    */
   public void deleteVolume(String dataCenterId, String volumeId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId), 202);
   }

}
