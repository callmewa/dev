/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.listview;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.core.IntentMap;
import com.example.feed.ImageDownloader;
import com.example.map.BasicMapActivity;
import com.google.picasa.model.Entry;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragment extends Fragment {
    private Entry mEntry;
    private int mPosition;
    final ImageDownloader mImageDownloader = IntentMap.IMAGE_DOWNLOADER;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given position.
     */
    public static ScreenSlidePageFragment create(int position) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(String.valueOf(R.string.entry_id_key), position);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(String.valueOf(R.string.entry_id_key));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a ImageView
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ScreenSlideActivity activity = (ScreenSlideActivity)getActivity();
        mEntry = activity.mEntries.get(mPosition);

        ImageView imageView = (ImageView) getView().findViewById(R.id.listImage);
        String imageUrl = mEntry.getContent().getSrc();
        mImageDownloader.download(imageUrl, imageView);
        //TODO: although this works it's very error prone.  click listeners should be set on the parent activity
        final Button btnMap = (Button) getActivity().findViewById(R.id.btnMap);
        final Context ctx = getActivity();
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnMap.setVisibility(View.VISIBLE);
                return true;
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, BasicMapActivity.class);
                ctx.startActivity(intent);
                btnMap.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnMap.setVisibility(View.GONE);
                    }
                }, 300);
            }
        });
    }
}
