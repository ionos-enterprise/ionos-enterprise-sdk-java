# SDK for Java

Version: **profitbricks-sdk-java v4.0.0**

## Table of Contents

* [Description](#description)
* [Getting Started](#getting-started)
* [Installation](#installation)
* [Usage](#usage)
    * [Authentication](#authentication)
    * [How to: Create a Data Center](#how-to-create-a-data-center)
    * [How to: Create a Data Center with Multiple Resources](#how-to-create-data-center-with-multiple-resources)
    * [How to: Delete a Data Center](#how-to-delete-a-data-center)
    * [How to: Create a Server](#how-to-create-a-server)
    * [How to: Update Cores and Memory](#how-to-update-cores-and-memory)
    * [How to: Attach and Detach a Volume](#how-to-attach-and-detach-a-volume)
    * [How to: List Data Centers, Servers, and Volumes](#how-to-list-data-centers-servers-and-volumes)
    * [How to: Create Network Interfaces](#how-to-create-network-interfaces)
* [Reference](#reference)
    * [Virtual Data Centers](#virtual-data-centers)
    * [Servers](#servers)
    * [Volumes](#volumes)
    * [NICs](#nics)
    * [Firewall Rules](#firewall-rules)
    * [LANs](#lans)
    * [Images](#images)
    * [Load Balancers](#load-balancers)
    * [Locations](#locations)
    * [IP Blocks](#ip-blocks)
    * [Snapshots](#snapshots)
    * [Requests](#requests)
    * [Locations](#locations)
* [Support](#support)
* [Testing](#testing)
* [Contributing](#contributing)

--------------

## Description

This Java library is a wrapper for the ProfitBricks REST API. All API operations are performed over SSL and authenticated using your ProfitBricks portal credentials. The API can be accessed within an instance running in ProfitBricks or directly over the Internet from any application that can send an HTTPS request and receive an HTTPS response.

This guide will show you how to programmatically perform common management tasks using the ProfitBricks SDK for Java.

## Getting Started

* ProfitBricks account
* Java
* [Apache Maven](https://maven.apache.org/)

Before you begin you will need to have [signed-up](https://www.profitbricks.com/signup) for a ProfitBricks account. The credentials you setup during sign-up will be used to authenticate against the API.

Apache Maven must also be installed. Please review the official [Apache Maven installation documentation](https://maven.apache.org/install.html) for details on the installation.

## Installation

The official ProfitBricks SDK for Java is available from the ProfitBricks GitHub account found [here](https://github.com/profitbricks/profitbricks-sdk-Java). You can download the latest stable version by cloning the repository and then adding the project to your solution.

Once the SDK is downloaded:

1. Maven will run live tests before installing. Therefore, it is necessary set the following environment variables:
       
       * PROFITBRICKS_USERNAME
       * PROFITBRICKS_PASSWORD

2. Now the SDK can be installed. 

        mvn install

    Maven will install the package into the local repository and become available as a dependency to local projects. The `mvn install` command will run live unit tests which can take several minutes to complete. The `-DskipTests=true` parameter can be used to skip these tests:

        mvn install -DskipTests=true

3. Now the ProfitBricks SDK can be added to your `pom.xml` under the `<dependency>` section.

        <dependency>
            <groupId>com.profitbricks.rest.client</groupId>
            <artifactId>profitbricks-sdk-java</artifactId>
            <version>4.0.0</version>
        </dependency>

## Usage

### Authentication

First you need to instantiate ProfitBricks API and pass the credentials.

    ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    profitbricksApi.setCredentials("username", "password");

As an alternative, the following example will inherit the credentials from `PROFITBRICKS_USERNAME` and `PROFITBRICKS_PASSWORD` environment variables:

    profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

It should now be possible to list all data centers:

    DataCenters datacenters = profitbricksApi.getDataCenterApi().getAllDataCenters();

### How to: Create a Data Center

ProfitBricks introduces the concept of virtual data centers. These are logically separated from one another and allow you to have a self-contained environment for all servers, volumes, networking, snapshots, and so forth. The goal is to give you the same experience as you would have if you were running your own physical data center.

You are required to have a data center created before you can create any further objects. Think of the data center as a bucket in which all objects, such as servers and volumes, reside. 

The following code example shows you how to programmatically create a data center: 

    DataCenter datacenter = new DataCenter();

    datacenter.getProperties().setName("SDK TEST DC - Data center");
    datacenter.getProperties().setLocation("us/las");
    datacenter.getProperties().setDescription("SDK TEST Description");

    DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);

### How to: Delete a Data Center

You will want to exercise a bit of caution here. Removing a data center will **destroy** all objects contained within that data center -- servers, volumes, snapshots, and so on. The objects -- **once removed** -- will be **unrecoverable**. 

The following is an example on how to remove the data center created above:

    profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);

### How To: Create Data Center with Multiple Resources

The ProfitBricks SDK for Java allows a single request to create multiple nested resources. The following will create a composite data center with an associated server, NIC, and volume:
    
    DataCenter datacenter = new DataCenter();
    datacenter.getProperties().setName("SDK TEST DC - Composite Data center");
    datacenter.getProperties().setLocation("us/las");
    datacenter.getProperties().setDescription("SDK TEST Description");
    
    // Add a server
    Server server = new Server();
    
    server.getProperties().setName("comp test");
    server.getProperties().setCores("1");
    server.getProperties().setRam("1024");
    
    // Add a volume to the server
    Volume volume = new Volume();
    volume.getProperties().setName("SDK TEST VOLUME - Volume");
    volume.getProperties().setSize("10");
    volume.getProperties().setImage("826c507a-fe3f-11e6-afc5-525400f64d8d");
    volume.getProperties().setType("HDD");
    
    List<String> sshkeys = new ArrayList<String>();
    sshkeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCoLVLHON4BSK3D8L4H79aFo...");
    volume.getProperties().setSshKeys(sshkeys);
    
    Volumes volumes = new Volumes();
    List<Volume> volumeList = new ArrayList<Volume>();
    volumeList.add(volume);
    volumes.setItems(volumeList);
    server.getEntities().setVolumes(volumes);
    
    // Add a NIC to the server
    final Nic nic = new Nic();
    nic.getProperties().setName("SDK TEST NIC - Nic");
    nic.getProperties().setLan("1");
    nic.getProperties().setNat(Boolean.FALSE);
    
    Nics nics = new Nics();
    List<Nic> nicList = new ArrayList<Nic>();
    nicList.add(nic);
    nics.setItems(nicList);
    server.getEntities().setNics(nics);
    
    Servers servers = new Servers();
    List<Server> serversList = new ArrayList<Server>();
    serversList.add(server);
    servers.setItems(serversList);
    datacenter.getEntities().setServers(servers);
    
    DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);

### How to: Create a Server

The following example demonstrates how you would create a server and assign it an OS, cores, and memory. We urge you to check the [documentation](https://devops.profitbricks.com/api/rest/) to see the complete list attributes available.

    Server server = new Server();
    server.getProperties().setName("SDK TEST SERVER - Server");
    server.getProperties().setRam("1024");
    server.getProperties().setCores("4");
    server.getProperties().setCpuFamily("AMD_OPTERON");

    Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);

One of the unique features of the ProfitBricks platform when compared with the other providers is that it allows you to define your own settings for cores, memory, and disk size without being tied to a particular instance size.  

### How to: Update Cores and Memory

ProfitBricks allows users to dynamically update cores and memory independently of each other. This removes the restriction of needing to upgrade to the next size up to receive an increase in memory. You can now simply increase the instances memory keeping your costs in-line with your resource needs. 

The following code illustrates how you can update cores and memory: 

    PBObject object = new PBObject();
    object.setName("SDK TEST SERVER CHANGED");
    object.setRam("1024");
    object.setCores("4");

    Server updatedServer = profitbricksApi.getServerApi().updateServer(dataCenterId, serverId, object);

### How to: Attach and Detach a Volume

ProfitBricks allows for the creation of multiple storage volumes. You can attach and detach these on the fly. This allows for various scenarios such as attaching a failed OS disk to another server for possible recovery or moving a volume to another server to bring online.

The following illustrates how you would attach a volume and then detach it from a server:

    // First we need to create a volume.
    Volume volume = new Volume();
    volume.getProperties().setName("SDK TEST VOLUME - Volume");
    volume.getProperties().setSize("1024");
    volume.getProperties().setLicenceType("LINUX");
    volume.getProperties().setType("HDD");
  
    Volume newVolume = profitbricksApi.getVolumeApi().createVolume(dataCenterId, volume);

    // Then we are going to attach the new volume to a server.
    Volume attachedVolume = profitbricksApi.getVolumeApi().attachVolume(dataCenterId, serverId, volumeId);

    // Here we are going to detach it from the server.
    profitbricksApi.getVolumeApi().detachVolume(dataCenterId, serverId, volumeId);

### How to: List Data Centers, Servers, and Volumes

You can pull various resource lists from your data centers using the SDK. The three most common resources are data centers, servers, and volumes.

The following code illustrates how to retrieve these three list types: 

    DataCenters datacenters = profitbricksApi.getDataCenterApi().getAllDataCenters();

    Servers servers = profitbricksApi.getServerApi().getAllServers(dataCenterId);

    Volumes volumes = profitbricksApi.getVolumeApi().getAllVolumes(dataCenterId);

### How to: Create Network Interfaces

The ProfitBricks platform supports adding multiple NICs to a server. These NICs can be used to create different, segmented networks on the platform.
The sample below shows you how to add a second NIC to an existing server: 

    Nic nic = new Nic();

    nic.getProperties().setName("SDK TEST NIC - Nic");
    nic.getProperties().setLan("1");

    nic.getEntities().setFirewallrules(null);

    Nic newNic = profitbricksApi.getNicApi().createNic(dataCenterId, serverId, nic);

One item to note is this function will result in the server being restarted.

## Reference  

### Virtual Data Centers

* getAllDataCenters()
* getDataCenter(String id)
* createDataCenter(DataCenter datacenter)
* updateDataCenter(String id, PBObject datacenter)
* deleteDataCenter(String id)

### Servers

* getAllServers(String dataCenterId)
* getServer(String dataCenterId, String serverId)
* createServer(String dataCenterId, Server server)
* deleteServer(String dataCenterId, String serverId)
* updateServer(String dataCenterId, String serverId, PBObject server)
* rebootServer(String dataCenterId, String serverId)
* startServer(String dataCenterId, String serverId)
* stopServer(String dataCenterId, String serverId)

### Volumes

* getAllVolumes(String dataCenterId)
* getAllVolumes(String dataCenterId, String serverId)
* getVolume(String dataCenterId, String volumeId)
* createVolume(String dataCenterId, Volume volume)
* attachVolume(String dataCenterId, String serverId, String volumeId)
* updateVolume(String dataCenterId, String volumeId, PBObject volume)
* detachVolume(String dataCenterId, String serverId, String volumeId)
* deleteVolume(String dataCenterId, String volumeId)

### NICs

* getAllNics(String dataCenterId, String serverId)
* getNic(String dataCenterId, String serverId, String nicId)
* createNic(String dataCenterId, String serverId, Nic nic)
* updateNic(String dataCenterId, String serverId, String nicId, PBObject nic)
* deleteNic(String dataCenterId, String serverId, String nicId)
* assignNicToLoadBalancer(String dataCenterId, String loadBalancerId, String nicId)
* unassignNicFromLoadBalancer(String dataCenterId, String loadBalancerId, String nicId)
* getAllBalancedNics(String dataCenterId, String loadBalancerId, String serverId)
* getBalancedNic(String dataCenterId, String loadBalancerId, String serverId, String nicId)

### Firewall Rules

* getAllFirewallRules(String dataCenterId, String serverId, String nicId)
* getFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId)
* createFirewallRule(String dataCenterId, String serverId, String nicId, FirewallRule firewallRule)
* deleteFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId)
* updateFirewWallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId, PBObject firewallRule)

### LANs

* getAllLans(String dataCenterId)
* getLan(String dataCenterId, String lanId)
* createLan(String dataCenterId, Lan lan)
* updateLan(String dataCenterId, String lanId, Boolean isPublic)
* deleteLan(String dataCenterId, String lanId)

### Images

* getAllImages()
* getImage(String imageId)
* deleteImage(String imageId)
* updateImage(String imageId, PBObject object)

### Load Balancers

* getAllLoadBalancers(String dataCenterId)
* getLoadBalancer(String dataCenterId, String loadBalancerId)
* createLoadBalancer(String dataCenterId, LoadBalancer loadBalancer)
* updateLoadBalancer(String dataCenterId, String loadBalancerId, PBObject loadBalancer) 
* deleteLoadBalaner(String dataCenterId, String loadBalancerId)

### IP Blocks

* getAllIPBlocks()
* getIPBlock(String ipBlockId)
* createIPBlock(String location, String size, IPBlock ipBlock)
* deleteIPBlock(String ipBlockId)
    
### Snapshots

* getAllSnapshots()
* restoreSnapshot(String dataCenterId, String serverId, String snapshotId)
* getSnapshot(String snapshotId)
* updateSnapshot(String dataCenterId, String snapshotId, PBObject snapshot)
* deleteSnapshot(String snapshotId)

### Requests

* getRequest(String url)

### Locations

* getAllLocations()
* getLocation(String id)

## Support

You can engage with us on the [ProfitBricks DevOps Central](https://devops.profitbricks.com/) site where we will be more than happy to answer any questions you might have. Please review the list below for additional resources.

* [ProfitBricks SDK for Java](https://devops.profitbricks.com/libraries/java/) guide.
* [ProfitBricks REST API](https://devops.profitbricks.com/api/rest/) documentation.
* Ask a question or discuss at [ProfitBricks DevOps Central](https://devops.profitbricks.com/community/).
* Report an [issue here](https://github.com/profitbricks/profitbricks-sdk-java/issues).

## Testing

To run the unit tests you need to set following environment variables:

    export PROFITBRICKS_USERNAME=username
    export PROFITBRICKS_PASSWORD=password

Maven can then be used to run the tests.

    mvn test

## Contributing

1. Fork the repository (https://github.com/profitbricks/profitbricks-sdk-java/fork).
2. Create your feature branch (git checkout -b my-new-feature).
3. Commit your changes (git commit -am 'Add some feature').
4. Push to the branch (git push origin my-new-feature).
5. Create a new Pull Request.