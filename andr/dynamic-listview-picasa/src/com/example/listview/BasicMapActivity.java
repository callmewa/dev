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

package com.example.listview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 * <p>
 * Notice how we deal with the possibility that the Google Play services APK is not
 * installed/enabled/updated on a user's device.
 */
public class BasicMapActivity extends FragmentActivity {
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    static boolean setup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_demo);

        if(savedInstanceState == null)setUpMapIfNeeded();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(mMapFragment!=null){

            mMap = mMapFragment.getMap();
            Marker marker = mMap.addMarker(new MarkerOptions()
                    //37° 46' 30" N / 122° 25' 5" W
                    .position(new LatLng(37.7756, -122.4193))
                    .title("Hello world")
                    .snippet("Population: 4,137,400")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.android_platform)));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.setTitle("sf");
                    return false;
                }
            });


            GroundOverlay groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.android_platform))
                    .position(new LatLng(37.7756, -122.4193),100));

            mMap.setMyLocationEnabled(true);

            PolylineOptions rectOptions = new PolylineOptions()
                    .add(new LatLng(37.35, -122.0))
                    .add(new LatLng(37.45, -122.0))  // North of the previous point, but at the same longitude
                    .add(new LatLng(37.45, -122.2))  // Same latitude, and 30km to the west
                    .add(new LatLng(37.35, -122.2))  // Same longitude, and 16km to the south
                    .add(new LatLng(37.35, -122.0))
                    .color(Color.RED); // Closes the polyline.

            // Get back the mutable Polyline
            //Polyline polyline = mMap.addPolyline(rectOptions);

            // Instantiates a new Polygon object and adds points to define a rectangle
            PolygonOptions recOptions = new PolygonOptions()
                    .add(new LatLng(37.35, -122.0),
                            new LatLng(37.45, -122.0),
                            new LatLng(37.45, -122.2),
                            new LatLng(37.35, -122.2),
                            new LatLng(37.35, -122.0))
                    //.strokeColor(Color.RED)
                    //.fillColor(Color.BLUE)
                    ;

// Get back the mutable Polygon
            //Polygon polygon = mMap.addPolygon(recOptions);

            Polygon polygon = mMap.addPolygon(new PolygonOptions()
                    .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5))
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));

            mMap.addPolygon(new PolygonOptions()
                    .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5), new LatLng(3, 0), new LatLng(0, 0))
                    .addHole(Arrays.asList(new LatLng(1, 1), new LatLng(1, 2), new LatLng(2, 2), new LatLng(2, 1), new LatLng(1, 1)))
                    .fillColor(Color.BLUE));

            // Instantiates a new CircleOptions object and defines the center and radius
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(37.4, -122.1))
                    .radius(1000); // In meters

// Get back the mutable Circle
            Circle circle = mMap.addCircle(circleOptions);


            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(-37.81319, 144.96298), new LatLng(-31.95285, 115.85734))
                    .width(25)
                    .color(Color.BLUE)
                    .geodesic(true));
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        //if (mMapFragment == null) {
        //    setup = !setup;
            // Try to obtain the map from the SupportMapFragment.
            //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            GoogleMapOptions options = new GoogleMapOptions();
            options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                    .compassEnabled(true)
                    .rotateGesturesEnabled(false)
                    .tiltGesturesEnabled(false);

            mMapFragment = SupportMapFragment.newInstance(options);
            //mMapFragment.setRetainInstance(false);
            View container = findViewById(R.id.fragment_container);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mMapFragment, "map").commit();

            System.out.print("");
        //}
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

}
