package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.picasa.model.Entry;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by callmewa on 8/5/13.
 */
public class ItemDetailActivity extends FragmentActivity {

    ImageDownloader mImageDownloader = null;
    ArrayList <Entry> mEntries;


    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String itemId = intent.getStringExtra(getResources().getString(R.string.detail_id_key));
        mEntries = (ArrayList<Entry>) IntentMap.SharedMap.get(itemId);


        Long sharedKey = getIntent().getLongExtra(getResources().getString(R.string.image_downloader_key), -1);
        Object obj = IntentMap.SharedMap.remove(sharedKey);
        mImageDownloader = ((WeakReference<ImageDownloader>)obj).get();
//        if(obj != null){
//            mImageDownloader = ((WeakReference<ImageDownloader>)obj).get();
//            if(mImageDownloader == null){
//                mImageDownloader = new ImageDownloader();
//                mImageDownloader.setMode(ImageDownloader.Mode.CORRECT);
//            }
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        String itemTitle = intent.getStringExtra(getResources().getString(R.string.detail_title_key));
        TextView textView = (TextView) findViewById(R.id.detail_title);
        textView.setText(itemTitle);

    }
    @Override
    protected void onSaveInstanceState (Bundle outState){
        Long sharedKey = getIntent().getLongExtra(getResources().getString(R.string.image_downloader_key), -1);
        IntentMap.SharedMap.put(sharedKey, new WeakReference<ImageDownloader>(mImageDownloader));
    }
}