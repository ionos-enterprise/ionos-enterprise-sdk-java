package com.profitbricks.rest.test.resource;

import com.profitbricks.rest.domain.*;

import java.util.ArrayList;
import java.util.List;

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
            sshKeys.add("ssh-rsa AAAAB3NzaC1");
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
