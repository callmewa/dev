package com.example.listview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.picasa.model.Entry;

import java.lang.ref.WeakReference;

public class DynamicListViewActivity extends ListActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list);

        ImageListAdapter adapter = new ImageListAdapter(this);
        setListAdapter(adapter);

        LoadFeedData loadFeedData = new LoadFeedData(adapter);
        loadFeedData.execute();

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textOverlay = (TextView)view.findViewById(R.id.textOverlay);

                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                textOverlay.setAlpha(0f);
                textOverlay.setVisibility(View.VISIBLE);

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                textOverlay.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null);

                Intent intent = new Intent(DynamicListViewActivity.this, ItemDetailActivity.class);
                String title = ((Entry)DynamicListViewActivity.this.getListAdapter().getItem(position)).getTitle().toString();
                //String url = ((Entry)DynamicListViewActivity.this.getListAdapter().getItem(position)).getContent().getSrc();
                String url =  ((Entry)DynamicListViewActivity.this.getListAdapter().getItem(position)).media$group.media$content.get(0).url;
                intent.putExtra(DynamicListViewActivity.this.getResources().getString(R.string.detail_title_key), title);
                intent.putExtra(DynamicListViewActivity.this.getResources().getString(R.string.detail_img_url_key), url);

                WeakReference<ImageDownloader> weakDownloader = new WeakReference<ImageDownloader>(((ImageListAdapter)DynamicListViewActivity.this.getListAdapter()).mImageDownloader);
                Long key = System.currentTimeMillis();
                IntentMap.SharedMap.put(key, weakDownloader);
                intent.putExtra("shared_key", key);
                startActivity(intent);

            }
        });
    }
}