package com.example.android.actionbarcompat;

import android.app.ListActivity;
import android.os.Bundle;

import com.example.listview.ImageListAdapter;
import com.example.listview.LoadFeedData;

public class DynamicListViewActivity extends ListActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ImageListAdapter adapter = new ImageListAdapter(this);
        setListAdapter(adapter);

        LoadFeedData loadFeedData = new LoadFeedData(adapter);
        loadFeedData.execute();
    }


}