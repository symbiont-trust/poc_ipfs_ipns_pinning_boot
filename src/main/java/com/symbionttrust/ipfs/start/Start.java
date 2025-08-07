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
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import com.symbionttrust.ipfs.cli.IpfsCli;

import lombok.RequiredArgsConstructor;

/**
 * This class is the entry point. The start method is called by Spring once it has autowired the 
 * app.  The lombok @RequiredArgsConstructor generates a constructor which passes IpfsCku in.
 * <p>
 * At startup it scans the classpath for an implemntation of the IpfsCli interface and injects it.
 * <p>
 * This is equivalent to marking the IpfsCli with @Autowired
 * <p>
 * Since Spring version 4.3, Spring will autowire without the @Autowired annotation if there is a
 * constructor.
 * 
 * @author John Dickerson - 26 Jul 2025
 */
@SpringBootApplication
@ComponentScan( basePackages = "com.symbionttrust.ipfs" )
@RequiredArgsConstructor
public class Start {

    // @RequiredArgsConstructor is creating a constructor and in later versions of Spring it is 
    // injected via constructors even without using the @Autowired annotation
    private final IpfsCli ipfsCli;

    /**
    @PostConstruct( )
    public void start() {
        ipfsCli.run();
    }
    */


    // This is better than using @PostConstruct as otherwise the application only prints it has
    // started when it is ending
    @EventListener( ApplicationReadyEvent.class )
    public void launchCli() {

        new Thread( ipfsCli::run, "IpfsCliThread" ).start();
    }


    public static void main( String[] args ) {

        SpringApplication.run( Start.class, args );
    }
}
