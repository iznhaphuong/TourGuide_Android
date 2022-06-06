package vn.edu.tdc.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.libraries.maps.GoogleMap;

import vn.edu.tdc.tourguide.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = "tag";
    private SupportMapFragment mapFragment;

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
        Log.d(TAG, "onMapReady: ");
        mMap = googleMap;
        Intent intent = getIntent();
        String x = intent.getStringExtra(DetailScreenActivity.EXTRA_LOCATION_LAT);
        String y = intent.getStringExtra(DetailScreenActivity.EXTRA_LOCATION_LONG);
        String permission = intent.getStringExtra(DetailScreenActivity.EXTRA_PERMISSION);
        if (permission.equals("true")) {
            mMap.setMyLocationEnabled(true);
        }
        double xLat = Double.parseDouble(x);
        double yLong = Double.parseDouble(y);
        String title = intent.getStringExtra(DetailScreenActivity.EXTRA_TITLE);
        Log.d(TAG, "onMapReady: " + x + y + title + permission);

        // Add a marker in location and move the camera
        LatLng location = new LatLng(xLat, yLong);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(title)
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}

