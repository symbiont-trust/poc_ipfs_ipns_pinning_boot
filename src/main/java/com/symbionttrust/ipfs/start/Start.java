/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.symbionttrust.ipfs.helper.IpfsApiHelper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * @author John Dickerson - 26 Jul 2025
 */
// @Component
@SpringBootApplication
@ComponentScan( basePackages = "com.symbionttrust.ipfs" )
@RequiredArgsConstructor
public class Start {

    // @Autowired
    private final IpfsApiHelper ipfsHelper;

    @PostConstruct( )
    public void start() {

        //ipfsHelper.
    }


    public static void main( String[] args ) {

        SpringApplication.run( Start.class, args );
    }

}
