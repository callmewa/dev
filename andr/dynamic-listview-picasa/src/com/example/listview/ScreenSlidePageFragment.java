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
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    final ImageDownloader mImageDownloader;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(Entry entry, ImageDownloader imageDownloader) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment(entry, imageDownloader);

        return fragment;
    }

    public ScreenSlidePageFragment(Entry entry, ImageDownloader imageDownloader) {
        mEntry = entry;
        mImageDownloader = imageDownloader;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a ImageView
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.listImage);

        String imageUrl = mEntry.getContent().getSrc();
        mImageDownloader.download(imageUrl, imageView);

        //TODO: remove after adding map button
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BasicMapActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
