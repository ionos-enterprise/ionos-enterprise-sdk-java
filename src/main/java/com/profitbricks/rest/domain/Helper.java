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
package com.profitbricks.rest.domain;

import com.profitbricks.rest.domain.raw.LansRaw;
import com.profitbricks.rest.domain.raw.DataCentersRaw;
import com.profitbricks.rest.domain.raw.FirewallRulesRaw;
import com.profitbricks.rest.domain.raw.NicsRaw;
import com.profitbricks.rest.domain.raw.CDRomsRaw;
import com.profitbricks.rest.domain.raw.LanRaw;
import com.profitbricks.rest.domain.raw.CDRomRaw;
import com.profitbricks.rest.domain.raw.NicRaw;
import com.profitbricks.rest.domain.raw.LoadBalancerRaw;
import com.profitbricks.rest.domain.raw.LoadBalancersRaw;
import com.profitbricks.rest.domain.raw.ServersRaw;
import com.profitbricks.rest.domain.raw.ServerRaw;
import com.profitbricks.rest.domain.raw.VolumesRaw;
import com.profitbricks.rest.domain.raw.VolumeRaw;
import com.profitbricks.rest.domain.raw.FirewallRuleRaw;
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.raw.IPBlockRaw;
import com.profitbricks.rest.domain.raw.IPBlocksRaw;
import com.profitbricks.rest.domain.raw.ImageRaw;
import com.profitbricks.rest.domain.raw.ImagesRaw;
import com.profitbricks.rest.domain.raw.SnapshotRaw;
import com.profitbricks.rest.domain.raw.SnapshotsRaw;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class Helper {

   public static DataCenter convertDataCenter(DataCenterRaw datacenter) {
      DataCenter toReturn = new DataCenter();

      if (datacenter == null)
         return toReturn;

      toReturn.setId(datacenter.getId());
      toReturn.setType(datacenter.getType());
      toReturn.setHref(datacenter.getHref());
      toReturn.setRequestId(datacenter.getRequestId());
      toReturn.setMetadata(datacenter.getMetadata());
      toReturn.setName(datacenter.getProperties().getName());
      toReturn.setDescription(datacenter.getProperties().getDescription());
      toReturn.setLocation(datacenter.getProperties().getLocation());

      toReturn.setServers(convertServers(datacenter.getEntities().getServers()));
      toReturn.setVolumes(convertVolumes(datacenter.getEntities().getVolumes()));
      toReturn.setLoadbalancers(convertLoadBalancers(datacenter.getEntities().getLoadbalancers()));
      toReturn.setLans(convertLans(datacenter.getEntities().getLans()));

      return toReturn;
   }

   public static List<DataCenter> convertDataCenters(DataCentersRaw datacenters) {
      List<DataCenter> toReturn = new ArrayList<DataCenter>();

      if (datacenters == null)
         return toReturn;
      for (DataCenterRaw dc : datacenters.getItems())
         toReturn.add(convertDataCenter(dc));

      return toReturn;
   }

   public static List<Server> convertServers(ServersRaw servers) {
      List<Server> toReturn = new ArrayList<Server>();

      if (servers == null)
         return toReturn;

      for (ServerRaw s : servers.getItems())
         toReturn.add(convertServer(s));
      return toReturn;
   }

   public static Server convertServer(ServerRaw server) {
      Server toReturn = new Server();

      if (server == null)
         return toReturn;

      toReturn.setId(server.getId());
      toReturn.setType(server.getType());
      toReturn.setHref(server.getHref());
      toReturn.setRequestId(server.getRequestId());
      toReturn.setMetadata(server.getMetadata());
      toReturn.setName(server.getProperties().getName());
      toReturn.setAvailabilityZone(server.getProperties().getAvailabilityZone());
      toReturn.setBootCdrom(server.getProperties().getBootCdrom());
      toReturn.setBootVolume(server.getProperties().getBootVolume());
      toReturn.setLicencetype(server.getProperties().getLicencetype());
      toReturn.setCores(server.getProperties().getCores());
      toReturn.setRam(server.getProperties().getRam());
      toReturn.setVmState(server.getProperties().getVmState());

      if (server.getEntities() != null) {
         toReturn.setNics(convertNics(server.getEntities().getNics()));
         toReturn.setVolumes(convertVolumes(server.getEntities().getVolumes()));
         toReturn.setCdroms(convertImages(server.getEntities().getCdroms()));
      }
      return toReturn;
   }

   public static List<Nic> convertNics(NicsRaw nics) {
      List<Nic> toReturn = new ArrayList<Nic>();

      if (nics == null)
         return toReturn;

      for (NicRaw n : nics.getItems())
         toReturn.add(convertNic(n));

      return toReturn;
   }

   public static Nic convertNic(NicRaw nic) {
      Nic toReturn = new Nic();

      if (nic == null)
         return toReturn;

      toReturn.setId(nic.getId());
      toReturn.setType(nic.getType());
      toReturn.setHref(nic.getHref());
      toReturn.setRequestId(nic.getRequestId());
      toReturn.setMetadata(nic.getMetadata());
      toReturn.setName(nic.getProperties().getName());
      toReturn.setFirewallActive(nic.getProperties().isFirewallActive());
      toReturn.setDhcp(nic.getProperties().isDhcp());
      toReturn.setMac(nic.getProperties().getMac());
      toReturn.setIps(nic.getProperties().getIps());

      toReturn.setFirewallrules(convertFirewallRules(nic.getEntities().getFirewallrules()));

      return toReturn;
   }

   public static List<FirewallRule> convertFirewallRules(FirewallRulesRaw firewallrules) {
      List<FirewallRule> toReturn = new ArrayList<FirewallRule>();

      if (firewallrules == null)
         return toReturn;

      for (FirewallRuleRaw r : firewallrules.getItems())
         toReturn.add(convertFirewallRule(r));

      return toReturn;
   }

   public static FirewallRule convertFirewallRule(FirewallRuleRaw r) {
      FirewallRule toReturn = new FirewallRule();

      if (r == null)
         return toReturn;

      toReturn.setId(r.getId());
      toReturn.setType(r.getType());
      toReturn.setHref(r.getHref());
      toReturn.setRequestId(r.getRequestId());
      toReturn.setMetadata(r.getMetadata());
      toReturn.setName(r.getProperties().getName());
      toReturn.setProtocol(r.getProperties().getProtocol());
      toReturn.setSourceMac(r.getProperties().getSourceMac());
      toReturn.setSourceIp(r.getProperties().getSourceIp());
      toReturn.setTargetIp(r.getProperties().getTargetIp());
      toReturn.setPortRangeStart(r.getProperties().getPortRangeStart());
      toReturn.setPortRangeEnd(r.getProperties().getPortRangeEnd());
      toReturn.setIcmpType(r.getProperties().getIcmpType());
      toReturn.setIcmpCode(r.getProperties().getIcmpCode());

      return toReturn;
   }

   public static List<Volume> convertVolumes(VolumesRaw volumes) {
      List<Volume> toReturn = new ArrayList<Volume>();

      if (volumes == null)
         return toReturn;

      for (VolumeRaw v : volumes.getItems())
         toReturn.add(convertVolume(v));

      return toReturn;
   }

   public static Volume convertVolume(VolumeRaw v) {
      Volume toReturn = new Volume();

      if (v == null)
         return toReturn;

      toReturn.setId(v.getId());
      toReturn.setType(v.getType());
      toReturn.setHref(v.getHref());
      toReturn.setRequestId(v.getRequestId());
      toReturn.setMetadata(v.getMetadata());
      toReturn.setName(v.getProperties().getName());
      toReturn.setCores(v.getProperties().getCores());
      toReturn.setRam(v.getProperties().getRam());
      toReturn.setAvailabilityZone(v.getProperties().getAvailabilityZone());
      toReturn.setVmState(v.getProperties().getVmState());
      toReturn.setBootVolume(v.getProperties().getBootVolume());
      toReturn.setBootCdrom(v.getProperties().getBootCdrom());
      toReturn.setSize(v.getProperties().getSize());
      toReturn.setBus(v.getProperties().getBus());
      toReturn.setImage(v.getProperties().getImage());
      toReturn.setLicenceType(v.getProperties().getLicenceType());

      return toReturn;
   }

   public static List<LoadBalancer> convertLoadBalancers(LoadBalancersRaw loadbalancers) {
      List<LoadBalancer> toReturn = new ArrayList<LoadBalancer>();

      if (loadbalancers == null)
         return toReturn;

      for (LoadBalancerRaw l : loadbalancers.getItems())
         toReturn.add(convertLoadBalancer(l));

      return toReturn;
   }

   public static List<CDRom> convertCDRoms(CDRomsRaw cdroms) {
      List<CDRom> toReturn = new ArrayList<CDRom>();

      if (cdroms == null)
         return toReturn;

      for (CDRomRaw c : cdroms.getItems())
         toReturn.add(convertCDRom(c));
      return toReturn;
   }

   public static CDRom convertCDRom(CDRomRaw c) {
      CDRom toReturn = new CDRom();
      if (c == null)
         return toReturn;

      toReturn.setId(c.getId());
      toReturn.setType(c.getType());
      toReturn.setHref(c.getHref());
      toReturn.setRequestId(c.getRequestId());
      toReturn.setMetadata(c.getMetadata());
      toReturn.setName(c.getProperties().getName());
      toReturn.setImage(convertCDImage(c.getProperties().getImage()));

      return toReturn;
   }

   public static CDRom.Image convertCDImage(CDRomRaw.Properties.Image image) {
      CDRom.Image toReturn = new CDRom.Image();

      if (image == null)
         return toReturn;

      toReturn.setId(image.getId());
      toReturn.setType(image.getType());
      toReturn.setHref(image.getHref());

      return toReturn;
   }

   public static LoadBalancer convertLoadBalancer(LoadBalancerRaw l) {
      LoadBalancer toReturn = new LoadBalancer();

      if (l == null)
         return toReturn;

      toReturn.setId(l.getId());
      toReturn.setType(l.getType());
      toReturn.setHref(l.getHref());
      toReturn.setRequestId(l.getRequestId());
      toReturn.setMetadata(l.getMetadata());
      toReturn.setName(l.getProperties().getName());
      toReturn.setBalancednics(convertNics(l.getEntities().getBalancednics()));

      return toReturn;
   }

   public static List<Lan> convertLans(LansRaw lans) {
      List<Lan> toReturn = new ArrayList<Lan>();

      if (lans == null)
         return toReturn;

      for (LanRaw l : lans.getItems())
         toReturn.add(convertLan(l));

      return toReturn;
   }

   public static Lan convertLan(LanRaw l) {
      Lan toReturn = new Lan();

      if (l == null)
         return toReturn;

      toReturn.setId(l.getId());
      toReturn.setType(l.getType());
      toReturn.setHref(l.getHref());
      toReturn.setRequestId(l.getRequestId());
      toReturn.setMetadata(l.getMetadata());
      toReturn.setName(l.getProperties().getName());
      toReturn.setIsPublic(l.getProperties().isIsPublic());
      toReturn.setNics(convertNics(l.getEntities().getNics()));

      return toReturn;
   }

   public static Snapshot convertSnapshot(SnapshotRaw s) {
      Snapshot toReturn = new Snapshot();

      if (s == null)
         return toReturn;

      toReturn.setId(s.getId());
      toReturn.setType(s.getType());
      toReturn.setHref(s.getHref());
      toReturn.setRequestId(s.getRequestId());
      toReturn.setMetadata(s.getMetadata());
      toReturn.setName(s.getProperties().getName());
      toReturn.setDescription(s.getProperties().getDescription());
      toReturn.setLocation(s.getProperties().getLocation());
      toReturn.setSize(s.getProperties().getSize());
      toReturn.setLicenceType(s.getProperties().getLicenceType());
      toReturn.setCpuHotPlug(s.getProperties().getCpuHotPlug());
      toReturn.setCpuHotUnplug(s.getProperties().getCpuHotUnplug());
      toReturn.setRamHotPlug(s.getProperties().getRamHotPlug());
      toReturn.setRamHotUnplug(s.getProperties().getRamHotUnplug());
      toReturn.setNicHotPlug(s.getProperties().getNicHotPlug());
      toReturn.setNicHotUnplug(s.getProperties().getNicHotUnplug());
      toReturn.setDiscScsiHotPlug(s.getProperties().getDiscScsiHotPlug());
      toReturn.setDiscScsiHotUnplug(s.getProperties().getDiscScsiHotUnplug());
      toReturn.setDiscVirtioHotPlug(s.getProperties().getDiscVirtioHotPlug());
      toReturn.setDiscVirtioHotUnplug(s.getProperties().getDiscVirtioHotUnplug());

      return toReturn;
   }

   public static List<Snapshot> convertSnapshots(SnapshotsRaw snapshots) {
      List<Snapshot> toReturn = new ArrayList<Snapshot>();

      if (snapshots == null)
         return toReturn;

      for (SnapshotRaw s : snapshots.getItems())
         toReturn.add(convertSnapshot(s));

      return toReturn;
   }

   public static List<IPBlock> convertIPBlocks(IPBlocksRaw ipblocks) {
      List<IPBlock> toReturn = new ArrayList<IPBlock>();

      if (ipblocks == null)
         return toReturn;

      for (IPBlockRaw i : ipblocks.getItems())
         toReturn.add(convertIPBlock(i));

      return toReturn;
   }

   public static IPBlock convertIPBlock(IPBlockRaw i) {
      IPBlock toReturn = new IPBlock();

      if (i == null)
         return toReturn;

      toReturn.setId(i.getId());
      toReturn.setType(i.getType());
      toReturn.setHref(i.getHref());
      toReturn.setRequestId(i.getRequestId());
      toReturn.setMetadata(i.getMetadata());
      toReturn.setIps(i.getProperties().getIps());
      toReturn.setLocation(i.getProperties().getLocation());
      toReturn.setSize(i.getProperties().getSize());

      return toReturn;
   }

   public static List<Image> convertImages(ImagesRaw images) {
      List<Image> toReturn = new ArrayList<Image>();

      if (images == null)
         return toReturn;

      for (ImageRaw i : images.getItems())
         toReturn.add(convertImage(i));

      return toReturn;
   }

   public static Image convertImage(ImageRaw i) {
      Image toReturn = new Image();

      if (i == null)
         return toReturn;

      toReturn.setId(i.getId());
      toReturn.setType(i.getType());
      toReturn.setHref(i.getHref());
      toReturn.setRequestId(i.getRequestId());
      toReturn.setMetadata(i.getMetadata());
      toReturn.setName(i.getProperties().getName());
      toReturn.setImagePassword(i.getProperties().getImagePassword());
      toReturn.setBus(i.getProperties().getBus());
      toReturn.setDeviceNumber(i.getProperties().getDeviceNumber());

      toReturn.setDescription(i.getProperties().getDescription());
      toReturn.setLocation(i.getProperties().getLocation());
      toReturn.setIsPublic(i.getProperties().getIsPublic());
      toReturn.setImageType(i.getProperties().getImageType());
      toReturn.setSize(i.getProperties().getSize());
      toReturn.setLicenceType(i.getProperties().getLicenceType());
      toReturn.setCpuHotPlug(i.getProperties().getCpuHotPlug());
      toReturn.setCpuHotUnplug(i.getProperties().getCpuHotUnplug());
      toReturn.setRamHotPlug(i.getProperties().getRamHotPlug());
      toReturn.setRamHotUnplug(i.getProperties().getRamHotUnplug());
      toReturn.setNicHotPlug(i.getProperties().getNicHotPlug());
      toReturn.setNicHotUnplug(i.getProperties().getNicHotUnplug());
      toReturn.setDiscScsiHotPlug(i.getProperties().getDiscScsiHotPlug());
      toReturn.setDiscScsiHotUnplug(i.getProperties().getDiscScsiHotUnplug());
      toReturn.setDiscVirtioHotPlug(i.getProperties().getDiscVirtioHotPlug());
      toReturn.setDiscVirtioHotUnplug(i.getProperties().getDiscVirtioHotUnplug());
      toReturn.setType(i.getProperties().getType());
      toReturn.setImage(i.getProperties().getImage());

      return toReturn;
   }
}
