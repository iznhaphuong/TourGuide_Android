package vn.edu.tdc.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
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

import vn.edu.tdc.tourguide.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int REQ_CODE = 99;
    private boolean isPermission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Check and allow permission
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) || !checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            //Yeu cau cap quyen
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_CODE);
        } else {
            performAction();
        }

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
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        int xLat = Integer.parseInt(intent.getStringExtra(DetailScreenActivity.EXTRA_LOCATION_LAT));
        int yLong = Integer.parseInt(intent.getStringExtra(DetailScreenActivity.EXTRA_LOCATION_LONG));
        String title = intent.getStringExtra(DetailScreenActivity.EXTRA_TITLE);

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(xLat, yLong);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(title)
                );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        if (isPermission){
            mMap.setMyLocationEnabled(true);
        }
    }

    private void performAction(){
        Toast.makeText(this, "Called", Toast.LENGTH_SHORT).show();
        isPermission = true;
    }

    //Check permission
    private boolean checkPermission (String permission) {
        int check = checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE) {
            if (permissions.length == grantResults.length) {
                for (int i = 0; i < permissions.length; ++i) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                performAction();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);    }
}

