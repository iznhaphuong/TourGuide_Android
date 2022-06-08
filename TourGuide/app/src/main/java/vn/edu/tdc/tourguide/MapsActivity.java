


package vn.edu.tdc.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
        String locationLabel = intent.getStringExtra(DetailScreenActivity.EXTRA_TITLE);
        setTitle(title);

        Log.d("TAGMAPS", "onMapReady: " + x + y);

        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            mMap.setMyLocationEnabled(true);
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Address address = getAddress(MapsActivity.this, location.getLatitude(), location.getLongitude());
                                if (address != null) {
                                    Log.d("TAGADDRESS", "onSuccess: " + address.getAddressLine(0));
                                }
                            }
                        }
                    });
        }

        double xLat = Double.parseDouble(x);
        double yLong = Double.parseDouble(y);
        Log.d("TAGMAPS", "onMapReady: " + x + y + locationLabel);
        Log.d("TAGMAPSLOCALITY", "onMapReady: " + getLocality(xLat, yLong));
        getAddress(this, xLat, yLong);
        // Add a marker in location and move the camera
        LatLng location = new LatLng(xLat, yLong);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(locationLabel)
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    public String getLocality(double xLat, double yLong) {
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> list = null;
        try {
            list = gcd.getFromLocation(xLat, yLong, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = "Viet nam";
        if (list != null & list.size() > 0) {
            android.location.Address address = list.get(0);
            result = address.getLocality();
        }
        return result;
    }

    public boolean checkPermission(String permission) {
        int check = checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }

    private Address getAddress(Context context, double LATITUDE, double LONGITUDE) {
        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
