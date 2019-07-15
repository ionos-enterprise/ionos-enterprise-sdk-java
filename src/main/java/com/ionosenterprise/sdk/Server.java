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
import com.ionosenterprise.rest.domain.CDRom;
import com.ionosenterprise.rest.domain.CDRoms;
import com.ionosenterprise.rest.domain.PBObject;
import com.ionosenterprise.rest.domain.Servers;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

/**
 * @author jasmin@stackpointcloud.com
 */
public class Server extends AbstractLabelApi {

    public Server(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return "datacenters/%s/servers";
    }

    /**
     * You can retrieve a list of all servers within a data center
     *
     * @param dataCenterId The unique ID of the data center
     * @return Servers object with a list of servers in the specified datacenter.
     */
    public Servers getAllServers(String dataCenterId) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withPathParams(dataCenterId).withDepth().build(),
                Collections.EMPTY_MAP, Servers.class);
    }

    /**
     * You can retrieve a list of all servers within a data center
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     * @return Servers object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.Server getServer(String dataCenterId, String serverId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId).withDepth()
                        .build(),Collections.EMPTY_MAP, com.ionosenterprise.rest.domain.Server.class);
    }

    /**
     * Creates a server within an existing data center, You can configure
     * additional properties such as specifying a boot volume and connecting the
     * server to an existing LAN.
     *
     * @param dataCenterId The unique ID of the data center
     * @param server       object has the following properties:
     *                     <br>
     *                     <br>
     *                     name = The hostname of the server.
     *                     <br>
     *                     <br>
     *                     cores = The total number of cores for the server.
     *                     <br>
     *                     <br>
     *                     ram = The amount of memory for the server in MB, e.g. 2048. Size must be
     *                     specified in multiples of 256 MB with a minimum of 256 MB; however, if
     *                     you set ramHotPlug to TRUE then you must use a minimum of 1024 MB.
     *                     <br>
     *                     <br>
     *                     availabilityZone = The availability zone in which the server should
     *                     exist.
     *                     <br>
     *                     <br>
     *                     licenceType = Sets the OS type of the server. If undefined the OS type
     *                     will be inherited from the boot image or boot volume.
     *                     <br>
     *                     <br>
     *                     bootVolume = Reference to a Volume used for booting. If not �null� then
     *                     bootCdrom has to be �null�.
     *                     <br>
     *                     <br>
     *                     bootCdrom = Reference to a CD-ROM used for booting. If not 'null' then
     *                     bootVolume has to be 'null'.
     *                     <br>
     *                     <br>
     *                     cpuFamily = Sets the CPU type. "AMD_OPTERON" or "INTEL_XEON". Defaults to
     *                     "AMD_OPTERON".
     * @return Server object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Server createServer(String dataCenterId,
                                                               com.ionosenterprise.rest.domain.Server server)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.create(getResourcePathBuilder().withPathParams(dataCenterId).build(), server,
                com.ionosenterprise.rest.domain.Server.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * This will remove a server from a data center. NOTE: This will not
     * automatically remove the storage volume(s) attached to a server. A
     * separate API call is required to perform that action.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     * @return a String representing the requestId
     */
    public String deleteServer(String dataCenterId, String serverId) throws RestClientException, IOException {
        return client.delete(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId).build(),
                HttpStatus.SC_ACCEPTED);

    }

    /**
     * Creates a server within an existing data center, You can configure
     * additional properties such as specifying a boot volume and connecting the
     * server to an existing LAN.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     * @param server       object has the following properties:
     *                     <br>
     *                     <br>
     *                     name = The hostname of the server.
     *                     <br>
     *                     <br>
     *                     cores = The total number of cores for the server.
     *                     <br>
     *                     <br>
     *                     ram = The amount of memory for the server in MB, e.g. 2048. Size must be
     *                     specified in multiples of 256 MB with a minimum of 256 MB; however, if
     *                     you set ramHotPlug to TRUE then you must use a minimum of 1024 MB.
     *                     <br>
     *                     <br>
     *                     availabilityZone = The availability zone in which the server should
     *                     exist.
     *                     <br>
     *                     <br>
     *                     licenceType = Sets the OS type of the server. If undefined the OS type
     *                     will be inherited from the boot image or boot volume.
     *                     <br>
     *                     <br>
     *                     bootVolume = Reference to a Volume used for booting. If not �null� then
     *                     bootCdrom has to be �null�.
     *                     <br>
     *                     <br>
     *                     bootCdrom = Reference to a CD-ROM used for booting. If not 'null' then
     *                     bootVolume has to be 'null'.
     * @return Server object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Server updateServer(String dataCenterId, String serverId, Object server)
            throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        return client.update(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId).build(),
                server, com.ionosenterprise.rest.domain.Server.class, HttpStatus.SC_ACCEPTED);

    }

    /**
     * This will force a hard reboot of the server, Do not use this method if you want to gracefully reboot the machine.
     * This is the equivalent of powering off the machine and turning it back on.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     */
    public void rebootServer(String dataCenterId, String serverId) throws RestClientException, IOException {
        client.execute(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId)
                .appendPathSegment("/reboot").build(), HttpStatus.SC_ACCEPTED);
    }

    /**
     * This will start a server. If the server's public IP was deallocated then a new IP will be assigned.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     */
    public void startServer(String dataCenterId, String serverId) throws RestClientException, IOException {
        client.execute(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId)
                .appendPathSegment("/start").build(), HttpStatus.SC_ACCEPTED);
    }

    /**
     * This will stop a server. The machine will be forcefully powered off, billing will cease, and the public IP,
     * if one is allocated, will be deallocated.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     */
    public void stopServer(String dataCenterId, String serverId) throws RestClientException, IOException {
        client.execute(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId)
                .appendPathSegment("/stop").build(), HttpStatus.SC_ACCEPTED);
    }

    /**
     * CD ROM functions
     */

    /**
     * This will retrieve a list of CD-ROMs attached to the server.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     */
    public CDRoms getAllAttachedCDRoms(String dataCenterId, String serverId) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId)
                .appendPathSegment("/cdroms").withDepth().build(), Collections.EMPTY_MAP, CDRoms.class);
    }

    /**
     * This will retrieve a CD-ROMs attached to the server.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     * @param cdromId      The unique ID of the CD ROM
     */
    public CDRom getAttachedCDRom(String dataCenterId, String serverId, String cdromId)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId)
                .appendPathSegment("/cdroms").appendPathSegment(cdromId).withDepth().build(), Collections.EMPTY_MAP,
                CDRom.class);
    }

    /**
     * Attaches a CD-ROM to an existing server.
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     * @param imageId      The unique ID of the CD ROM Image
     */
    public CDRom attachCDRom(String dataCenterId, String serverId, String imageId) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        PBObject object = new PBObject();
        object.setId(imageId);
        return client.create(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId)
                .appendPathSegment("/cdroms").build(), object, CDRom.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * Detaches a CD-ROM from the server
     *
     * @param dataCenterId The unique ID of the data center
     * @param serverId     The unique ID of the server
     * @param cdromId      The unique ID of the CD ROM Image
     * @return a String representing the requestId
     */
    public String detachCDRom(String dataCenterId, String serverId, String cdromId)
            throws RestClientException, IOException {

        return client.delete(getResourcePathBuilder().withPathParams(dataCenterId).appendPathSegment(serverId)
                .appendPathSegment("/cdroms").appendPathSegment(cdromId).build(), HttpStatus.SC_ACCEPTED);
    }

}
