/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.helper.key;

import java.util.Objects;

/**
 * @author John Dickerson - 28 Jul 2025
 */
public enum KeyEnum {

    ED25519( "ed25519", null ),
    RSA_2048( "rsa", 2048 ),
    RSA_4096( "rsa", 4096 );

    private final String type;
    private final Integer size;

    KeyEnum( String type, Integer size ) {

        this.type = type;
        this.size = size;
    }


    public String getType() {

        return type;
    }


    public Integer getSize() {

        return size;
    }


    public static KeyEnum from( String type, Integer size ) {

        for ( KeyEnum keyType : KeyEnum.values() ) {

            if ( keyType.type.equals( type ) && Objects.equals( keyType.size, size ) ) {

                return keyType;
            }
        }

        throw new IllegalArgumentException(
                "Invalid type-size combination: " + type + ", " + size );
    }
}
