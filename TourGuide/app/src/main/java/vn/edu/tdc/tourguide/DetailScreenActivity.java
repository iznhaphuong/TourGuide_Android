package vn.edu.tdc.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        Intent intent = getIntent();
        String id = intent.getStringExtra(AttractionActivity.EXTRA_DESTINATION);
        String title = intent.getStringExtra(AttractionActivity.EXTRA_TITLE);

        setTitle(title);

        imgLogo = findViewById(R.id.imgLogo);
        txtLocationName = findViewById(R.id.locationName);
        ratingValue = findViewById(R.id.locationRating);
        txtLocationLink = findViewById(R.id.locationLink);
        txtLocationDescription = findViewById(R.id.locationDescription);

        Destination destination = Destination.getDestination(id);
        if (destination != null) {
            City.getImage(destination.getImage(), imgLogo);
            txtLocationName.setText(destination.getName());
            ratingValue.setRating(destination.getRating());
            txtLocationLink.setText(destination.getAddress());
//            txtLocationDescription.setText(destination.get;
        }
    }
}
