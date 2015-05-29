/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.rest.domain.Volumes;
import java.io.IOException;

/**
 *
 * @author jasmin.gacic
 */
public class VolumeApi extends ProfitbricksAPIBase {

   public VolumeApi() {
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
      return client.get(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
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
      return client.get(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
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
   public Volume getVolume(String dataCenterId, String volumeId) throws RestClientException, IOException {
      return client.get(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId)
              .concat(depth), null, Volume.class);
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
   public Volume createVolume(String dataCenterId, Volume volume) throws RestClientException, IOException {
      return client.create(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), volume, Volume.class, 202);
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
   public Volume attachVolume(String dataCenterId, String serverId, String volumeId) throws RestClientException, IOException {
      PBObject object = new PBObject();
      object.setId(volumeId);
      return client.create(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat("servers").concat("/").concat(serverId).concat("/").concat(resource), object, Volume.class, 202);
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
      client.delete(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat("servers").concat("/").concat(serverId).concat("/").concat(resource).concat("/").concat(volumeId), 202);
   }

   public void deleteVolume(String dataCenterId, String volumeId) throws RestClientException, IOException {
      client.delete(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId), 202);
   }

}
