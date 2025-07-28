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

import com.symbionttrust.ipfs.cli.IpfsCli;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * This class is the entry point. The start method is called by Spring once it has autowired the 
 * app.  The lombok @RequiredArgsConstructor generates a constructor which passes IpfsCku in.
 * 
 * At startup it scans the classpath for an implemntation of the IpfsCli interface and injects it.
 * 
 * This is equivalent to marking the IpfsCli with @Autowired
 * 
 * @author John Dickerson - 26 Jul 2025
 */
@SpringBootApplication
@ComponentScan( basePackages = "com.symbionttrust.ipfs" )
@RequiredArgsConstructor
public class Start {

    private final IpfsCli ipfsCli;

    @PostConstruct( )
    public void start() {

        ipfsCli.run();
    }


    public static void main( String[] args ) {

        SpringApplication.run( Start.class, args );
    }
}
