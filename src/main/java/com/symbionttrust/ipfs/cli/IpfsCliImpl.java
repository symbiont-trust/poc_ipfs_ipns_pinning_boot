/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.cli;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.symbionttrust.ipfs.helper.IpfsApiHelper;
import com.symbionttrust.ipfs.helper.key.KeyEnum;
import com.symbionttrust.ipfs.helper.key.KeyInfo;

import lombok.RequiredArgsConstructor;

/**
 * @author John Dickerson - 28 Jul 2025
 */
@Component
@RequiredArgsConstructor
public class IpfsCliImpl implements IpfsCli {

    private final IpfsApiHelper ipfsHelper;

    @Override
    public void run() {

        Thread.getAllStackTraces().keySet().stream()
                .filter( t -> t.getName().contains( "Shutdown" ) )
                .forEach( t -> System.out.println( "Hook: " + t ) );

        Scanner scanner = new Scanner( System.in );

        Boolean keepRunning = true;

        while ( keepRunning ) {

            printMenu();
            System.out.print( "Choose an option: " );
            String input = scanner.nextLine().trim();

            try {
                switch ( input ) {
                    case "1" -> listKeys();
                    case "2" -> generateKey( scanner );
                    case "3" -> publishCID( scanner );
                    case "4" -> addFile( scanner );
                    case "5" -> pinFile( scanner );
                    case "6" -> removeKey( scanner );
                    case "7" -> renameKey( scanner );
                    case "x" -> {
                        System.out.println( "Exiting CLI." );
                        keepRunning = false;
                        // System.exit( 0 );
                    }
                    default -> System.out.println( "Invalid option." );
                }
            }
            catch ( IOException e ) {
                System.err.println( "IO error: " + e.getMessage() );
            }
        }
    }


    private void printMenu() {

        System.out.println( "\n--- IPFS CLI Menu ---" );
        System.out.println( "1. List named keys" );
        System.out.println( "2. Generate key" );
        System.out.println( "3. Publish CID to IPNS" );
        System.out.println( "4. Add file" );
        System.out.println( "5. Pin CID" );
        System.out.println( "6. Remove key" );
        System.out.println( "7. Rename key" );
        System.out.println( "x. Exit" );
    }


    private void listKeys() throws IOException {

        for ( KeyInfo key : ipfsHelper.listKeys() ) {

            System.out.printf( "Key: %s => %s%n", key.getName(), key.getId() );
        }
    }


    private void generateKey( Scanner scanner ) throws IOException {

        System.out.print( "Key name: " );
        String name = scanner.nextLine().trim();
        System.out.print( "Key type (e.g. RSA_2048): " );
        String type = scanner.nextLine().trim();

        try {
            KeyEnum keyEnum = KeyEnum.valueOf( type.toUpperCase() );
            String ipns = ipfsHelper.generateKey( name, keyEnum );
            System.out.println( "Generated IPNS: " + ipns );
        }
        catch ( IllegalArgumentException e ) {

            System.out.println( "Invalid key type." );
        }
    }


    private void publishCID( Scanner scanner ) throws IOException {

        System.out.print( "Key name: " );
        String key = scanner.nextLine().trim();
        System.out.print( "CID to publish: " );
        String cid = scanner.nextLine().trim();
        String ipns = ipfsHelper.publishIPNS( key, cid );
        System.out.println( "Published IPNS: " + ipns );
    }


    private void addFile( Scanner scanner ) throws IOException {

        System.out.print( "File path: " );
        File file = new File( scanner.nextLine().trim() );

        if ( !file.exists() ) {
            System.out.println( "File not found." );
            return;
        }

        String cid = ipfsHelper.addFile( file );
        System.out.println( "File added. CID: " + cid );
    }


    private void pinFile( Scanner scanner ) throws IOException {

        System.out.print( "CID to pin: " );
        ipfsHelper.pinFile( scanner.nextLine().trim() );
        System.out.println( "Pinned." );
    }


    private void removeKey( Scanner scanner ) throws IOException {

        System.out.print( "Key name to remove: " );
        ipfsHelper.removeKey( scanner.nextLine().trim() );
        System.out.println( "Removed." );
    }


    private void renameKey( Scanner scanner ) throws IOException {

        System.out.print( "Old key name: " );
        String oldName = scanner.nextLine().trim();
        System.out.print( "New key name: " );
        String newName = scanner.nextLine().trim();
        ipfsHelper.renameKey( oldName, newName );
        System.out.println( "Renamed." );
    }
}
