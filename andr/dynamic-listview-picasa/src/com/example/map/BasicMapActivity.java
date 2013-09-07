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

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.example.listview.Contact;
import com.example.listview.DatabaseHandler;
import com.example.listview.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    List<Marker> markers;
    Marker me;
    static DecimalFormat DIST_FORMAT = new DecimalFormat("#.#");

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
            mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            if(markers == null){
                markers = new ArrayList<Marker>();
                for(Contact place:mContacts){
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(place.getLat(), place.getLon()))
                            .title(place.getName())
                            .snippet(place.getAddress())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    markers.add(marker);
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                cu = CameraUpdateFactory.newLatLngBounds(bounds, 20);


                boolean post = mMapFragment.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        mMap.animateCamera(cu);
                        me = mMap.addMarker(new MarkerOptions()
                                .position(mMap.getCameraPosition().target)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                    }
                });
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    int index =  markers.indexOf(marker);
                    if (index >= 0){
                        marker.setSnippet(mContacts.get(index).getAddress());
                        marker.showInfoWindow();
                        return true;
                    }
                    return false;
                }
            });

            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                Random rand = new Random();
                @Override
                public void onCameraChange(final CameraPosition cameraPosition) {
                    me.setPosition(cameraPosition.target);
                    double lat = cameraPosition.target.latitude;
                    double lon = cameraPosition.target.longitude;
                    int i = 0;
                    mContacts = mDb.getContactByDist(lat, lon, 5);
                    for(Marker marker:markers){
                        marker.remove();
                    }
                    markers.clear();
                    //add new markers within set distance
                    for(Contact place:mContacts){
                        double lat2 = place.getLat();
                        double lon2 = place.getLon();
                        double distance = hdistance(Math.toRadians(lat), Math.toRadians(lon), Math.toRadians(lat2), Math.toRadians(lon2));
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(place.getLat(), place.getLon()))
                                .title(place.getName())
                                .snippet(DIST_FORMAT.format(distance) + " km away")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        markers.add(marker);
                    }
                    if(markers.size()>0) markers.get(0).showInfoWindow();
                }
            });
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.android_platform)));
        }
    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    private double hdistance(double lat, double lon, double lat2, double lon2) {
        double EARTH_RADIUS = 6367.45;  //km
        double deltaLat = lat2 - lat;
        double deltaLon = lon2 - lon;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat) * Math.cos(lat2) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS*c;
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
