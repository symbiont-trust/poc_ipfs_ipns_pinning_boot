/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.helper.key;

import java.io.IOException;

/**
 * @author John Dickerson - 28 Jul 2025
 */
public interface IpfsKeyHelper {

    // Generates a new named keypair (e.g., RSA 2048) and returns its IPNS hash.
    String generateKey( String name, KeyEnum keyType ) throws IOException;


    // Lists existing named keys and their IPNS hashes.
    KeyInfo[] listKeys() throws IOException;


    // Removes a named key by name.
    void removeKey( String keyName ) throws IOException;


    // Renames an existing key
    void renameKey( String oldName, String newName ) throws IOException;
}
