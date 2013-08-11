package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by callmewa on 8/5/13.
 */
public class ItemDetailActivity extends FragmentActivity {

    private ImageDownloader mImageDownloader = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra(getResources().getString(R.string.detail_title_key));

        TextView textView = (TextView) findViewById(R.id.detail_title);
        textView.setText(itemTitle);
        Long sharedkey = getIntent().getLongExtra("shared_key", -1);
        Object obj = IntentMap.SharedMap.remove(sharedkey);
        if(obj != null){
            mImageDownloader = ((WeakReference<ImageDownloader>)obj).get();
            if(mImageDownloader == null){
                mImageDownloader = new ImageDownloader();
                mImageDownloader.setMode(ImageDownloader.Mode.CORRECT);
            }
        }
    }
}