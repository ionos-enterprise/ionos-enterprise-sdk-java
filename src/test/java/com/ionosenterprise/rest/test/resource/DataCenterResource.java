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
package com.ionosenterprise.rest.test.resource;

import com.ionosenterprise.rest.domain.*;

public class DataCenterResource {

    private static DataCenter dataCenter;
    private static DataCenter compositeDataCenter;
    private static DataCenter badDataCenter;
    private static DataCenter editDataCenter;

    public static DataCenter getDataCenter(){
        if(dataCenter == null){
            dataCenter = new DataCenter();
            dataCenter.getProperties().setName("Java SDK Test");
            dataCenter.getProperties().setDescription("Java SDK test datacenter");
            dataCenter.getProperties().setLocation("us/las");
        }
        return dataCenter;
    }

    public static DataCenter getCompositeDataCenter(){
        if(compositeDataCenter == null){
            compositeDataCenter = new DataCenter();
            compositeDataCenter.getProperties().setName("Java SDK Test Composite");
            compositeDataCenter.getProperties().setDescription("Java SDK test composite datacenter");
            compositeDataCenter.getProperties().setLocation("us/las");

            Server compositeServer = new Server();
            compositeServer.getProperties().setName("Java SDK Test Composite");
            compositeServer.getProperties().setRam(1024);
            compositeServer.getProperties().setCores(1);

            Servers servers = new Servers();
            servers.addItem(compositeServer);

            Volume volume = new Volume();
            volume.getProperties().setName("Java SDK Test");
            volume.getProperties().setSize(2);
            volume.getProperties().setBus(Bus.VIRTIO);
            volume.getProperties().setType("HDD");
            volume.getProperties().setLicenceType(LicenceType.UNKNOWN);
            volume.getProperties().setAvailabilityZone(AvailabilityZone.ZONE_1);
            volume.getProperties().setImageAlias("ubuntu:latest");
            volume.getProperties().setImagePassword("StrongPassword01");
            Volumes volumes = new Volumes();
            volumes.addItem(volume);
            compositeServer.getEntities().setVolumes(volumes);
        }
        return compositeDataCenter;
    }

    public static DataCenter getEditDataCenter(){
        if(editDataCenter == null){
            editDataCenter = new DataCenter();
            editDataCenter.getProperties().setName("Java SDK Test - RENAME");
        }
        return editDataCenter;
    }

    public static DataCenter getBadDataCenter(){
        if(badDataCenter == null){
            badDataCenter = new DataCenter();
            badDataCenter.getProperties().setName("Java SDK Test");
        }

        return badDataCenter;
    }
}
