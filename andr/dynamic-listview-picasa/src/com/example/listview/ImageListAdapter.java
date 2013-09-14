package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.IntentMap;
import com.example.feed.ImageDownloader;
import com.google.picasa.model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by callmewa on 8/2/13.
 */
public class ImageListAdapter extends BaseAdapter{

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Entry> mEntries = new ArrayList<Entry>();

    final ImageDownloader mImageDownloader = IntentMap.IMAGE_DOWNLOADER;

    public ImageListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup itemView;
        if (convertView == null){
            itemView = (ViewGroup) mLayoutInflater.inflate(R.layout.list_item, parent, false);
        }else{
            itemView = (ViewGroup) convertView;
        }

        ImageView imageView = (ImageView) itemView.findViewById(R.id.listImage);
        TextView titleText = (TextView) itemView.findViewById(R.id.listTitle);
        //TextView descriptionText = (TextView) itemView.findViewById(R.id.listDescription);
        //TextView overlayText = (TextView) itemView.findViewById(R.id.textOverlay);

        //String imageUrl = mEntries.get(position).getContent().getSrc();
        String imageUrl = mEntries.get(position).media$group.media$content.get(0).url;
        mImageDownloader.download(imageUrl, imageView);

        String title = mEntries.get(position).getTitle().toString();
        titleText.setText(title);
        //String description = mEntries.get(position).getSummary().toString();
        //descriptionText.setText(description);
        return itemView;
    }

    public void upDateEntries(List<Entry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}