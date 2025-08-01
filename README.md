# README

## Overview

This example and its docs shows you how to:

* get your own IPFS node running locally
* how to publish to IPFS / IPNS
* how to pin

The idea in the Trust Demo is that we will later publish decentralised Trust profiles to IPFS
and reference the IPNS hash in a solidity smart contract.

This POC is about getting the IPFS / IPNS / pinning side working and documenting how it is done.

When one publishes a file to IPFS one is given a hash. Anyone who has the IPFS hash can use it to
retrieve the file.

If the file is changed and republished the hash changes.  This means that everyone who had the
previous hash cannot retrieve the new file anymore.  For this reason there is a naming service 
called IPNS.  The IPNS hash can be remapped to a different IPFS hash but the IPNS hash does not
change. This means anyone who has the IPNS hash can know they can retrieve the latest version of 
the file.

Let us now consider runing our own ipfs node on the cloud. We can configure our ipfs node to have
a domain name like:

```
my-node.com
```

Then ipns resources can be retrieved like:

```
https://my-node.com/ipns/<ipns-hash>
```

And ipfs resource can be retrieved like:

```
https://my-node.com/ipfs/<ipfs-hash>
```

Now if we want to serve the content given by the ``<ipns-hash>`` without remembering the hash we can do
some domain configuration so the content can be retrieved just by typing in the browser:

```
https://gateway.symbiont-trust.com
```

To do this we need to go to the cname record of the domain symbiont-trust.com and create a mapping 
like:

```
gateway.symbiont-trust.com. IN CNAME my-ipfs-node.com.
```

For some reason we can only use a sub domain in the cname record. We cannot do:

```
symbiont-trust.com. IN CNAME my-ipfs-node.com.
```

However instead of editing the cname record we can edit the A record:

```
symbiont-trust.com. IN A <ip-address-of-my-node.com>
```

Note that we are not allowed to put the domain my-node.com in the A record. However we can add its
IP address.


## Instructions to get your ipfs node up and running

### Installing IPFS Desktop

This is good for playing around when NOT running this app.

For some reason running this example twice has issues on the second time when using the Desktop
version of IPFS. 

Anyways to install it:

Download IPFS Desktop

```
https://docs.ipfs.tech/install/ipfs-desktop/
```

I downloaded it from here:

```
https://github.com/ipfs/ipfs-desktop/releases
```

When you open IPFS Desktop it internally runs:

```
go-ipfs 
```
go-ipfs is an IPFS node written in the Go language

You can also access the node via a browser:

```
http://127.0.0.1:5001/webui
```

### Install command line version

#### On the Mac:

Install IPFS

```
brew install ipfs
```

#### On Windows
@todo
    
    
#### One Unix flavours
@todo


### Basic commands of command line version
Initialize the IPFS repository

```
ipfs init
```

Start the IPFS daemon

```
ipfs daemon
```
Check your node ID:

```
ipfs id
```

View your config:

```
ipfs config show
```

Show connected peers

```
ipfs swarm peers
```

Add a file to IPFS and get its uuid

```
ipfs add myfile.txt
```

Retrieve a file from IPFS

```
ipfs get <CID>
```


## API of java-ipfs-http-client

See:  

```
https://github.com/ipfs-shipyard/java-ipfs-http-client
```

## Instructions:

Import into STS as a maven project

Behind the scenes it will run:

```
mvn install
```

Then find this class:

```
com.symbionttrust.ipfs.start.Start
```

Run it