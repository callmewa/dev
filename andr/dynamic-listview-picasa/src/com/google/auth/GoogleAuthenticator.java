package com.google.auth;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by callmewa on 8/10/13.
 */
public class GoogleAuthenticator {

    String authUrl = "https://www.google.com/accounts/ClientLogin";
    //String authParams = "accountType=HOSTED_OR_GOOGLE&service=lh2&source=gdata-PhotosPartialDemo&Email=platr.upload@gmail.com&Passwd=foodimages";

    //TODO: figure out a better way to host this
    static String authToken = null;

    public String authenticate(HttpClient client){
        HttpPost post = new HttpPost(authUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("Email", "platr.upload@gmail.com"));
        nameValuePairs.add(new BasicNameValuePair("Passwd", "foodimages"));
        nameValuePairs.add(new BasicNameValuePair("accountType", "HOSTED_OR_GOOGLE"));
        nameValuePairs.add(new BasicNameValuePair("source", "gdata-Demo"));
        nameValuePairs.add(new BasicNameValuePair("service", "lh2"));


        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                //System.out.println(line);
                if (line.startsWith("Auth=")) {
                    String authToken = line.substring(5);
                    GoogleAuthenticator.authToken = authToken;
                    return authToken;
                }
            }
        }
        catch (IOException e) {
            post.abort();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
