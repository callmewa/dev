/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.listview.Contact;
import com.example.listview.DatabaseHandler;
import com.example.listview.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 * <p/>
 * Notice how we deal with the possibility that the Google Play services APK is not
 * installed/enabled/updated on a user's device.
 */
public class BasicMapActivity extends FragmentActivity {
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    DatabaseHandler mDb;
    List<Contact> mContacts;
    CameraUpdate cu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_demo);

        mDb = new DatabaseHandler(this);
        init();
        if (savedInstanceState == null) setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //retrieve fragment
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (mMapFragment != null) {
            mMap = mMapFragment.getMap();
            mMap.setMyLocationEnabled(true);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for(Contact place:mContacts){
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLat(), place.getLon()))
                        .title(place.getName())
                        .snippet(place.getAddress())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                builder.include(marker.getPosition());
                marker.showInfoWindow();
            }
            LatLngBounds bounds = builder.build();

            int padding = 20; // offset from edges of the map in pixels
            cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);


            boolean post = mMapFragment.getView().post(new Runnable() {
                @Override
                public void run() {
                    mMap.moveCamera(cu);
                }
            });
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.android_platform)));
//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    marker.setTitle("sf");
//                    return false;
//                }
//            });

        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(true)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);

        mMapFragment = SupportMapFragment.newInstance(options);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mMapFragment, "map").commit();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    }

    private void init() {
        mContacts = mDb.getAllContacts();
    }

}
