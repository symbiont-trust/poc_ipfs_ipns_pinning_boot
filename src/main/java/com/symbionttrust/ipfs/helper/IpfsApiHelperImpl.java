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

import org.springframework.stereotype.Component;

import com.symbionttrust.ipfs.helper.key.IpfsKeyHelper;

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

    @Override
    public String addFile( File file ) throws IOException {

        NamedStreamable.FileWrapper wrapper = new NamedStreamable.FileWrapper( file );
        MerkleNode node = ipfs.add( wrapper ).get( 0 );
        return node.hash.toBase58();
    }


    @Override
    public String publishToIpns( String cid ) throws IOException {

        Map<?, ?> result = ipfs.name.publish( Multihash.fromBase58( cid ) );
        return result.get( "Name" ).toString(); // IPNS key (e.g. Qmb123...)
    }


    @Override
    public void pinFile( String cid ) throws IOException {

        ipfs.pin.add( Multihash.fromBase58( cid ) );
    }


    @Override
    public String generateKey( String name, String type, int size ) throws IOException {

        return ipfsKeyHelper.generateKey( name, type, size );
    }


    @Override
    public Map<String, String> listKeys() throws IOException {

        return ipfsKeyHelper.listKeys();
    }


    @Override
    public String publishWithKey( String keyName, String cid ) throws IOException {

        return ipfsKeyHelper.publishWithKey( keyName, cid );
    }


    @Override
    public void removeKey( String keyName ) throws IOException {

        ipfsKeyHelper.removeKey( keyName );
        
    }


    @Override
    public void renameKey( String oldName, String newName ) throws IOException {

        ipfsKeyHelper.renameKey( oldName, newName );
    }

}
