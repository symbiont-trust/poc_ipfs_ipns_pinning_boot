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
import java.util.LinkedHashMap;
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

        HttpClient client = HttpClient.newHttpClient();

        try {
            return client.send( request, HttpResponse.BodyHandlers.ofString() ).body();
        }
        catch ( InterruptedException e ) {
            throw new IOException( "Request interrupted", e );
        }
    }


    @Override
    public String generateKey( String name, String type, int size ) throws IOException {

        var response = post( "/key/gen", Map.of( "arg", name, "type", type, "size", String.valueOf(
                size ) ) );
        return new JSONObject( response ).getString( "Id" );
    }


    @Override
    public Map<String, String> listKeys() throws IOException {

        var response = post( "/key/list", Map.of() );
        var keys = new JSONObject( response ).getJSONArray( "Keys" );
        Map<String, String> result = new LinkedHashMap<>();

        for ( int i = 0; i < keys.length(); i++ ) {
            JSONObject key = keys.getJSONObject( i );
            result.put( key.getString( "Name" ), key.getString( "Id" ) );
        }

        return result;
    }


    @Override
    public String publishWithKey( String keyName, String cid ) throws IOException {

        var response = post( "/name/publish", Map.of( "arg", cid, "key", keyName ) );
        return new JSONObject( response ).getString( "Name" );
    }


    @Override
    public void removeKey( String keyName ) throws IOException {

        post( "/key/rm", Map.of( "arg", keyName ) );
    }


    @Override
    public void renameKey( String oldName, String newName ) throws IOException {

        post( "/key/rename", Map.of( "arg", oldName, "new", newName ) );
    }
}
