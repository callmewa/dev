package com.example.listview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by callmewa on 8/5/13.
 */

public class ItemDetailFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.item_detail_image, container, false);
        View frameView = inflater.inflate(R.layout.item_detail_list, container, false);
        ImageListAdapter adapter = new ImageListAdapter(getActivity());

        ListView listView = (ListView) frameView.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        LoadFeedData loadFeedData = new LoadFeedData(adapter);
        loadFeedData.execute();
        return frameView;
    }
}
