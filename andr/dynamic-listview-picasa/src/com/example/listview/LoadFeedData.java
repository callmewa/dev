package com.example.listview;

import android.os.AsyncTask;

import com.google.auth.GoogleAuthenticator;
import com.google.gson.Gson;
import com.google.picasa.model.Entry;
import com.google.picasa.model.SearchResult;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by callmewa on 8/2/13.
 */
public class LoadFeedData {

    DefaultHttpClient client = new DefaultHttpClient();
    String authToken = null;



            //fields=entry(id,title,summary,content)
    private InputStream retrieveStream(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpGet);
            HttpEntity getResponseEntity = httpResponse.getEntity();
            return getResponseEntity.getContent();
        } catch (IOException e) {
            httpGet.abort();
        }
        return null;
    }

    public void loadFeed(ImageListAdapter mAdapter){
        LoadFeed feed = new LoadFeed(mAdapter);
        feed.execute();
    }

    public void loadAlbum(List<Entry> entries){
        LoadAlbum album = new LoadAlbum(null, entries);
        album.execute();
    }

    private void setAuthorizationHeader(String authToken) {
        String authHeader = "GoogleLogin auth=" + authToken;
        Collection<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Authorization", authHeader));
        client.getParams().setParameter(ClientPNames.DEFAULT_HEADERS, headers);
    }



    public class LoadFeed extends AsyncTask<Void, Void, ArrayList<Entry>> {

        private final String mUrl = "https://picasaweb.google.com/data/feed/api/user/platr.upload" +
                "?kind=album&fields=entry(id,title,summary,media:group(media:content))&alt=json";

        private final ImageListAdapter mAdapter;

        public LoadFeed(ImageListAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        protected ArrayList<Entry> doInBackground(Void... params) {
            GoogleAuthenticator auth = new GoogleAuthenticator();
            authToken = auth.authenticate(client);
            setAuthorizationHeader(authToken);

            InputStream source = retrieveStream(mUrl);
            Reader reader = null;
            try {
                reader = new InputStreamReader(source);
            } catch (Exception e) {
                return null;
            }
            Gson gson = new Gson();
            SearchResult result = gson.fromJson(reader,SearchResult.class);
            return result.getFeed().getEntry();
        }

        protected void onPostExecute(ArrayList<Entry> entries) {
            mAdapter.upDateEntries(entries);
            loadAlbum(entries);
        }
    }

    public class LoadAlbum extends AsyncTask<Void, Void, ArrayList<Entry>> {

        private final String albumUrlParams = "?fields=entry(id,title,summary,content)&alt=json&max-results=20";

        private final ImageListAdapter mAdapter;
        private List<Entry> albumEntries;

        public LoadAlbum(ImageListAdapter adapter, List<Entry> albumEntries) {
            mAdapter = adapter;
            this.albumEntries = albumEntries;
        }

        @Override
        protected ArrayList<Entry> doInBackground(Void... params) {
            for (Entry albumEntry: albumEntries){
                String albumUrl = albumEntry.getId().$t.split("\\?")[0].replace("/entry/", "/feed/");
                albumUrl+=albumUrlParams;
                InputStream source = retrieveStream(albumUrl);
                Reader reader = null;
                try {
                    reader = new InputStreamReader(source);
                } catch (Exception e) {
                }
                Gson gson = new Gson();
                SearchResult result = gson.fromJson(reader,SearchResult.class);
                ArrayList<Entry> entries = result.feed.entry;
                IntentMap.SharedMap.put(albumEntry.id.$t, entries);
            }
            return null;
        }
    }



}