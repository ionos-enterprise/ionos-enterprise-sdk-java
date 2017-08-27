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
    * [IP Blocks](#ip-blocks)
    * [Snapshots](#snapshots)
    * [Requests](#requests)
    * [Locations](#locations)
    * [Groups](#groups)
    * [Shares](#shares)
    * [Users](#users)
    * [Resources](#resources)
    * [Contract Resources](#contract-resources)
* [Examples](#examples)
    * [POM](#pom)
    * [Wait for Resources](#wait-for-resources)
    * [Component Build](#component-build)
    * [Composite Build](#composite-build)
* [Support](#support)
* [Testing](#testing)
* [Contributing](#contributing)

--------------

## Description

This Java library is a wrapper for the ProfitBricks REST API. All API operations are performed over SSL and are authenticated using your ProfitBricks portal credentials. The API can be accessed within an instance running in ProfitBricks or directly over the Internet from any application that can send an HTTPS request and receive an HTTPS response.

This guide will show you how to programmatically perform common management tasks using the ProfitBricks SDK for Java.

## Getting Started

* ProfitBricks account
* Java
* [Apache Maven](https://maven.apache.org/)

Before you begin you will need to have [signed up](https://www.profitbricks.com/signup) for a ProfitBricks account. The credentials you set up during sign-up will be used to authenticate against the API.

Apache Maven must also be installed. Please review the official [Apache Maven installation documentation](https://maven.apache.org/install.html) for details on the installation.

## Installation

The official ProfitBricks Java library is available from the ProfitBricks GitHub account found [here](https://github.com/profitbricks/profitbricks-sdk-Java). You can download the latest stable version by cloning the repository and then adding the project to your solution.

After the SDK is downloaded:

1. Maven will run live tests before installing. Therefore, it is necessary set the following environment variables:

    * `PROFITBRICKS_USERNAME`
    * `PROFITBRICKS_PASSWORD`

    If you wish to override default CloudAPI url you can do it by setting the following environment variable:

    * `PROFITBRICKS_API_URL`

2. Now the SDK can be installed.

        mvn install

    Maven will install the package into the local repository and become available as a dependency to local projects. The `mvn install` command will run live unit tests which can take several minutes to complete. The `-DskipTests=true` parameter can be used to skip these tests:

        mvn install -DskipTests=true

3. Now the ProfitBricks SDK for Java can be added to your `pom.xml` `<dependencies>` section.

        <dependency>
            <groupId>com.profitbricks.rest.client</groupId>
            <artifactId>profitbricks-sdk-java</artifactId>
            <version>4.0.0</version>
        </dependency>

## Usage

### Authentication

First you need to instantiate the ProfitBricks API and pass the ProfitBricks account credentials:

    ProfitbricksApi profitbricksApi = new ProfitbricksApi();
    profitbricksApi.setCredentials("username", "password");

If you have set the `PROFITBRICKS_USERNAME` and `PROFITBRICKS_PASSWORD` environment variables, then you can exclude the `setCredentials` function. The credentials will be inherited from the environment variables. The Cloud API URL can also be overridden with the `PROFITBRICKS_API_URL` environment variable.

List all data centers:

    DataCenters datacenters = profitbricksApi.getDataCenter().getAllDataCenters();

This will list all data centers you have under your account.

### How to: Create a Data Center

ProfitBricks introduces the concept of virtual data centers. These are logically separated from one another and allow you to have a self-contained environment for all servers, volumes, networking, snapshots, etc. This gives you the same experience as if you were running your own physical data center.

You are required to create a data center before you can create any further objects. Think of the data center as a bucket in which all objects (such as servers and volumes) are stored.

This code example shows how to programmatically create a data center:

    DataCenter datacenter = new DataCenter();

    datacenter.getProperties().setName("SDK Test Data Center");
    datacenter.getProperties().setLocation("us/las");
    datacenter.getProperties().setDescription("SDK test data center description");

    DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);

### How to: Delete a Data Center

Use caution when deleting a data center. Deleting a data center will **destroy** all objects contained within that data center -- including all servers, volumes, snapshots, etc. **When the objects are deleted** they **cannot be recovered**.

This example deletes the data center created above:

    profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);

### How To: Create Data Center with Multiple Resources

The ProfitBricks SDK for Java allows a single request to create multiple nested resources.

This example will create a composite data center with an associated server, NIC, and volume:

    DataCenter datacenter = new DataCenter();
    datacenter.getProperties().setName("SDK Test Data Center");
    datacenter.getProperties().setLocation("us/las");
    datacenter.getProperties().setDescription("Java SDK test description");

    // Add a server
    Server server = new Server();

    server.getProperties().setName("SDK Test Server");
    server.getProperties().setCores(2);
    server.getProperties().setRam(4096);

    // Add a volume to the server
    Volume volume = new Volume();
    volume.getProperties().setName("SDK Test Volume");
    volume.getProperties().setSize(10);
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
    Nic nic = new Nic();
    nic.getProperties().setName("SDK Test Nic");
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

    DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);

### How to: Create a Server

This example creates a server and assigns it an OS, cores, and memory. We urge you to refer to the [documentation](https://devops.profitbricks.com/api/rest/) to see the complete list attributes available.

    Server server = new Server();
    server.getProperties().setName("SDK Test Server");
    server.getProperties().setRam(4096);
    server.getProperties().setCores(2);
    server.getProperties().setCpuFamily("AMD_OPTERON");

    Server newServer = profitbricksApi.getServer().createServer(dataCenterId, server);

One of the unique features of the ProfitBricks platform is that it allows you to define your own settings for cores, memory, and disk size without being tied to a particular instance size.  

### How to: Update Cores and Memory

ProfitBricks allows users to dynamically update cores and memory independently of each other. This means that you do not have to upgrade to the larger size in order to increase memory. You can simply increase the instance's memory, which keeps your costs in line with your resource needs.

This example updates cores and memory:

    Server.Properties object = new Server().new Properties();
    object.setName("SDK New Server Name");
    object.setRam(1024);
    object.setCores(4);

    Server updatedServer = profitbricksApi.getServer().updateServer(dataCenterId, serverId, object);

### How to: Attach and Detach a Volume

ProfitBricks allows for the creation of multiple storage volumes. You can attach and detach these on the fly. This is helpful in scenarios such as attaching a failed OS disk to another server for recovery, or moving a volume to another server to bring online.

This example attaches a volume, then detaches it from a server:

    // First we need to create a volume.
    Volume volume = new Volume();
    volume.getProperties().setName("SDK Test Volume");
    volume.getProperties().setSize(1024);
    volume.getProperties().setLicenceType("LINUX");
    volume.getProperties().setType("HDD");

    Volume newVolume = profitbricksApi.getVolume().createVolume(dataCenterId, volume);

    // Then we are going to attach the new volume to a server.
    Volume attachedVolume = profitbricksApi.getVolume().attachVolume(dataCenterId, serverId, volumeId);

    // Here we are going to detach it from the server.
    profitbricksApi.getVolume().detachVolume(dataCenterId, serverId, volumeId);

### How to: List Data Centers, Servers, and Volumes

You can pull various resource lists from your data centers using the SDK for Java. The three most common resources are data centers, servers, and volumes.

This example retrieves these three list types:

    DataCenters datacenters = profitbricksApi.getDataCenter().getAllDataCenters();

    Servers servers = profitbricksApi.getServer().getAllServers(dataCenterId);

    Volumes volumes = profitbricksApi.getVolume().getAllVolumes(dataCenterId);

### How to: Create Network Interfaces

The ProfitBricks platform supports adding multiple NICs to a server. These NICs can be used to create different, segmented networks on the platform.

This example adds a second NIC to an existing server:

    Nic nic = new Nic();

    nic.getProperties().setName("SDK Test Nic");
    nic.getProperties().setLan("1");

    nic.getEntities().setFirewallrules(null);

    Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, serverId, nic);

Note: This function will result in the server being restarted.

## Reference  

### Virtual Data Centers

Virtual Data Centers are the foundation of the ProfitBricks platform. Virtual Data Centers act as logical containers for all other objects you will be creating, e.g., servers. You can provision as many data centers as you want. Data centers have their own private network and are logically segmented from each other to create isolation.

#### List Data Centers

```
getAllDataCenters()
```
---

#### Retrieve a Data Center

**Request Arguments**

| Name | Type | Description | Required |
|---|---|---|---|
| id | string | The ID of the data center. | Yes |

```
getDataCenter(String id)
```
---

#### Create a Data Center

**Request Arguments**

| Name | Required |Type | Description |
|---|---|---|---|
| name | **yes** | string | The name of the data center. |
| location | **yes** |string | The physical location where the data center will be created. This will be where all of your servers live. |
| description | no | string | A description for the data center, e.g. staging, production. |

**Supported Locations**

| ID | Country | City |
|---|---|---|
| us/las | United States | Las Vegas |
| us/ewr  | United States | Newark |
| de/fra | Germany | Frankfurt |
| de/fkb | Germany | Karlsruhe |

```
createDataCenter(DataCenter datacenter)
```

**NOTES**

* The value for `name` cannot contain the characters: (@, /, , |, ??, ?).
* You cannot change a data center's `location` once it has been provisioned.

---

#### Update a Data Center

After retrieving a data center, you can change it's properties and call the `update` method:

```
updateDataCenter(String id, DataCenter.Properties object)
```

**Request Arguments**

| Name | Required | Type | Description |
| --- | --- | --- | --- |
| Properties.name | no | string | The new name of the data center. |
| Properties.description | no | string | The new description of the data center. |
---

#### Delete a Data Center

This will remove all objects within the data center and remove the data center object itself.

**NOTE**: This is a highly destructive operation which should be used with extreme caution.

```
deleteDataCenter(String id)
```

---

### Servers

#### List Servers

You can retrieve a list of all servers within a data center.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** |string | The ID of the data center. |

```
getAllServers(String dataCenterId)
```

---

#### Retrieve a Server

Returns information about a server such as its configuration, provisioning status, etc.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** |string | The ID of the server. |

```
getServer(String dataCenterId, String serverId)
```

---

#### Create a Server

Creates a server within an existing data center. You can configure additional properties such as specifying a boot volume and connecting the server to an existing LAN.

**Request Arguments**

| Name | Required |Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| name | **yes** | string | The hostname of the server. |
| cores | **yes** | int | The total number of cores for the server. |
| ram | **yes** |int | The amount of memory for the server in MB, e.g. 2048. Size must be specified in multiples of 256 MB with a minimum of 256 MB; however, if you set ramHotPlug to TRUE then you must use a minimum of 1024 MB. |
| availabilityZone | no | string | The availability zone in which the server should exist. |
| licenceType | no | string | Sets the OS type of the server. If undefined the OS type will be inherited from the boot image or boot volume. |
| bootVolume | no | string | Reference to a Volume used for booting. If not ?null? then bootCdrom has to be ?null?. |
| bootCdrom | no | string | Reference to a CD-ROM used for booting. If not 'null' then bootVolume has to be 'null'. |
| cpuFamily | no | string | Sets the CPU type. "AMD_OPTERON" or "INTEL_XEON". Defaults to "AMD_OPTERON". |

**Licence Types**

| Licence Type | Description |
|---|---|
| WINDOWS | You must specify this if you are using your own, custom Windows image due to Microsoft's licensing terms. |
| WINDOWS2016 | Use this for the Microsoft Windows Server 2016 operating system. |
| LINUX | |
| UNKNOWN | If you are using an image uploaded to your account, your OS Type will inherit as UNKNOWN. |

**Availability Zones**

| Availability Zone | Description |
|---|---|
| AUTO | Automatically selected zone |
| ZONE_1 | Zone 1 |
| ZONE_2 | Zone 2 |
| ZONE_3 | Zone 3 |

```
createServer(String dataCenterId, Server server)
```

---

#### Update a Server

Performs updates to the attributes of a server.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** |string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| server.name | no | string | The name of the server. |
| server.cores | no | int | The number of cores for the server. |
| server.ram | no | int | The amount of memory in the server. |
| server.availabilityZone | no | string | The new availability zone for the server. |
| server.licenceType | no | string | The licence type for the server. |
| server.bootVolume | no | string | Reference to a Volume used for booting. If not ?null? then bootCdrom has to be ?null? |
| server.bootCdrom | no | string | Reference to a CD-ROM used for booting. If not 'null' then bootVolume has to be 'null'. |

After retrieving a server, either by getting it by ID, or as a create response object, you can change its properties and call the `update` method:

```
updateServer(String dataCenterId, String serverId, Server.Properties server)
```

---

#### Delete a Server

This will remove a server from a data center. NOTE: This will not automatically remove the storage volume(s) attached to a server. A separate API call is required to perform that action.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |

After retrieving a server, either by getting it by ID, or as a create response object, you can call the `delete` method directly on the object:

```
deleteServer(String dataCenterId, String serverId)
```

---

#### Reboot a Server

Forces a hard reboot of the server. Do not use this method if you want to gracefully reboot the machine. This is the equivalent of powering off the machine and turning it back on.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |

After retrieving a server, either by getting it by ID, or as a create response object, you can call the `reboot` method directly on the object:

```
rebootServer(String dataCenterId, String serverId)
```

---

#### Start a Server

Starts a server. If the server's public IP address was deallocated, a new IP address will be assigned.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |

After retrieving a server, either by getting it by ID, or as a create response object, you can call the `start` method directly on the object:

```
startServer(String dataCenterId, String serverId)
```

---

#### Stop a Server

Stops a server. The machine will be forcefully powered off, billing will stop, and if a public IP address is allocated, it will be deallocated.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |

After retrieving a server, either by getting it by ID, or as a create response object, you can call the `stop` method directly on the object:

```
stopServer(String dataCenterId, String serverId)
```

---

#### Attach a CD-ROM

Attaches a CD-ROM to the server.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| imageId | **yes** | string | The ID of a ProfitBricks image of type CDROM. |

After retrieving a server, either by getting it by ID, or as a create response object, you can call the `attach_cdrom` method directly on the object:

```
attachCDRom(String dataCenterId, String serverId, String imageId)
```

---

#### Detach a CD-ROM

Detaches a CD-ROM from the server. Depending on the volume's "hot_unplug" settings, this may result in the server being rebooted.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| cdromID | **yes** | string | The ID of the attached CDROM. |

After retrieving a server, you can call the `detach_cdrom` method directly on the object:

```
detachCDRom(String dataCenterId, String serverId, String cdromId)
```

---

#### List attached CD-ROMs

Lists CD-ROMs that are attached to the server

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |


```
ligetAllAttachedCDRoms(String dataCenterId, String serverId)
```

---

#### Get attached CD-ROM

Retrieves a CD-ROM that is attached to the server

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| cdromID | **yes** | string | The ID of the attached CD-ROM. |


```
ligetAllAttachedCDRoms(String dataCenterId, String serverId)
```


---
### Volumes

#### List Volumes

Retrieves a list of volumes within the data center. If you want to retrieve a list of volumes attached to a server you can pass the `serverId` parameter as below.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |

```
getAllVolumes(String dataCenterId)
getAllVolumes(String dataCenterId, String serverId)
```

---

#### Get a Volume

Retrieves the attributes of a given volume.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| volumeId | **yes** | string | The ID of the volume. |

```
getVolume(String dataCenterId, String volumeId)
```

---

#### Create a Volume

Creates a volume within the data center.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| size | **yes** | int | The size of the volume in GB. |
| type | **yes** | string | The volume type, HDD or SSD. |
| image | **yes*** | string | The image or snapshot ID. |
| licenceType | **yes*** | string | The licence type of the volume. Options: LINUX, WINDOWS, UNKNOWN, OTHER |
| imagePassword | **yes**** | string | One-time password is set on the Image for the appropriate account. This field may only be set in creation requests. When reading, it always returns null. Password has to contain 8-50 characters. Only these characters are allowed: [abcdefghjkmnpqrstuvxABCDEFGHJKLMNPQRSTUVX23456789] |
| sshKeys | **yes**** | string | SSH keys to allow access to the volume via SSH |
| name | no | string | The name of the volume. |  
| availabilityZone | no | string | The storage availability zone assigned to the volume. Valid values: AUTO, ZONE_1, ZONE_2, or ZONE_3. This only applies to HDD volumes. Leave blank or set to AUTO when provisioning SSD volumes. |
| bus | no | enum | The bus type of the volume (VIRTIO or IDE). Default: VIRTIO. |

*You will need to provide either the `image` or the `licenceType` parameters. `licenceType` is required, but if `image` is supplied, it is already set and cannot be changed. Similarly either the `imagePassword` or `sshKeys` parameters need to be supplied when creating a volume. We recommend setting a valid value for `imagePassword` even when using `sshKeys` so that it is possible to authenticate using the remote console feature of the DCD.

**You will need to provide either `imagePassword` or `sshKeys`.

```
createVolume(String dataCenterId, Volume volume)
```

---

#### Update a Volume

Updates a specified volume.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| volumeId | **yes** | string | The ID of the volume. |
| volume.name | no | string | The name of the volume. |
| volume.size | no | int | The size of the volume in GB. |
| volume.bus | no | enum | The bus type of the volume (VIRTIO or IDE). Default: VIRTIO. |

Various attributes on the volume can be updated (either in full or partially) although the following restrictions apply:

* An existing storage volume can be increased. It cannot be decreased.
* The volume size will be increased without reboot if the hot plug settings have been set to `true`.
* The additional capacity is not added to any partition. You will need to partition it after it has been added.
* After you have increased the volume size you cannot decrease the volume size.

Since an existing volume is being modified, none of the request parameters are specifically required, as long as the changes meet the requirements for creating a volume.

After retrieving a volume, you can change its properties and call the `update` method:

```
updateVolume(String dataCenterId, String volumeId, Volume.Properties volume)
```

---

#### Attach a Volume

Attaches a pre-existing storage volume to the server.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| volumeId | **yes** | string | The ID of a storage volume. |

After retrieving a server, either by getting it by ID, or as a create response object, you can call the `attach_volume` method directly on the object:

```
attachVolume(String dataCenterId, String serverId, String volumeId)
```

---

#### Detach a Volume

Detaches a volume from the server. Depending on the volume's "hot_unplug" settings, this may result in the server being rebooted.

This will NOT delete the volume from your data center. You will need to make a separate request to delete a volume.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| volumeId | **yes** | string | The ID of the attached volume. |

After retrieving a server, you can call the `detach_volume` method directly on the object:

```
detachVolume(String dataCenterId, String serverId, String volumeId)
```

---

#### Delete a Volume

Deletes the specified volume. This will result in the volume being removed from your data center. Use this with caution.

After retrieving a volume, either by getting it by ID, or as a create response object, you can call the `delete` method directly on the object:

```
deleteVolume(String dataCenterId, String volumeId)
```

---


### NICs

#### List NICs

Retrieves a list of NICs within the data center.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |

```
getAllNics(String dataCenterId, String serverId)
```

---

#### Get a NIC

Retrieves the attributes of a given NIC.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |

```
getNic(String dataCenterId, String serverId, String nicId)
```

---

#### Create a NIC

Adds a NIC to the target server.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| lan | **yes** | int | The LAN ID the NIC will sit on. If the LAN ID does not exist it will be created. |  
| name | no | string | The name of the NIC. |
| ips | no | string collection | IPs assigned to the NIC. This can be a collection. |
| dhcp | no | bool | Set to FALSE if you wish to disable DHCP on the NIC. Default: TRUE. |
| nat | no | bool | Indicates the private IP address has outbound access to the public internet. |
| firewallActive | no | bool | Once you add a firewall rule this will reflect a true value. |
| firewallrules | no | string collection | A list of firewall rules associated to the NIC represented as a collection. |

```
createNic(String dataCenterId, String serverId, Nic nic)
```

---

#### Update a NIC

Various attributes on the NIC can be updated (either in full or partially) although the following restrictions apply:

* The primary address of a NIC connected to a load balancer can only be changed by changing the IP address of the load balancer.
* You can also add additional reserved, public IP addresses to the NIC.
* The user can specify and assign private IP addresses manually.
* Valid IP addresses for private networks are 10.0.0.0/8, 172.16.0.0/12 or 192.168.0.0/16.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |
| nic.name | no | string | The name of the NIC. |
| nic.ips | no | string collection | IPs assigned to the NIC represented as a collection. |
| nic.dhcp | no | bool | Boolean value that indicates if the NIC is using DHCP or not. |
| nic.lan | no | int | The LAN ID the NIC sits on. |
| nic.nat | no | bool | Indicates the private IP address has outbound access to the public internet. |

After retrieving a NIC, either by getting it by ID, or as a create response object, you can call the `update` method directly on the object:

```
updateNic(String dataCenterId, String serverId, String nicId, Nic.Properties nic)
```

---

#### List Load Balanced NICs

Retrieves a list of NICs associated with the load balancer.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| loadBalancerId | **yes** | string | The ID of the load balancer. |

After retrieving a load balancer, you can call the `getAllBalancedNics` method directly:

```
getAllBalancedNics(String dataCenterId, String loadBalancerId, String serverId)
```

---

#### Get a Load Balanced NIC

Retrieves the attributes of a given load balanced NIC.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| loadBalancerId | **yes** | string | The ID of the load balancer. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the load balancer. |

```
getBalancedNic(String dataCenterId, String loadBalancerId, String serverId, String nicId)
```

---

#### Associate NIC to a Load Balancer

Associates a NIC to a Load Balancer, enabling the NIC to participate in load-balancing.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| loadBalancerId | **yes** | string | The ID of the load balancer. |
| nicId | **yes** | string | The ID of the load balancer. |

After retrieving a load balancer, you can call the `assignNicToLoadBalancer` method :

```
assignNicToLoadBalancer(String dataCenterId, String loadBalancerId, String nicId)
```

---

#### Remove a NIC Association

Removes the association of a NIC with a load balancer.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| loadBalancerId | **yes** | string | The ID of the load balancer. |
| nicId | **yes** | string | The ID of the load balancer. |

After retrieving a load balancer, you can call the `unassignNicFromLoadBalancer` method :

```
unassignNicFromLoadBalancer(String dataCenterId, String loadBalancerId, String nicId)
```

---


#### Delete a NIC

Deletes the specified NIC.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |

After retrieving a NIC, either by getting it by ID, or as a create response object, you can call the `delete` method directly on the object:

```
deleteNic(String dataCenterId, String serverId, String nicId)
```

---

### Firewall Rules

#### List Firewall Rules

Retrieves a list of firewall rules associated with a particular NIC.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |

```
getAllFirewallRules(String dataCenterId, String serverId, String nicId)
```

---

#### Get a Firewall Rule

Retrieves the attributes of a given firewall rule.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |
| firewallRuleId | **yes** | string | The ID of the firewall rule. |

```
getFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId)
```

---

#### Create a Firewall Rule

Adds a firewall rule to the NIC.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |
| protocol | **yes** | string | The protocol for the rule: TCP, UDP, ICMP, ANY. |
| name | no | string | The name of the Firewall Rule. |  
| sourceMac | no | string | Only traffic originating from the respective MAC address is allowed. Valid format: aa:bb:cc:dd:ee:ff. Value null allows all source MAC address. |
| sourceIp | no | string | Only traffic originating from the respective IPv4 address is allowed. Value null allows all source IPs. |
| targetIp | no | string | In case the target NIC has multiple IP addresses, only traffic directed to the respective IP address of the NIC is allowed. Value null allows all target IPs. |
| portRangeStart | no | string | Defines the start range of the allowed port (from 1 to 65534) if protocol TCP or UDP is chosen. Leave portRangeStart and portRangeEnd value null to allow all ports. |
| portRangeEnd | no | string | Defines the end range of the allowed port (from 1 to 65534) if the protocol TCP or UDP is chosen. Leave portRangeStart and portRangeEnd null to allow all ports. |
| icmpType | no | string | Defines the allowed type (from 0 to 254) if the protocol ICMP is chosen. Value null allows all types. |
| icmpCode | no | string | Defines the allowed code (from 0 to 254) if protocol ICMP is chosen. Value null allows all codes. |

```
createFirewallRule(String dataCenterId, String serverId, String nicId, FirewallRule firewallRule)
```

---

#### Update a Firewall Rule

Performs updates to attributes of a firewall rule.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |
| firewallRuleId | **yes** | string | The ID of the firewall rule. |
| firewallRule.name | no | string | The name of the Firewall Rule. |
| firewallRule.sourceMac | no | string | Only traffic originating from the respective MAC address is allowed. Valid format: aa:bb:cc:dd:ee:ff. Value null allows all source MAC address. |
| firewallRule.sourceIp | no | string | Only traffic originating from the respective IPv4 address is allowed. Value null allows all source IPs. |
| firewallRule.targetIp | no | string | In case the target NIC has multiple IP addresses, only traffic directed to the respective IP address of the NIC is allowed. Value null allows all target IPs. |
| firewallRule.portRangeStart | no | string | Defines the start range of the allowed port (from 1 to 65534) if protocol TCP or UDP is chosen. Leave portRangeStart and portRangeEnd value null to allow all ports. |
| firewallRule.portRangeEnd | no | string | Defines the end range of the allowed port (from 1 to 65534) if the protocol TCP or UDP is chosen. Leave portRangeStart and portRangeEnd null to allow all ports. |
| firewallRule.icmpType | no | string | Defines the allowed type (from 0 to 254) if the protocol ICMP is chosen. Value null allows all types. |
| firewallRule.icmpCode | no | string | Defines the allowed code (from 0 to 254) if protocol ICMP is chosen. Value null allows all codes. |

After retrieving a firewall rule, either by getting it by ID, or as a create response object, you can change its properties and call the `update` method:

```
updateFirewWallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId, FirewallRule.Properties firewallRule)
```

---

#### Delete a Firewall Rule

Removes the specific firewall rule.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| serverId | **yes** | string | The ID of the server. |
| nicId | **yes** | string | The ID of the NIC. |
| firewallRuleId | **yes** | string | The ID of the firewall rule. |

After retrieving a firewall rule, either by getting it by ID, or as a create response object, you can call the `delete` method directly on the object:

```
deleteFirewallRule(String dataCenterId, String serverId, String nicId, String firewallRuleId)
```

---

### LANs

#### List LANs

Retrieves a list of LANs within the data center.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |

```
getAllLans(String dataCenterId)
```

---

#### Create a LAN

Creates a LAN within a data center.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| name | no | string | The name of your LAN. |
| isPublic | no | bool | Boolean indicating if the LAN faces the public Internet or not. |
| nics | no | string collection | A collection of NICs associated with the LAN. |

```
createLan(String dataCenterId, Lan lan)
```

---

#### Get a LAN

Retrieves the attributes of a given LAN.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| lanId | **yes** | string | The ID of the LAN. |

```
getLan(String dataCenterId, String lanId)
```

---

#### Update a LAN

Performs updates to the attributes of a LAN.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| lanId | **yes** | string | The ID of the LAN. |
| isPublic | no | bool | Boolean indicating if the LAN faces the public Internet or not. |

After retrieving a LAN, you can change its properties and call the `updateLan` method:

```
updateLan(String dataCenterId, String lanId, Boolean isPublic)
```

---

#### Delete a LAN

Deletes the specified LAN.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| lanId | **yes** | string | The ID of the LAN. |

After retrieving a LAN, you can call the `deleteLan` method directly on the object:

```
deleteLan(String dataCenterId, String lanId)
```

### Images

#### List Images

Retrieves a list of images.

```
getAllImages()
```

---

#### Get an Image

Retrieves the attributes of a specific image.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| imageId | **yes** | string | The ID of the image. |

```
getImage(String imageId)
```

#### Delete an Image

Deletes a specific image.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| imageId | **yes** | string | The ID of the image. |

```
deleteImage(String imageId)
```

#### Update an Image

Updates a specific image.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| imageId | **yes** | string | The ID of the image. |

```
updateImage(String imageId, Image.Properties object)
```

---

### Load Balancers

#### List Load Balancers

Retrieves a list of load balancers within the data center.

| Name | Required | Type | Description |
|---|---|---|---|
| datacenter_id | **yes** | string | The ID of the data center. |

```
getAllLoadBalancers(String dataCenterId)
```

---

#### Get a Load Balancer

Retrieves the attributes of a given load balancer.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| loadBalancerId | **yes** | string | The ID of the load balancer. |

```
getLoadBalancer(String dataCenterId, String loadBalancerId)
```

---

#### Create a Load Balancer

Creates a load balancer within the data center. Load balancers can be used for traffic on either public or private IP addresses.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| name | **yes** | string | The name of the load balancer. |
| ip | no | string | IPv4 address of the load balancer. All attached NICs will inherit this IP address. |
| dhcp | no | bool | Indicates if the load balancer will reserve an IP address using DHCP. |
| balancednics | no | string collection | List of NICs taking part in load-balancing. All balanced NICs inherit the IP address of the load balancer. |

```
createLoadBalancer(String dataCenterId, LoadBalancer loadBalancer)
```

---

#### Update a Load Balancer

Performs updates to attributes of a load balancer.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| loadBalancerId | **yes** | string | The ID of the load balancer. |
| loadBalancer.name | no | string | The name of the load balancer. |
| loadBalancer.ip | no | string | The IP address of the load balancer. |
| loadBalancer.dhcp | no | bool | Indicates if the load balancer will reserve an IP address using DHCP. |

After retrieving a load balancer, you can change its properties and call the `updateLoadBalancer` method:

```
updateLoadBalancer(String dataCenterId, String loadBalancerId, LoadBalancer.Properties loadBalancer)
```

---

#### Delete a Load Balancer

Deletes the specified load balancer.

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the data center. |
| loadBalancerId | **yes** | string | The ID of the load balancer. |

After retrieving a load balancer, you can call the `deleteLoadBalaner` method directly on the object:

```
deleteLoadBalaner(String dataCenterId, String loadBalancerId)
```

---

### IP Blocks

#### List IP Blocks

Retrieves a list of IP address blocks.

```
getAllIPBlocks()
```

---

#### Get an IP Block

Retrieves the attributes of a specific IP Block.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| ipBlockId | **yes** | string | The ID of the IP block. |

```
getIPBlock(String ipBlockId)
```

---

#### Create an IP Block

Creates an IP block.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| ipBlock.location | **yes** | string | This must be one of the following locations: us/las, de/fra, de/fkb. |
| ipBlock.size | **yes** | int | The size of the IP block you want. |
| ipBlock.name | no | string | A descriptive name for the IP block |

```
createIPBlock(IPBlock ipBlock)
```

---

#### Delete an IP Block

Deletes the specified IP Block.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| ipBlockId | **yes** | string | The ID of the IP block. |

After retrieving an IP block, either by getting it by ID, you can call the `deleteIPBlock` method directly on the object:

```
deleteIPBlock(String ipBlockId)
```
---

### Snapshots

#### List Snapshots

Retrieves a list of all snapshots.

```
getAllSnapshots()
```

---

#### Get a Snapshot

Retrieves the attributes of a specific snapshot.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| snapshotId | **yes** | string | The ID of the snapshot. |

```
getSnapshot(String snapshotId)
```

---

#### Update a Snapshot

Performs updates to attributes of a snapshot.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the snapshot. |
| snapshotId | **yes** | string | The ID of the snapshot. |
| name | no | string | The name of the snapshot. |
| description | no | string | The description of the snapshot. |
| cpuHotPlug | no | bool | This volume is capable of CPU hot plug (no reboot required) |
| cpuHotUnplug | no | bool | This volume is capable of CPU hot unplug (no reboot required) |
| ramHotPlug | no | bool | This volume is capable of memory hot plug (no reboot required) |
| ramHotUnplug | no | bool | This volume is capable of memory hot unplug (no reboot required) |
| nicHotPlug | no | bool | This volume is capable of NIC hot plug (no reboot required) |
| nicHotUnplug | no | bool | This volume is capable of NIC hot unplug (no reboot required) |
| discVirtioHotPlug | no | bool | This volume is capable of Virt-IO drive hot plug (no reboot required) |
| discVirtioHotUnplug | no | bool | This volume is capable of Virt-IO drive hot unplug (no reboot required) |
| discScsiHotPlug | no | bool | This volume is capable of SCSI drive hot plug (no reboot required) |
| discScsiHotUnplug | no | bool | This volume is capable of SCSI drive hot unplug (no reboot required) |
| licenceType | no | string | The snapshot's licence type: LINUX, WINDOWS, or UNKNOWN. |

After retrieving a snapshot, you can change its properties and call the `updateSnapshot` method:

```
updateSnapshot(String dataCenterId, String snapshotId, Snapshot.Properties snapshot)
```

---

#### Create a Volume Snapshot

Creates a snapshot of a volume within the data center. You can use a snapshot to create a new storage volume or to restore a storage volume.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the datacenter. |
| volumeId | **yes** | string | The ID of the volume. |
| name |  no | string | The name of the snapshot. |
| description | no | string | The description of the snapshot. |

After retrieving a volume, you can call the `createSnapshot` method directly on the object:

```
createSnapshot(dataCenterId, volumeId, description);
```

---


#### Restore a Volume Snapshot

Restores a snapshot onto a volume. A snapshot is created as an image which can be used to create new volumes or to restore an existing volume.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| dataCenterId | **yes** | string | The ID of the datacenter. |
| volumeId | **yes** | string | The ID of the volume. |
| snapshotId | **yes** | string | The ID of the snapshot. |

After retrieving a volume, either by getting it by ID, or as a create response object, you can call the `restoreSnapshot` method directly on the object:

```
restoreSnapshot(String dataCenterId, String volumeId, String snapshotId)
```

---

#### Delete a Snapshot

Deletes the specified snapshot.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| snapshotId | **yes** | string | The ID of the snapshot. |

After retrieving a snapshot, you can call the `deleteSnapshot` method directly on the object:

```
deleteSnapshot(String snapshotId)
```

---

### Requests

#### Get a Request status

Retrieves the status of a specific request.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| url | **yes** | string | The ID of the request. |

```
getRequestStatus(String url)
```


#### Get a Request

Retrieves the attributes of a specific request.

**Request Arguments**

| Name | Required | Type | Description |
|---|---|---|---|
| requestId | **yes** | string | The ID of the request. |

```
getRequest(String url)
```

#### List Requests

Retrieves a list of requests.

```
listRequests()
```

---

### Locations

#### List Locations

Locations represent regions where you can provision your Virtual Data Centers.

```
getAllLocations()
```

---

#### Get a Location

Retrieves the attributes of a given location.

**Request Arguments**

| Name | Required | Type | Description |
| --- | --- | --- | --- |
| id | **yes** | string | The unique identifier consisting of country/city. |

```
getLocation(String id)
```

---

### Groups

#### List Groups

Retrieves a list of all groups.


    getAllGroups

---

#### Get a Group

Retrieves the attributes of a given group.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |


    getGroup(String groupId)

---

#### Create a Group

Creates a new group and set group privileges.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| name | **yes** | string | The ID of the group. |
| createDatacenter | no | bool | Indicates if the group is allowed to create virtual data centers. |
| createSnapshot | no | bool | Indicates if the group is allowed to create snapshots. |
| reserveIp | no | bool | Indicates if the group is allowed to reserve IP addresses. |
| accessActivityLog | no | bool | Indicates if the group is allowed to access activity log. |

    createGroup(Group group)

---

#### Update a Group

Updates a group's name or privileges.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |
| group.name | no | string | The ID of the group. |
| group.createDatacenter | no | bool | Indicates if the group is allowed to create virtual data centers. |
| group.createSnapshot | no | bool | Indicates if the group is allowed to create snapshots. |
| group.reserveIp | no | bool | Indicates if the group is allowed to reserve IP addresses. |
| group.accessActivityLog | no | bool | Indicates if the group is allowed to access activity log. |

      updateGroup(String groupId, Group.Properties group)

---

#### Delete a Group

Deletes the specified group.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |

      deleteGroup(String groupId)

---

### Shares

#### List Shares

Retrieves a list of all shares though a group.

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |

    getAllShares(String groupId)

---

#### Get a Share

Retrieves a specific resource share available to a group.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |
| resourceId | **yes** | string | The ID of the resource.

    getShare(String groupId, String resourceId);

---

#### Add a Share

Shares a resource through a group.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |
| resourceId | **yes** | string | The ID of the resource. |
| editPrivilege | no | string | Indicates that the group has permission to edit privileges on the resource. |
| sharePrivilege | no | string | Indicates that the group has permission to share the resource. |

    createShare(String groupId, String resourceId, Share share)

---

#### Update a Share

Updates the permissions of a group for a resource share.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |
| resourceId | **yes** | string | The ID of the resource. |
| share.editPrivilege | no | string | Indicates that the group has permission to edit privileges on the resource. |
| share.sharePrivilege | no | string | Indicates that the group has permission to share the resource. |

    createShare(String groupId, String resourceId, Share.Properties share)

---

#### Delete a Share

Removes a resource share from a group.

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |
| resourceId | **yes** | string | The ID of the resource. |

    deleteShare(String groupId, String resourceId)

---

### Users

#### List Users

Retrieves a list of all users.

    getAllUsers()

---

#### Get a User

Retrieves a single user.

| Name | Required | Type | Description |
|---|:-:|---|---|
| userId | **yes** | string | The ID of the user. |

    getUser(String userId)

---

#### Create a User

Creates a new user.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| firstname | **yes** | string | A name for the user. |
| lastname | **yes**  | bool | A name for the user. |
| email | **yes**  | bool | An e-mail address for the user. |
| password | **yes**  | bool | A password for the user. |
| administrator | no | bool | Assigns the user have administrative rights. |
| forceSecAuth | no | bool | Indicates if secure (two-factor) authentication should be forced for the user. |

    createUser(User user)

---

#### Update a User

Updates an existing user.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| userId | **yes** | string | The ID of the user. |
| user.firstname | **yes** | string | A name for the user. |
| user.lastname | **yes**  | bool | A name for the user. |
| user.email | **yes**  | bool | An e-mail address for the user. |
| user.administrator | **yes** | bool | Assigns the user have administrative rights. |
| user.forceSecAuth | **yes** | bool | Indicates if secure (two-factor) authentication should be forced for the user. |

    updateUser(String userId,User.Properties user)

---

#### Delete a User

Removes a user.

| Name | Required | Type | Description |
|---|:-:|---|---|
| userId | **yes** | string | The ID of the user. |

    deleteUser(String userId)

---

#### List Users in a Group

Retrieves a list of all users that are members of a particular group.

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes**| string | The ID of the group. |

    getAllGroupUsers(String groupId)

---

#### Add User to Group

Adds an existing user to a group.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |
| userId | **yes** | string | The ID of the user. |

    addUserToGroup(String groupId , String userId)

---

#### Remove User from a Group

Removes a user from a group.

| Name | Required | Type | Description |
|---|:-:|---|---|
| groupId | **yes** | string | The ID of the group. |
| userId | **yes** | string | The ID of the user. |

    removeUserFromGroup(String groupId,String userId)

---

### Resources

#### List Resources

Retrieves a list of all resources. Alternatively, Retrieves all resources of a particular type.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| resourceType | no | string | The resource type: `datacenter`, `image`, `snapshot` or `ipblock`. |

    getAllResources()

    getAllByType(Resource.ResourceType resourceType)

---

#### Get a Resource

Retrieves a single resource of a particular type.

The following table describes the request arguments:

| Name | Required | Type | Description |
|---|:-:|---|---|
| resourceType | **yes** | string | The resource type: `datacenter`, `image`, `snapshot` or `ipblock`. |
| resourceId | **yes** | string | The ID of the resource. |

    getByType(Resource.ResourceType resourceType, String resourceId)

---

### Contract Resources

#### List Contract Resources

Retrieves information about the resource limits for a particular contract and the current resource usage.

    getContract()

---

## Examples

The examples in this section make two assumptions:

1. The ProfitBricks account credentials will be set through environment variables:

        export PROFITBRICKS_USERNAME=username
        export PROFITBRICKS_PASSWORD=password

2. The standard Maven project directory structure will be used with the example classes.

        my-app
        |-- pom.xml
        `-- src
            |-- main
            |   `-- java
            |       `-- com
            |           `-- company
            |               `-- app
            |                   `-- Common.java
            |                    -- ComponentBuild.java
            |                    -- CompositeBuild.java
            `-- test
                `-- java
                    `-- com
                        `-- company
                            `-- app
                                `-- ...

### POM

`pom.xml`

    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.company.app</groupId>
      <artifactId>test</artifactId>
      <packaging>jar</packaging>
      <version>1.0-SNAPSHOT</version>
      <name>app</name>
      <url>http://maven.apache.org</url>
      <dependencies>
        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>3.8.1</version>
          <scope>test</scope>
        </dependency>
        <dependency>
           <groupId>com.profitbricks.rest.client</groupId>
           <artifactId>profitbricks-sdk-java</artifactId>
           <version>4.0.0</version>
        </dependency>
      </dependencies>
    </project>

### Wait for Resources

ProfitBricks allows servers to be created with individual, customizable components including NICs and volumes. A wait method is necessary to provision components that depend on each other.

This is an example of a `waitTillProvisioned` method which can be used between dependent requests:

`src/main/java/com/company/app/Common.java`

    package com.company.app;

    import com.profitbricks.sdk.ProfitbricksApi;
    import com.profitbricks.rest.domain.RequestStatus;
    import com.profitbricks.rest.client.RestClientException;

    import java.io.IOException;
    import java.lang.Exception;
    import java.util.concurrent.TimeUnit;

    public class Common
    {
        public static void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException, IOException, Exception {
            ProfitbricksApi profitbricksApi = new ProfitbricksApi();

            int counter = 120;
            for (int i = 0; i < counter; i++) {
                RequestStatus request = profitbricksApi.getRequest().getRequestStatus(requestId);
                TimeUnit.SECONDS.sleep(1);
                if (request.getMetadata().getStatus().equals("DONE")) {
                    break;
                }
                if (request.getMetadata().getStatus().equals("FAILED")) {
                    throw new IOException("The request execution has failed with following message: " + request.getMetadata().getMessage());
                }
            }
        }
    }

### Component Build

Using the above `waitTillProvisioned` method and individual components, a fully operational public server can be built using this example:

`src/main/java/com/company/app/ComponentBuild.java`

    package com.company.app;

    import static com.company.app.Common.waitTillProvisioned;
    import com.profitbricks.rest.domain.*;
    import com.profitbricks.sdk.ProfitbricksApi;
    import com.profitbricks.rest.client.RestClientException;

    import java.util.ArrayList;
    import java.util.List;
    import java.lang.Exception;

    public class ComponentBuild
    {
        public static void main( String[] args ) throws RestClientException, Exception
        {
            ProfitbricksApi profitbricksApi = new ProfitbricksApi();

            // Add a data center
            DataCenter datacenter = new DataCenter();
            datacenter.getProperties().setName("Java SDK Data Center");
            datacenter.getProperties().setLocation("us/las");
            datacenter.getProperties().setDescription("Java SDK data center");
            DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);

            waitTillProvisioned(newDatacenter.getRequestId());
            String dataCenterId = newDatacenter.getId();

            // Add a LAN
            Lan lan = new Lan();
            lan.getProperties().setName("Public LAN");
            lan.getProperties().setIsPublic(Boolean.TRUE);
            Lan newLan = profitbricksApi.getLan().createLan(dataCenterId, lan);

            waitTillProvisioned(newLan.getRequestId());
            String lanId = newLan.getId();

            // Add a server
            Server server = new Server();
            server.getProperties().setName("Java SDK Server");
            server.getProperties().setCores(2);
            server.getProperties().setRam(4096);
            Server newServer = profitbricksApi.getServer().createServer(dataCenterId, server);

            waitTillProvisioned(newServer.getRequestId());
            String serverId = newServer.getId();

            // Add a NIC to the server
            Nic nic = new Nic();
            nic.getProperties().setName("Public NIC");
            nic.getProperties().setLan(lanId);
            nic.getProperties().setNat(Boolean.FALSE);
            Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, serverId, nic);

            waitTillProvisioned(newNic.getRequestId());

            // Add a volume to the server
            List<String> sshkeys = new ArrayList<String>();
            sshkeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCoLVLHON4BSK3D8L4H79aFo...");

            Volume volume = new Volume();
            volume.getProperties().setName("System Volume");
            volume.getProperties().setSize(10);
            volume.getProperties().setImage("0d4f97f0-1689-11e7-97ce-525400f64d8d");
            volume.getProperties().setType("HDD");
            volume.getProperties().setSshKeys(sshkeys);
            Volume newVolume = profitbricksApi.getVolume().createVolume(dataCenterId, volume);

            waitTillProvisioned(newVolume.getRequestId());
            String volumeId = newVolume.getId();

            // Attach volume
            Volume attachedVolume = profitbricksApi.getVolume().attachVolume(dataCenterId, serverId, volumeId);
            waitTillProvisioned(attachedVolume.getRequestId());

            System.out.println(newDatacenter.getId());
            System.out.println(newDatacenter.getProperties().getName());

            System.out.println(newServer.getId());
            System.out.println(newServer.getProperties().getName());
        }
    }

### Composite Build

ProfitBricks also allows servers to be built using a composite request. This example  demonstrates a fully-operational public server built with a single composite request. Note that a second request is necessary to set the provisioned LAN to public.

`src/main/java/com/company/app/CompositeBuild.java`

    package com.company.app;

    import static com.company.app.Common.waitTillProvisioned;
    import com.profitbricks.sdk.ProfitbricksApi;
    import com.profitbricks.rest.domain.*;
    import com.profitbricks.rest.client.RestClientException;

    import java.util.ArrayList;
    import java.util.List;
    import java.lang.Exception;

    public class CompositeBuild
    {
        public static void main( String[] args ) throws RestClientException, Exception
        {
            ProfitbricksApi profitbricksApi = new ProfitbricksApi();

            // Add data center
            DataCenter datacenter = new DataCenter();
            datacenter.getProperties().setName("Java SDK Data Center");
            datacenter.getProperties().setLocation("us/las");
            datacenter.getProperties().setDescription("Java SDK data center");

            // Add a server
            Server server = new Server();
            server.getProperties().setName("Java SDK Server");
            server.getProperties().setCores(2);
            server.getProperties().setRam(4096);

            // Add a volume to the server
            Volume volume = new Volume();
            volume.getProperties().setName("System Volume");
            volume.getProperties().setSize(10);
            volume.getProperties().setImage("0d4f97f0-1689-11e7-97ce-525400f64d8d");
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
            Nic nic = new Nic();
            nic.getProperties().setName("Private NIC");
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

            DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);
            waitTillProvisioned(newDatacenter.getRequestId());
            String dataCenterId = newDatacenter.getId();

            Lan updatedLan = profitbricksApi.getLan().updateLan(dataCenterId, "1", Boolean.TRUE);
            waitTillProvisioned(updatedLan.getRequestId());

            System.out.println(newDatacenter.getId());
            System.out.println(newDatacenter.getProperties().getName());
        }
    }

## Support

You can engage with us on the [ProfitBricks DevOps Central](https://devops.profitbricks.com/) site where we will be happy to answer any questions you might have.

**Additional Resources**

* [ProfitBricks SDK for Java](https://devops.profitbricks.com/libraries/java/) guide.
* [ProfitBricks REST API](https://devops.profitbricks.com/api/rest/) documentation.
* Ask a question or discuss at [ProfitBricks DevOps Central](https://devops.profitbricks.com/community/).
* Report an [issue here](https://github.com/profitbricks/profitbricks-sdk-java/issues).

## Testing

Set these environment variables to run the unit tests:

    export PROFITBRICKS_USERName=username
    export PROFITBRICKS_PASSWORD=password

Maven can then be used to run the tests:

    mvn test

## Contributing

1. Fork the repository (https://github.com/profitbricks/profitbricks-sdk-java/fork)
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create a new Pull Request
