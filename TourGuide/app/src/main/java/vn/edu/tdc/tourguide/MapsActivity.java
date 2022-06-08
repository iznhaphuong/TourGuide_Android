


package vn.edu.tdc.tourguide;


import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng mLocation;
    private LatLng dLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("TAGMAPS", "onMapReady: ");
        mMap = googleMap;
        //Create controls
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        Intent intent = getIntent();
        String x = intent.getStringExtra(DetailScreenActivity.EXTRA_LOCATION_LAT);
        String y = intent.getStringExtra(DetailScreenActivity.EXTRA_LOCATION_LONG);
        String title = intent.getStringExtra(DetailScreenActivity.EXTRA_ADDRESS);
        DetailScreenActivity.id = intent.getStringExtra(DetailScreenActivity.EXTRA_ID);
        DetailScreenActivity.title = intent.getStringExtra(DetailScreenActivity.EXTRA_TITLE_DETAIL);
        setTitle(title);
        String locationLabel = intent.getStringExtra(DetailScreenActivity.EXTRA_TITLE);
        setTitle(title);
        double xLat = Double.parseDouble(x);
        double yLong = Double.parseDouble(y);
        dLocation = new LatLng(xLat, yLong);

        //Check permission to display my location
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker in location and move the camera
        mMap.addMarker(new MarkerOptions()
                .position(dLocation)
                .title(locationLabel)
                .snippet(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dLocation));
        Log.d("TAGMLOCATION", "onMapReady: " + mLocation);


    }
    public boolean checkPermission(String permission) {
        int check = checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }
}
