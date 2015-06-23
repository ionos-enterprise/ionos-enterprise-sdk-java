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
import com.profitbricks.rest.domain.Helper;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.rest.domain.raw.VolumeRaw;
import com.profitbricks.rest.domain.raw.VolumesRaw;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
   public List<Volume> getAllVolumes(String dataCenterId) throws RestClientException, IOException {
      return Helper.convertVolumes(client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource)
              .concat(depth), null, VolumesRaw.class));
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
   public List<Volume> getAllVolumes(String dataCenterId, String serverId) throws RestClientException, IOException {
      return Helper.convertVolumes(client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat("servers").concat("/").concat(serverId).concat("/").concat(resource)
              .concat(depth), null, VolumesRaw.class));
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
      return Helper.convertVolume(client.get(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId)
              .concat(depth), null, VolumeRaw.class));
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
   public Volume createVolume(String dataCenterId, VolumeRaw volume) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return Helper.convertVolume(client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), volume, VolumeRaw.class, 202));
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
      return Helper.convertVolume(client.create(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat("servers").concat("/").concat(serverId).concat("/").concat(resource), object, VolumeRaw.class, 202));
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

   public void deleteVolume(String dataCenterId, String volumeId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId), 202);
   }

}
