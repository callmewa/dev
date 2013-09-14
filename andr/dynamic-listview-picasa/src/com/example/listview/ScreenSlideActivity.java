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
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.core.DepthPageTransformer;
import com.example.core.IntentMap;
import com.example.feed.ImageDownloader;
import com.google.gson.reflect.TypeToken;
import com.google.picasa.model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates a "screen-slide" animation using a {@link android.support.v4.view.ViewPager}. Because {@link android.support.v4.view.ViewPager}
 * automatically plays such an animation when calling {@link android.support.v4.view.ViewPager#setCurrentItem(int)}, there
 * isn't any animation-specific code in this sample.
 *
 * <p>This sample shows a "next" button that advances the user to the next step in a wizard,
 * animating the current screen out (to the left) and the next screen in (from the right). The
 * reverse animation is played when the user presses the "previous" button.</p>
 *
 * @see ScreenSlidePageFragment
 */
public class ScreenSlideActivity extends FragmentActivity {
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    List<Entry> mEntries;
    boolean menuShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra(getString(R.string.detail_id_key));
        mEntries = (List<Entry>) IntentMap.SHARED_MAP.get(itemId);

        //retrieves the serialized mEntries should {@link IntentMap.SHARED_MAP} be destroyed
        if(savedInstanceState!=null && mEntries == null){
            String gson = savedInstanceState.getString(getString(R.string.detail_id_key));
            mEntries = IntentMap.GSON.fromJson(gson, new TypeToken<List<Entry>>(){}.getType());
        }

        //TODO; remove before live.. A crash is happening because process is killed thus map is empty
        if(mEntries == null){
            throw new NullPointerException("mEntries is null for itemId <" + itemId + "> in " + IntentMap.SHARED_MAP);
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ImageSlidePagerAdapter(mEntries, getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });
        mPager.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    protected  void onSaveInstanceState(Bundle outState){
        outState.putString(getString(R.string.detail_id_key) ,IntentMap.GSON.toJson(mEntries));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, DynamicListViewActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class ImageSlidePagerAdapter extends  FragmentStatePagerAdapter{
        private List<Entry> mEntries = new ArrayList<Entry>();

        final ImageDownloader mImageDownloader;

        public ImageSlidePagerAdapter(List<Entry> entries, FragmentManager fm) {
            super(fm);
            mImageDownloader = IntentMap.IMAGE_DOWNLOADER;
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
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position);
        }
    }
}
