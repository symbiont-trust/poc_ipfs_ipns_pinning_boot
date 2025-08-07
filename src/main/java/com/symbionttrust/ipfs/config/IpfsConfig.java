/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.config;

import java.net.http.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.ipfs.api.IPFS;

/**
 * @author John Dickerson - 29 Jul 2025
 */
@Configuration
public class IpfsConfig {

    // Without destroyMethod being set to "" Spring boot calls the shutdown method on JVM exit
    @Bean( destroyMethod = "" )
    IPFS ipfsClient() {

        return new IPFS( "/ip4/127.0.0.1/tcp/5001" );
    }


    @Bean
    HttpClient createHttpClient() {

        return HttpClient.newHttpClient();
    }
}
