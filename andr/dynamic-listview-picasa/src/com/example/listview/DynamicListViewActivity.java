package com.example.listview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.feed.LoadFeedData;
import com.google.picasa.model.Entry;

public class DynamicListViewActivity extends ListActivity {

    private ImageListAdapter mAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list);

        mAdapter = new ImageListAdapter(this);
        setListAdapter(mAdapter);

        LoadFeedData loadFeedData = new LoadFeedData();
        loadFeedData.loadFeed(mAdapter);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView textOverlay = (TextView)view.findViewById(R.id.textOverlay);
                Intent intent = new Intent(DynamicListViewActivity.this, ScreenSlideActivity.class);
                String title = ((Entry)DynamicListViewActivity.this.getListAdapter().getItem(position)).getTitle().toString();
                String url =  ((Entry)DynamicListViewActivity.this.getListAdapter().getItem(position)).id.$t;
                intent.putExtra(getString(R.string.detail_title_key), title);
                intent.putExtra(getString(R.string.detail_id_key), url);
                startActivity(intent);
            }
        });
    }
}