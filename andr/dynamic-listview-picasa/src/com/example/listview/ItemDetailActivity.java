package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by callmewa on 8/5/13.
 */
public class ItemDetailActivity extends FragmentActivity {

    private final ImageDownloader mImageDownloader = new ImageDownloader();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra(getResources().getString(R.string.detail_title_key));

        TextView textView = (TextView) findViewById(R.id.detail_title);
        textView.setText(itemTitle);

        ImageView imageView = (ImageView) findViewById(R.id.detailImage);
        String imageUrl = getIntent().getStringExtra(getResources().getString(R.string.detail_img_url_key));
        mImageDownloader.setMode(ImageDownloader.Mode.CORRECT);
        mImageDownloader.download(imageUrl,imageView );

    }
}