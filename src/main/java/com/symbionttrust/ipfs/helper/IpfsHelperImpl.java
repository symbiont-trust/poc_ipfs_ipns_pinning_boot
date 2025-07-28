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
public class IpfsHelperImpl implements IpfsHelper {

    private final IPFS ipfs;

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
}
