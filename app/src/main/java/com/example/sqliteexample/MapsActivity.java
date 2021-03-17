package com.example.sqliteexample;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelperClass db;
    List<Marker> markerList;
    LatLng location = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        db = new DatabaseHelperClass(this);
        Geocoder coder = new Geocoder(this);
        markerList = new ArrayList<>();
        List<EmployeeModelClass> placeList = db.getAllPlaces();
        if (placeList.size() > 0) {

            for (EmployeeModelClass p : placeList) {
                //   String myInfo = "ID: " + p.getId() + " Latitude: " + p.getLatitude() + "Longitude"
                //  + p.getLongitude() + " Title: " + p.getName();
                //  Log.d("myInfo", myInfo);
                List<Address> geocodeResults = null;

                try {
                    geocodeResults = coder.getFromLocationName(p.getAddress(), 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator<Address> locations = geocodeResults.iterator();
                while (locations.hasNext()) {
                    Address loc = locations.next();
                    //latt = loc.getLatitude();
                    // longg = loc.getLongitude();
                    location = new LatLng(loc.getLatitude(), loc.getLongitude());
                    if (location == null) {
                        Toast.makeText(this, "There is no employee in the database", Toast.LENGTH_SHORT).show();

                        mMap.addMarker(new MarkerOptions().position(location).title("canada"));
                    } else {

                        markerList.add(mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(p.getName())
                                .zIndex(p.getId())
                                .snippet(p.getAddress())));
                    }
                }
            }


            for (Marker m : markerList) {
                LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
                mMap.addMarker(new MarkerOptions().position(latLng));

            }


        }
    }

}

