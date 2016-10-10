This guide will show you how to programmatically perform common management tasks using the Java SDK for the ProfitBricks REST API.

## Table of Contents

* [Concepts](#concepts)
* [Getting Started](#getting-started)
    * [Installation](#installation)
    * [Using the Library](#using-the-library)
* [How to: Create a Data Center](#how-to-create-a-data-center)
* [How to: Delete a Data Center](#how-to-delete-a-data-center)
* [How to: Create a Server](#how-to-create-a-server)
* [How to: Update Cores, Memory, and Volumes](#how-to-update-cores-memory-and-volumes)
* [How to: Attach and Detach a Volume](#how-to-attach-and-detach-a-volume)
* [How to: List Data Centers, Servers, and Volumes](#how-to-list-data-centers-servers-and-volumes)
* [How to: Create Network Interfaces](#how-to-create-network-interfaces)
* [Additional Documentation and Support](#additional-documentation-and-support)

--------------

## Concepts

This Java library is a wrapper for the ProfitBricks REST API. All API operations are performed over SSL and authenticated using your ProfitBricks portal credentials. The API can be accessed within an instance running in ProfitBricks or directly over the Internet from any application that can send an HTTPS request and receive an HTTPS response. 

## Getting Started

Before you begin you will need to have [signed-up](https://www.profitbricks.com/signup) for a ProfitBricks account. The credentials you setup during sign-up will be used to authenticate against the API.


### Installation

The official Java library is available from the ProfitBricks GitHub account found [here](https://github.com/profitbricks/profitbricks-sdk-Java). You can download the latest stable version by cloning the repository and then adding the project to your solution.
  
Or you can add the SDK:

    <dependency>
        <groupId>com.profitbricks.rest.client</groupId>
        <artifactId>profitbricks-sdk-Java</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>


### Using the Library

Here is a simple example on how to use the library.

First you need to instantiate Profitbricks API and pass the credentials:
 
```
    ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
```
List all data centers: 

    DataCenters datacenters = profitbricksApi.getDataCenterApi().getAllDataCenters();
    
This will list all data centers you have under your account.

## How to: Create a Data Center

ProfitBricks introduces the concept of data centers. These are logically separated from one another and allow you to have a self-contained environment for all servers, volumes, networking, snapshots, and so forth. The goal is to give you the same experience as you would have if you were running your own physical data center.

You are required to have a data center created before you can create any further objects. Think of the data center as a bucket in which all objects, such as servers and volumes, reside. 

The following code example shows you how to programmatically create a data center: 

    DataCenter datacenter = new DataCenter();

    datacenter.getProperties().setName("SDK TEST DC - Data center");
    datacenter.getProperties().setLocation(Location.US_LAS.value());
    datacenter.getProperties().setDescription("SDK TEST Description");

    DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);

## How to: Delete a Data Center

You will want to exercise a bit of caution here. Removing a data center will **destroy** all objects contained within that data center -- servers, volumes, snapshots, and so on. The objects -- **once removed** -- will be **unrecoverable**. 

The following is an example on how to remove the data center created above:

    profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);

## How to: Create a Server

The following example illustrates how you would create a server and assign it an OS, cores, and memory. We urge you to check the [documentation](https://devops.profitbricks.com/api/rest/) to see the complete list attributes available.

    Server server = new Server();
    server.getProperties().setName("SDK TEST SERVER - Server");
    server.getProperties().setRam("1024");
    server.getProperties().setCores("4");
    server.getProperties().setCpuFamily("AMD_OPTERON");

    Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);

One of the unique features of the ProfitBricks platform when compared with the other providers is that it allows you to define your own settings for cores, memory, and disk size without being tied to a particular instance size.  

## How to: Update Cores and Memory

ProfitBricks allows users to dynamically update cores and memory independently of each other. This removes the restriction of needing to upgrade to the next size up to receive an increase in memory. You can now simply increase the instances memory keeping your costs in-line with your resource needs. 

The following code illustrates how you can update cores and memory: 

    PBObject object = new PBObject();
    object.setName("SDK TEST SERVER CHANGED");
    object.setRam("1024");
    object.setCores("4");

    Server updatedServer = profitbricksApi.getServerApi().updateServer(dataCenterId, serverId, object);

## How to: Attach and Detach a Volume

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

## How to: List Data Centers, Servers, and Volumes

You can pull various resource lists from your data centers using the Java library. The three most common resources are data centers, servers, and volumes.

The following code illustrates how to pull these three list types: 

    DataCenters datacenters = profitbricksApi.getDataCenterApi().getAllDataCenters();

    Servers servers = profitbricksApi.getServerApi().getAllServers(dataCenterId);

    Volumes volumes = profitbricksApi.getVolumeApi().getAllVolumes(dataCenterId);

## How to: Create Network Interfaces

The ProfitBricks platform supports adding multiple NICs to a server. These NICs can be used to create different, segmented networks on the platform.
The sample below shows you how to add a second NIC to an existing server: 

    Nic nic = new Nic();

    nic.getProperties().setName("SDK TEST NIC - Nic");
    nic.getProperties().setLan("1");

    nic.getEntities().setFirewallrules(null);

    Nic newNic = profitbricksApi.getNicApi().createNic(dataCenterId, serverId, nic);

One item to note is this function will result in the server being rebooted.

## Running the Unit tests

To run the unit tests you need to set following environment variables:

```
PROFITBRICKS_USERNAME
```
and 

```
PROFITBRICKS_PASSWORD
```

## Additional Documentation and Support

You can engage with us on the [ProfitBricks DevOps Central](https://devops.profitbricks.com/) site where we will be more than happy to answer any questions you might have. Please review the list below for additional resources.

* [ProfitBricks SDK for Java](https://devops.profitbricks.com/libraries/profitbricks-sdk-java/) guide.
* [ProfitBricks REST API](https://devops.profitbricks.com/api/rest/) documentation.
* Ask a question or discuss at [ProfitBricks DevOps Central](https://devops.profitbricks.com/community).
* Report an [issue here](https://github.com/profitbricks/profitbricks-sdk-java/issues).
