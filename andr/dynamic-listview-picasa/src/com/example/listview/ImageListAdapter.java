package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.picasa.model.Entry;

import java.util.ArrayList;

/**
 * Created by callmewa on 8/2/13.
 */
public class ImageListAdapter extends BaseAdapter{

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private ArrayList<Entry> mEntries = new ArrayList<Entry>();

    private final ImageDownloader mImageDownloader = new ImageDownloader();

    public ImageListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageDownloader.setMode(ImageDownloader.Mode.CORRECT);
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
        TextView descriptionText = (TextView) itemView.findViewById(R.id.listDescription);
        TextView overlayText = (TextView) itemView.findViewById(R.id.textOverlay);

        overlayText.setVisibility(View.INVISIBLE);

        String imageUrl = mEntries.get(position).getContent().getSrc();
        mImageDownloader.download(imageUrl, imageView);

        //ImageTask task = new ImageTask(imageView);
        //task.execute(imageUrl);

        String title = mEntries.get(position).getTitle().toString();
        titleText.setText(title);
        String description = mEntries.get(position).getSummary().toString();
        descriptionText.setText(description);
        return itemView;
    }

    public void upDateEntries(ArrayList<Entry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}