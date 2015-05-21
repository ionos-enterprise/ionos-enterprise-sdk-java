/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
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

   public Volumes getAllVolumes(String dataCenterId) throws RestClientException, IOException {
      return client.get(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource)
              .concat(depth), null, Volumes.class);
   }

   public Volume getVolume(String dataCenterId, String volumeId) throws RestClientException, IOException {
      return client.get(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource).concat("/").concat(volumeId)
              .concat(depth), null, Volume.class);
   }

   public Volume createVolume(String dataCenterId, Volume volume) throws RestClientException, IOException {
      return client.create(urlBase.concat(parentResource).concat("/").concat(dataCenterId)
              .concat("/").concat(resource), volume, Volume.class, 202);
   }
}
