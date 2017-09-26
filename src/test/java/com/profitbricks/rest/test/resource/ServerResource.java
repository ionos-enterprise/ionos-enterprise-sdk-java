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
package com.profitbricks.rest.test.resource;

import com.profitbricks.rest.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis@stackpointcloud.com
 */
public class ServerResource {
    private static Server server;
    private static Server compositeServer;
    private static Server badServer;
    private static Server editServer;

    public static Server getServer() {
        if (server == null) {
            server = new Server();
            server.getProperties().setName("Java SDK Test");
            server.getProperties().setRam(1024);
            server.getProperties().setCores(1);
            server.getProperties().setAvailabilityZone("ZONE_1");
            server.getProperties().setCpuFamily("INTEL_XEON");
        }

        return server;
    }

    public static Server getCompositeServer() {
        if (compositeServer == null) {
            compositeServer = new Server();
            compositeServer.getProperties().setName("Java SDK Test Composite");
            compositeServer.getProperties().setRam(1024);
            compositeServer.getProperties().setCores(1);
            compositeServer.getProperties().setAvailabilityZone("ZONE_1");
            compositeServer.getProperties().setCpuFamily("INTEL_XEON");

            Nic nic = new Nic();
            nic.getProperties().setName("Java SDK Test");
            nic.getProperties().setDhcp(true);
            nic.getProperties().setLan("1");
            nic.getProperties().setFirewallActive(true);
            nic.getProperties().setNat(false);

            Nics nics = new Nics();
            nics.addItem(nic);
            compositeServer.getEntities().setNics(nics);

            FirewallRule rule = new FirewallRule();
            rule.getProperties().setName("SSH");
            rule.getProperties().setProtocol("TCP");
            rule.getProperties().setSourceMac("01:23:45:67:89:00");
            rule.getProperties().setSourceIp("None");
            rule.getProperties().setTargetIp("None");
            rule.getProperties().setPortRangeEnd("22");
            rule.getProperties().setPortRangeStart("22");
            rule.getProperties().setIcmpCode("None");
            rule.getProperties().setIcmpType("None");

            FirewallRules rules = new FirewallRules();
            rules.addItem(rule);
            nic.getEntities().setFirewallrules(rules);

            Volume volume = new Volume();
            volume.getProperties().setName("Java SDK Test");
            volume.getProperties().setSize(2);
            volume.getProperties().setBus(Bus.VIRTIO);
            volume.getProperties().setType("HDD");
            volume.getProperties().setLicenceType(LicenceType.UNKNOWN);
            volume.getProperties().setAvailabilityZone(AvailabilityZone.ZONE_1);
            List<String> sshKeys= new ArrayList<String>();
            sshKeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDgnV5MOhBqpQLt66Ksdf/VYtmVPUt6epSVxnxrvjayNto5flG2sH4cGqdI2C0NE9/w7BFNdwWqp0mL2kYynC8l+SejW/qjx37hrEBWIXqdTyumchm0LD/7K7P7/kz14IV5NcHjNAsntPgKjx/fzJlbA1VCQYmnOq9RZeKme44rdHYW0BBfgMzekcEbyGTNDGp51NYhVafZLXsF8MzCKlJ+NCPlDqzD6w0fQe/qtMFO8NbFyS9/Lk4prp4HAWEyLSM26w1iLycYpbpWrHw6oc1U7bNIgbsa0ezDu4+OPkxeHz7aG5TeJ/dn0Wftzdfy2sy5PJy5MnYP3RTuROsOv+chu+AshZNNJ9A4ar5gFXSX40sQ0i4GzxZGrsKhW42ZP4sElzV74gEBQ2BOIOJUh4qGRtnjsQCJHBs7DLgpeVeGUq2B7p5zDAlJBGCXiHuTgIM8aVnpdnNrFwmr9SF66iaTrt7x8HinNOCIIztMU15Fk2AYSxSEuju1d3VcPt/d0= spc@spc");
            volume.getProperties().setSshKeys(sshKeys);
            Volumes volumes = new Volumes();
            volumes.addItem(volume);
            compositeServer.getEntities().setVolumes(volumes);
        }

        return server;
    }

    public static Server getBadServer() {
        if (badServer == null) {
            badServer = new Server();
            badServer.getProperties().setName("Java SDK Test");
            badServer.getProperties().setRam(1024);
        }

        return badServer;
    }

    public static Server getEditServer() {
        if (editServer == null) {
            editServer = new Server();
            editServer.getProperties().setName("Java SDK Test RENAME");
        }

        return editServer;
    }
}
