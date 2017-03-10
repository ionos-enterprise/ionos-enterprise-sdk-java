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
import com.profitbricks.rest.domain.Snapshot;
import com.profitbricks.rest.domain.Snapshots;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jasmin.gacic
 */
public class SnapshotApi extends ProfitbricksAPIBase {

   public SnapshotApi() {
      super("snapshots", "");
   }

   public Snapshots getAllSnapshots() throws RestClientException, IOException {
      return client.get(getUrlBase().concat(resource).concat(depth), null, Snapshots.class);
   }

   public Snapshot createSnapshot(String dataCenterId, String serverId, String name, String description) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      Map<String, String> params = new HashMap<String, String>();
      params.put("name", name);
      params.put("description", description);
      return client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("volumes").concat("/").concat(serverId).concat("/").concat("create-snapshot"), params, Snapshot.class, 202);
   }

   public void restoreSnapshot(String dataCenterId, String serverId, String snapshotId) throws RestClientException, IOException {
      Map<String, String> params = new HashMap<String, String>();
      params.put("snapshotId", snapshotId);
      client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("volumes").concat("/").concat(serverId).concat("/").concat("restore-snapshot"), params, 202);
   }

   public Snapshot getSnapshot(String snapshotId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(resource).concat("/").concat(snapshotId).concat(depth), null, Snapshot.class);
   }

   public Snapshot updateSnapshot(String dataCenterId, String snapshotId, PBObject snapshot) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.update(getUrlBase().concat(resource).concat("/").concat(snapshotId), snapshot, Snapshot.class, 202);
   }

   public void deleteSnapshot(String snapshotId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(resource).concat("/").concat(snapshotId), 202);
   }
}
