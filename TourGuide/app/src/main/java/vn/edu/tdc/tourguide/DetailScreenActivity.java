package vn.edu.tdc.tourguide;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private Button btnAddSchedule;
    private Button btnReview;
    private TextView txtLocationDescription;
    private Intent intent;
    public static String title;
    public static String city_id;
    public static String EXTRA_LOCATION_LAT = "EXTRA_LOCATION_LAT";
    public static String EXTRA_LOCATION_LONG =  "EXTRA_LOCATION_LONG";
    public static String EXTRA_TITLE= "EXTRA_TITLE";
    public static String EXTRA_ID_DES= "EXTRA_ID_DES";
    public static String EXTRA_PERMISSION= "EXTRA_PERMISSION";
//    private String EXTRA_ID_DES_REVIEW = "EXTRA_ID_DES_REVIEW";
    private String xLat;
    private String yLong;
    private int REQ_CODE = 99;
    private String isPermission = "false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        intent = getIntent();
        String id = intent.getStringExtra(AttractionActivity.EXTRA_ID);
        String idFromReview = intent.getStringExtra(ReviewScreenActivity.EXTRA_ID_DES_REVIEW);
        title = intent.getStringExtra(AttractionActivity.EXTRA_TITLE);
        setTitle(title);
        String TAG = "TAG";
        Log.d(TAG, "onCreate: 3-" + id);

        imgLogo = findViewById(R.id.imgLogo);
        txtLocationName = findViewById(R.id.locationName);
        ratingValue = findViewById(R.id.locationRating);
        txtLocationLink = findViewById(R.id.locationLink);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);
        btnReview = findViewById(R.id.btnReview);
        txtLocationDescription = findViewById(R.id.locationDescription);
        Destination destination;
        if(id != null){
           destination = Destination.getDestination(id);
        }else{
             destination = Destination.getDestination(idFromReview);
        }
        city_id = destination.getCity_id();
        xLat = destination.getxLat()+"";
        yLong = destination.getyLong()+"";

        if (destination != null) {
            City.getImage(destination.getImage(), imgLogo);
            txtLocationName.setText(destination.getName());
            ratingValue.setRating(destination.getRating());
            txtLocationLink.setText(destination.getAddress());
//            txtLocationDescription.setText(destination.get;

        }
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailScreenActivity.this, ReviewScreenActivity.class);
                intent.putExtra(EXTRA_ID_DES, id);

                startActivity(intent);
            }
        });
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailScreenActivity.this, AddScheduleActivity.class);
                intent.putExtra(EXTRA_ID_DES, id);
                startActivity(intent);
            }
        });
        txtLocationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check and allow permission
                if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) || !checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Yeu cau cap quyen
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_CODE);
                } else {
                    performAction();
                }
                Intent intent = new Intent(DetailScreenActivity.this, MapsActivity.class);
                intent.putExtra(EXTRA_LOCATION_LAT, xLat);
                intent.putExtra(EXTRA_LOCATION_LONG, yLong);
                intent.putExtra(EXTRA_TITLE, title);
                intent.putExtra(EXTRA_PERMISSION, isPermission);
                Log.d("NHAPHUONG", "onClick: " + xLat + yLong + isPermission);
                startActivity(intent);
            }
        });
    }


    @SuppressLint("MissingPermission")
    private void performAction() {
        isPermission = "true";
    }

    //Check permission
    private boolean checkPermission(String permission) {
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

