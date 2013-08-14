package com.example.listview;

import com.google.auth.GoogleAuthenticator;

import junit.framework.TestCase;

import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by callmewa on 8/12/13.
 */
public class AuthTest extends TestCase {

    public void testGetAuthToken(){

        DefaultHttpClient client = new DefaultHttpClient();
        GoogleAuthenticator auth  = new GoogleAuthenticator();
        String authToken = auth.authenticate( client);

        assertNotNull(authToken);


    }

//    public static void main(String [ ] args){
//
//        AuthTest test = new AuthTest();
//        test.getAuthTokenTest();
//
//    }

}

