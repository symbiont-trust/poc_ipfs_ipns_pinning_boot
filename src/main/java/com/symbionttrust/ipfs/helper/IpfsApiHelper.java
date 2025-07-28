package com.symbionttrust.ipfs.helper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author John Dickerson - 26 Jul 2025
 */
public interface IpfsApiHelper {

    // Add a file to IPFS and get its ipfs hash
    String addFile( File file ) throws IOException;


    // Map the IPFS file hash to an IPNS hash and return that ipns hash
    String publishToIpns( String cid ) throws IOException;


    // pin the IPFS resource so it is not ejected from cache when the IPFS node
    // is running low on space.
    void pinFile( String cid ) throws IOException;


    /** Generates a new named keypair (e.g., RSA 2048) and returns its IPNS hash. */
    String generateKey( String name, String type, int size ) throws IOException;


    /** Lists existing named keys and their IPNS hashes. */
    Map<String, String> listKeys() throws IOException;


    /** Publishes a CID using the specified key name, returning the IPNS name. */
    String publishWithKey( String keyName, String cid ) throws IOException;


    /** Removes a named key by name. */
    void removeKey( String keyName ) throws IOException;


    /** Renames an existing key. */
    void renameKey( String oldName, String newName ) throws IOException;
}
