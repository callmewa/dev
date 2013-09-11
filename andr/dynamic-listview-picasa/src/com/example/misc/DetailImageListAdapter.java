package com.example.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.listview.ImageDownloader;
import com.example.listview.R;
import com.google.picasa.model.Entry;

import java.util.ArrayList;

/**
 * Created by callmewa on 8/11/13.
 */
public class DetailImageListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private ArrayList<Entry> mEntries = new ArrayList<Entry>();

    final ImageDownloader mImageDownloader;

    public DetailImageListAdapter(Context context, ArrayList<Entry> entries) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mImageDownloader = ((ItemDetailActivity)mContext).mImageDownloader;
        if (entries!=null){
            this.mEntries = entries;
            notifyDataSetChanged();
        }
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
            itemView = (ViewGroup) mLayoutInflater.inflate(R.layout.detail_list_item, parent, false);
        }else{
            itemView = (ViewGroup) convertView;
        }

        ImageView imageView = (ImageView) itemView.findViewById(R.id.listImage);
//        TextView titleText = (TextView) itemView.findViewById(R.id.listTitle);
//        TextView descriptionText = (TextView) itemView.findViewById(R.id.listDescription);
//        TextView overlayText = (TextView) itemView.findViewById(R.id.textOverlay);

        String imageUrl = mEntries.get(position).getContent().getSrc();
        mImageDownloader.download(imageUrl, imageView);

//        String title = mEntries.get(position).getTitle().toString();
//        titleText.setText(title);
//        String description = mEntries.get(position).getSummary().toString();
//        descriptionText.setText(description);
        //itemView.setRotation(90);
        return itemView;
    }

    public void upDateEntries(ArrayList<Entry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}