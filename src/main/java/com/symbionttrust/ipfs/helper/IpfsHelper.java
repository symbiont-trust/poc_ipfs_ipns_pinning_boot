package com.symbionttrust.ipfs.helper;

import java.io.File;
import java.io.IOException;

/**
 * @author John Dickerson - 26 Jul 2025
 */
public interface IpfsHelper {

    String addFile( File file ) throws IOException;


    String publishToIpns( String cid ) throws IOException;


    void pinFile( String cid ) throws IOException;
}
