package vn.edu.tdc.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
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
    private TextView txtLocationDescription;
    private Intent intent;
    public static String title;
    public static String city_id;
    public static String EXTRA_LOCATION_LAT= "EXTRA_LOCATION_LAT";
    public static String EXTRA_LOCATION_LONG= "EXTRA_LOCATION_LONG";
    public static String EXTRA_TITLE= "EXTRA_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        intent = getIntent();
        String id = intent.getStringExtra(AttractionActivity.EXTRA_ID);
        title = intent.getStringExtra(AttractionActivity.EXTRA_TITLE);
        setTitle(title);
        String TAG = "TAG";
        Log.d(TAG, "onCreate: 3-" + id);


        imgLogo = findViewById(R.id.imgLogo);
        txtLocationName = findViewById(R.id.locationName);
        ratingValue = findViewById(R.id.locationRating);
        txtLocationLink = findViewById(R.id.locationLink);
        txtLocationDescription = findViewById(R.id.locationDescription);

        Destination destination = Destination.getDestination(id);
        city_id = destination.getCity_id();
        int xLat = (int) destination.getxLat();
        int yLong = (int) destination.getyLong();

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
                Intent intent = new Intent(DetailScreenActivity.this, MapsActivity.class);
                intent.putExtra(EXTRA_LOCATION_LAT, xLat);
                intent.putExtra(EXTRA_LOCATION_LONG, yLong);
                intent.putExtra(EXTRA_TITLE, title);
                startActivity(intent);
            }
        });
    }

}

