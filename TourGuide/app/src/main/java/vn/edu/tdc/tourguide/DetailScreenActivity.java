

package vn.edu.tdc.tourguide;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.models.City;
import vn.edu.tdc.tourguide.models.Destination;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;

public class DetailScreenActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TextView txtLocationName;
    private RatingBar ratingValue;
    private TextView txtLocationLink;
    private TextView txtLocationDescription;
    private Intent intent;
    public static String title;
    public static String city_id;
    public static String EXTRA_LOCATION_LAT = "EXTRA_LOCATION_LAT";
    public static String EXTRA_LOCATION_LONG =  "EXTRA_LOCATION_LONG";
    public static String EXTRA_TITLE= "EXTRA_TITLE";
    public static String EXTRA_TITLE_DETAIL= "EXTRA_TITLE_DETAIL";
    public static String EXTRA_ADDRESS= "EXTRA_ADDRESS";
    public static String EXTRA_ID = "EXTRA_ID";
    public static String id;
    private String xLat;
    private String yLong;
    private int REQ_CODE = 111;
    private boolean isPermission = false;
    private Intent intentSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        String TAG = "TAG";
        Log.d(TAG, "onCreate: 3-" + id);

        if (id == null) {
            intent = getIntent();
            id = intent.getStringExtra(AttractionActivity.EXTRA_ID);
            title = intent.getStringExtra(AttractionActivity.EXTRA_TITLE);
        }
        setTitle(title);

        imgLogo = findViewById(R.id.imgLogo);
        txtLocationName = findViewById(R.id.locationName);
        ratingValue = findViewById(R.id.locationRating);
        txtLocationLink = findViewById(R.id.locationLink);
        txtLocationDescription = findViewById(R.id.locationDescription);

        Destination destination = Destination.getDestination(id);
        city_id = destination.getCity_id();

        xLat = destination.getxLat() +"";
        yLong = destination.getyLong()+"";

        if (destination != null) {
            City.getImage(destination.getImage(), imgLogo);
            txtLocationName.setText(destination.getName());
            ratingValue.setRating(destination.getRating());
            txtLocationLink.setText(destination.getAddress());
//            txtLocationDescription.setText(destination.get;
        }

        txtLocationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check and allow permission
                intentSend  = new Intent(DetailScreenActivity.this, MapsActivity.class);
                intentSend.putExtra(EXTRA_LOCATION_LAT, xLat);
                intentSend.putExtra(EXTRA_LOCATION_LONG, yLong);
                intentSend.putExtra(EXTRA_TITLE, txtLocationName.getText());
                intentSend.putExtra(EXTRA_ADDRESS, txtLocationLink.getText());
                intentSend.putExtra(EXTRA_ID, id);
                intentSend.putExtra(EXTRA_TITLE_DETAIL, title);

                if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Yeu cau cap quyen
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_CODE);
                } else {
                    performAction();
                }
            }
        });
    }


    @SuppressLint("MissingPermission")
    private void performAction() {
        isPermission = true;
        startActivity(intentSend);
    }


    //Check permission
    public boolean checkPermission(String permission) {
        int check = checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE) {
            if (permissions.length == grantResults.length) {
                for (int i = 0; i < permissions.length; ++i) {
                    String recommendation  = getResources().getString(R.string.recommendation);
                    Toast.makeText(DetailScreenActivity.this, recommendation, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            String permission  = getResources().getString(R.string.permission);
            Toast.makeText(DetailScreenActivity.this, permission, Toast.LENGTH_SHORT).show();
            performAction();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

