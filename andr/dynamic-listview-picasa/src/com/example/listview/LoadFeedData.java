package com.example.listview;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.picasa.model.Entry;
import com.google.picasa.model.SearchResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by callmewa on 8/2/13.
 */
public class LoadFeedData extends AsyncTask<Void, Void, ArrayList<Entry>> {


    private final String mUrl =
            "http://picasaweb.google.com/data/feed/api/all?kind=photo&q=" +
                    "sunset%20landscape&alt=json&max-results=20&thumbsize=144c";

    private InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
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

    private final ImageListAdapter mAdapter;

    public LoadFeedData(ImageListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
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
    }



}