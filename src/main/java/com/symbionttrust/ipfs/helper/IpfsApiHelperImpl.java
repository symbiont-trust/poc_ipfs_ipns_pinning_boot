/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.helper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.symbionttrust.ipfs.helper.key.IpfsKeyHelper;
import com.symbionttrust.ipfs.helper.key.KeyEnum;
import com.symbionttrust.ipfs.helper.key.KeyInfo;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.RequiredArgsConstructor;

/**
 * @author John Dickerson - 26 Jul 2025
 */
@Component
@RequiredArgsConstructor
public class IpfsApiHelperImpl implements IpfsApiHelper {

    private final IPFS ipfs;
    private final IpfsKeyHelper ipfsKeyHelper;

    // Add a file to IPFS and get its ipfs hash
    @Override
    public String addFile( File file ) throws IOException {

        NamedStreamable.FileWrapper wrapper = new NamedStreamable.FileWrapper( file );
        MerkleNode node = ipfs.add( wrapper ).get( 0 );
        return node.hash.toBase58();
    }


    // pin the IPFS resource so it is not ejected from cache when the IPFS node
    // is running low on space.
    @Override
    public void pinFile( String cid ) throws IOException {

        ipfs.pin.add( Multihash.fromBase58( cid ) );
    }


    // Generates a new named keypair (e.g., RSA 2048) and returns its IPNS hash
    @Override
    public String generateKey( String name, KeyEnum keyEnum ) throws IOException {

        return ipfsKeyHelper.generateKey( name, keyEnum );
    }


    // Lists existing named keys and their IPNS hashes
    @Override
    public KeyInfo[] listKeys() throws IOException {

        return ipfsKeyHelper.listKeys();
    }


    public String publishToIpns( String cid ) throws IOException {

        Map<?, ?> result = ipfs.name.publish( Multihash.fromBase58( cid ) );
        return result.get( "Name" ).toString(); // IPNS key (e.g. Qmb123...)
    }

    // Publishes a CID using the specified key name, returning the IPNS name
    // The cid is the hash from adding a file to IPFS
    @Override
    public String publishIPNS( String keyName, String cid ) throws IOException {

        Multihash hash = Multihash.fromBase58( cid );
        Map<?, ?> result = ipfs.name.publish( hash, Optional.of( keyName ) );
        return result.get( "Name" ).toString();
    }


    // Removes a named key by name
    @Override
    public void removeKey( String keyName ) throws IOException {

        ipfsKeyHelper.removeKey( keyName );
        
    }


    // Renames an existing key
    @Override
    public void renameKey( String oldName, String newName ) throws IOException {

        ipfsKeyHelper.renameKey( oldName, newName );
    }
}
