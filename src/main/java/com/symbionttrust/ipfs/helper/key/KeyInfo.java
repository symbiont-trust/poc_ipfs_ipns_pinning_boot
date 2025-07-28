/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.helper.key;


/**
 * @author John Dickerson - 28 Jul 2025
 */
public class KeyInfo {

    private final String name;
    private final String id;

    public KeyInfo( String name, String id ) {

        this.name = name;
        this.id = id;
    }


    public String getName() {

        return name;
    }


    public String getId() {

        return id;
    }


    @Override
    public String toString() {

        return "KeyInfo{name='" + name + "', id='" + id + "'}";
    }
}

