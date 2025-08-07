package com.symbionttrust.ipfs.helper;

import java.io.File;
import java.io.IOException;

import com.symbionttrust.ipfs.helper.key.KeyEnum;
import com.symbionttrust.ipfs.helper.key.KeyInfo;

/**
 * @author John Dickerson - 26 Jul 2025
 */
public interface IpfsApiHelper {

    // Add a file to IPFS and get its ipfs hash
    String addFile( File file ) throws IOException;

    // pin the IPFS resource so it is not ejected from cache when the IPFS node
    // is running low on space.
    void pinFile( String cid ) throws IOException;


    // Generates a new named keypair (e.g., RSA 2048) and returns its IPNS hash
    String generateKey( String name, KeyEnum keyEnum ) throws IOException;


    // Lists existing named keys and their IPNS hashes
    KeyInfo[] listKeys() throws IOException;


    String publishToIpns( String cid ) throws IOException;

    // Publishes a CID using the specified key name, returning the IPNS name
    // The cid is the hash from adding a file to IPFS
    String publishIPNS( String keyName, String cid ) throws IOException;


    // Removes a named key by name
    void removeKey( String keyName ) throws IOException;


    // Renames an existing key
    void renameKey( String oldName, String newName ) throws IOException;
}
