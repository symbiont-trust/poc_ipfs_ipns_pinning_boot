/*
    ================================================================================================
    This code is part of the symbiont-trust software.

    ================================================================================================
    Author : JD
    ================================================================================================
*/
package com.symbionttrust.ipfs.helper.key;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author John Dickerson - 28 Jul 2025
 */
@Component
@RequiredArgsConstructor
public class IpfsKeyHelperImpl implements IpfsKeyHelper {

    private final HttpClient httpClient;

    private static final String BASE_URL = "http://localhost:5001/api/v0";

    private String post( String endpoint, Map<String, String> params ) throws IOException {

        StringBuilder url = new StringBuilder( BASE_URL + endpoint + "?" );

        for ( var entry : params.entrySet() ) {

            url.append( entry.getKey() ).append( "=" ).append( URLEncoder.encode( entry.getValue(),
                    StandardCharsets.UTF_8 ) ).append( "&" );
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri( URI.create( url.toString() ) )
                .POST( HttpRequest.BodyPublishers.noBody() )
                .build();


        try {
            return httpClient.send( request, HttpResponse.BodyHandlers.ofString() ).body();
        }
        catch ( InterruptedException e ) {

            throw new IOException( "Request interrupted", e );
        }
    }


    // Generates a new named keypair (e.g., RSA 2048) and returns its IPNS hash.
    @Override
    public String generateKey( String name, KeyEnum keyType ) throws IOException {

        String type = keyType.getType();
        String size = keyType.getSize().toString();

        var response =
                post( "/key/gen",
                        Map.of( "arg", name, "type", type, "size", String.valueOf( size ) ) );

        return new JSONObject( response ).getString( "Id" );
    }


    // Lists existing named keys and their IPNS hashes.
    @Override
    public KeyInfo[] listKeys() throws IOException {

        var response = post("/key/list", Map.of());
        var keys = new JSONObject(response).getJSONArray("Keys");
        KeyInfo[] result = new KeyInfo[keys.length()];

        for (int i = 0; i < keys.length(); i++) {
            
            JSONObject key = keys.getJSONObject(i);
            
            result[i] = new KeyInfo(
                key.getString("Name"),
                key.getString("Id")
            );
        }

        return result;
    }


    // Publishes a CID using the specified key name, returning the IPNS name.
    /*
    @Override
    public String publishToIpnsWithKey( String keyName, String cid ) throws IOException {
    
        var response = post( "/name/publish", Map.of( "arg", cid, "key", keyName ) );
        return new JSONObject( response ).getString( "Name" );
    }
    */


    // Removes a named key by name.
    @Override
    public void removeKey( String keyName ) throws IOException {

        post( "/key/rm", Map.of( "arg", keyName ) );
    }


    // Renames an existing key
    @Override
    public void renameKey( String oldName, String newName ) throws IOException {

        post( "/key/rename", Map.of( "arg", oldName, "new", newName ) );
    }
}
