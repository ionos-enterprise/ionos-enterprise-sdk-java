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
import com.ionosenterprise.rest.domain.Snapshots;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class Snapshot extends BaseAPI {

    public Snapshot() throws Exception {
        super("snapshots", "");
    }

    /**
     * You can retrieve a list of all snapshots.
     *
     * @return Snapshots object with a list of Snapshots
     */
    public Snapshots getAllSnapshots() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat(depth), null, Snapshots.class);
    }

    /**
     * Creates a snapshot of a volume within the data center. You can use a
     * snapshot to create a new storage volume or to restore a storage volume.
     *
     * @param dataCenterId The unique ID of the data center.
     * @param volumeId The unique ID of the volume.
     * @param name The name of the snapshot.
     * @param description The description of the snapshot.
     * @param licenseType The license type of the snapshot.
     * @return Snapshot object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Snapshot createSnapshot(String dataCenterId, String volumeId, String name,
                                                                   String description, String licenseType)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
                IllegalArgumentException, InvocationTargetException {

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("description", description);
        params.put("licenseType", licenseType);

        return client.create(
                getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/").concat("volumes")
                        .concat("/").concat(volumeId).concat("/").concat("create-snapshot"),
                params, com.ionosenterprise.rest.domain.Snapshot.class, 202);
    }

    /**
     * This will restore a snapshot onto a volume. A snapshot is created as just
     * another image that can be used to create new volumes or to restore an
     * existing volume.
     *
     * @param dataCenterId The unique ID of the data center.
     * @param volumeId The unique ID of the volume.
     * @param snapshotId The unique ID of the snapshot.
     */
    public void restoreSnapshot(String dataCenterId, String volumeId, String snapshotId) throws RestClientException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("snapshotId", snapshotId);
        client.create(getUrlBase().concat("datacenters").concat("/").concat(dataCenterId).concat("/")
                .concat("volumes").concat("/").concat(volumeId).concat("/").concat("restore-snapshot"), params, 202);
    }

    /**
     * Retrieves the attributes of a specific snapshot.
     *
     * @param snapshotId The unique ID of the snapshot
     * @return Snapshot object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.Snapshot getSnapshot(String snapshotId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(snapshotId).concat(depth), null, com.ionosenterprise.rest.domain.Snapshot.class);
    }

    /**
     * Perform updates to attributes of a snapshot.
     *
     * @param dataCenterId The unique ID of the data center
     * @param snapshotId The unique ID of the data center
     * @param snapshot object has the following properties:
     * <br>
     * <br>
     * name = The name of the snapshot.
     * <br>
     * <br>
     * description = The description of the snapshot.
     * <br>
     * <br>
     * cpuHotPlug = This volume is capable of CPU hot plug (no reboot required)
     * <br>
     * <br>
     * cpuHotUnplug = This volume is capable of CPU hot unplug (no reboot
     * required)
     * <br>
     * <br>
     * ramHotPlug = This volume is capable of memory hot plug (no reboot
     * required)
     * <br>
     * <br>
     * ramHotUnplug = This volume is capable of memory hot unplug (no reboot
     * required)
     * <br>
     * <br>
     * nicHotPlug = This volume is capable of NIC hot plug (no reboot required)
     * <br>
     * <br>
     * nicHotUnplug = This volume is capable of NIC hot unplug (no reboot
     * required)
     * <br>
     * <br>
     * discVirtioHotPlug = This volume is capable of Virt-IO drive hot plug (no
     * reboot required)
     * <br>
     * <br>
     * discVirtioHotUnplug = This volume is capable of Virt-IO drive hot unplug
     * (no reboot required)
     * <br>
     * <br>
     * discScsiHotPlug = This volume is capable of SCSI drive hot plug (no
     * reboot required)
     * <br>
     * <br>
     * discScsiHotUnplug = This volume is capable of SCSI drive hot unplug (no
     * reboot required)
     * <br>
     * <br>
     *  licenceType =  The snapshot's licence type: LINUX, WINDOWS, or UNKNOWN.
     * <br>
     * <br>
     * @return Snapshot object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Snapshot updateSnapshot(String dataCenterId, String snapshotId, com.ionosenterprise.rest.domain.Snapshot.Properties snapshot) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.update(getUrlBase().concat(resource).concat("/").concat(snapshotId), snapshot, com.ionosenterprise.rest.domain.Snapshot.class, 202);
    }

     /**
     * Deletes the specified snapshot.
     *
     * @param snapshotId The unique ID of the snapshot
     */
    public void deleteSnapshot(String snapshotId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(resource).concat("/").concat(snapshotId), 202);
    }
}
