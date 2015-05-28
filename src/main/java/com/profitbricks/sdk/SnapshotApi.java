 /*
 * Copyright (c) <year>, <copyright holder>
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
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
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
import com.profitbricks.rest.domain.Snapshot;
import com.profitbricks.rest.domain.Snapshots;
import java.io.IOException;
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
      return client.get(urlBase.concat(resource).concat(depth), null, Snapshots.class);
   }

   public Snapshot createSnapshot(String dataCenterId, String serverId, String name, String description) throws RestClientException, IOException {
      Map<String, String> params = new HashMap<String, String>();
      params.put("name", name);
      params.put("description", description);
      return client.create(urlBase.concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("volumes").concat("/").concat(serverId).concat("/").concat("create-snapshot"), params, Snapshot.class, 202);
   }

   public void restoreSnapshot(String dataCenterId, String serverId, String snapshotId) throws RestClientException, IOException {
      Map<String, String> params = new HashMap<String, String>();
      params.put("snapshotId", snapshotId);
      client.create(urlBase.concat("datacenters").concat("/").concat(dataCenterId).concat("/")
              .concat("volumes").concat("/").concat(serverId).concat("/").concat("restore-snapshot"), params, 202);
   }

   public Snapshot getSnapshot(String snapshotId) throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat("/").concat(snapshotId).concat(depth), null, Snapshot.class);
   }

   public Snapshot updateSnapshot(String dataCenterId, String snapshotId, PBObject snapshot) throws RestClientException, IOException {
      return client.update(urlBase.concat(resource).concat("/").concat(snapshotId), snapshot, Snapshot.class, 202);
   }

   public void deleteSnapshot(String snapshotId) throws RestClientException, IOException {
      client.delete(urlBase.concat(resource).concat("/").concat(snapshotId), 202);
   }
}
