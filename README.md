This guide will show you how to programmatically perform common management tasks using the JAVA SDK for the ProfitBricks REST API.

## Table of Contents

* [Concepts](#Concepts)
* [Getting Started](#GettingStarted)
* [How to: Create a Data Center](#CreateDataCenter)
* [How to: Delete a Data Center](#DeleteDataCenter)
* [How to: Create a Server](#CreateServer)
* [How to: Update Cores, Memory, and Disk](#UpdateCoresMemoryDisk)
* [How to: Detach and Reattach a Storage Volume](#DetachReattachStorageVolume)
* [How to: List Servers, Volumes, and Data Centers](#ListServersVolumesDataCenters)
* [How to: Create Additional Network Interfaces](#CreateNic)

--------------

## <a name="Concepts"></a>Concepts

This JAVA library wraps the ProfitBricks REST API. All API operations are performed over SSL and authenticated using your ProfitBricks portal credentials. The API can be accessed within an instance running in ProfitBricks or directly over the Internet from any application that can send an HTTPS request and receive an HTTPS response. 

## <a name="GettingStarted"></a>Getting Started

Before you begin you will need to have [signed-up](https://www.profitbricks.com/signup) for a ProfitBricks account. The credentials you setup during sign-up will be used to authenticate against the API.


### Installation

The official JAVA library is available from the ProfitBricks GitHub account found [here](https://github.com/profitbricks/profitbricks-sdk-java). You can download the latest stable version by cloning the repository and then adding the project to your solution.
	
Or you can add the SDK:

	  <dependency>
            <groupId>com.profitbricks.rest.client</groupId>
            <artifactId>profitbricks-sdk-java</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>


### Using the Driver

Here is a simple example on how to use the library.

List all data centers: 

        DataCenters datacenters = profitbricksApi.getDataCenterApi().getAllDataCenters();

    
This will list all virtual data centers you have under your account.

### Additional Documentation and Support

You can engage with us in the community and we'll be more than happy to answer any questions you might have. 

## <a name="CreateDataCenter"> </a>How to: Create a Data Center

ProfitBricks introduces the concept of data centers. These are logically separated from one and the other and allow you to have a self-contained environment for all servers, volumes, networking, snapshots, and so forth. The goal is to give you the same experience as you would have if you were running your own physical data center.

You are required to have a data center created before you can create any other objects. Think of the data center as a bucket in which all objects, such as servers and volumes, live. 

The following code example shows you how to programmatically create a data center: 

	 DataCenter datacenter = new DataCenter();

      datacenter.getProperties().setName("SDK TEST DC - Data center");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);

## <a name="DeleteDataCenter"></a>How to: Delete a Data Center

You will want to exercise a bit of caution here. Removing a data center will **destroy** all objects contained within that data center -- servers, volumes, snapshots, and so on. The objects -- once removed -- will be unrecoverable. 

The following is an example on how to remove the data center created above:

          profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);


## <a name="CreateServer"></a>How to: Create a Server

The following example illustrates how you would create a server and assign it an OS, cores, and memory. We urge you to check the [documentation](https://devops.profitbricks.com/api/soap/) to see the complete list attributes available.

	   Server server = new Server();
      server.getProperties().setName("SDK TEST SERVER - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);

One of the unique features of the ProfitBricks platform when compared with the other providers is that it allows you to define your own settings for cores, memory, and disk size without being tied to a particular size.  

## <a name="UpdateServer"></a>How to: Update Cores, Memory, and Disk

ProfitBricks allows users to dynamically update cores, memory, and disk independently of each other. This removes the restriction of needing to upgrade to the next size up to receive an increase in memory. You can now simply increase the instances memory keeping your costs in-line with your resource needs. 

The following code illustrates how you can update cores and memory: 

	  String newName = "SDK TEST SERVER CHANGED";
      PBObject object = new PBObject();
      object.setName(newName);

      Server updatedServer = profitbricksApi.getServerApi().updateServer(dataCenterId, serverId, object);

## <a name="DetachReattachStorageVolume"></a>How to: Detach and Reattach a Storage Volume

ProfitBricks allows for the creation of multiple storage volumes. You can detach and reattach these on the fly. This allows for various scenarios such as re-attaching a failed OS disk to another server for possible recovery, move a volume to another location and re-attaching to a different server and spinning it up. 

The following illustrates how you would attach a volume and then detach it from a server:

	//First we need to create a volume.
	  Volume volume = new Volume();
	  volume.getProperties().setName("SDK TEST VOLUME - Volume");
	  volume.getProperties().setSize("1024");
	  volume.getProperties().setLicenceType("LINUX");
	
	  Volume newVolume = profitbricksApi.getVolumeApi().createVolume(dataCenterId, volume);

	//Then we are going to attach the newly volume to a server.
      Volume attachedVolume = profitbricksApi.getVolumeApi().attachVolume(dataCenterId, serverId, volumeId);


	//Here we are going to detach it from the server.
	      profitbricksApi.getVolumeApi().detachVolume(dataCenterId, serverId, volumeId);


## <a name="ListServersVolumesDataCenters"></a>How to: List Servers, Volumes, and Data Centers

You can pull various resource lists from your data centers using the JAVA library. The three most common resources are data centers, Servers, and Volumes.

The following code illustrates how to pull these three list types: 

	      DataCenters datacenters = profitbricksApi.getDataCenterApi().getAllDataCenters();

	      Servers servers = profitbricksApi.getServerApi().getAllServers(dataCenterId);

	      Volumes volumes = profitbricksApi.getVolumeApi().getAllVolumes(dataCenterId);


## <a name="CreateNic"></a>How to: Create Additional Network Interfaces

The ProfitBricks platform supports adding multiple NICs to a server. These NICs can be used to create different, segmented networks on the platform.
The sample below shows you how to add a second NIC to an existing server: 

	   Nic nic = new Nic();

      nic.getProperties().setName("SDK TEST NIC - Nic");
      nic.getProperties().setLan("1");

      nic.getEntities().setFirewallrules(null);

      Nic newNic = profitbricksApi.getNicApi().createNic(dataCenterId, serverId, nic);

One item to note is this function will result in the server being rebooted. 
