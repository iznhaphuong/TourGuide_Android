package vn.edu.tdc.tourguide;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.adapter.HeadlineImageAdapter;
import vn.edu.tdc.tourguide.model.HeadlineImage;

public class DetailScreenActivity extends AppCompatActivity {
    private RecyclerView rcvHeadlineImage;
    private List<HeadlineImage> myListHeadlineImage;
    private HeadlineImageAdapter headlineImageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);
        rcvHeadlineImage = findViewById(R.id.rcvHeadlineImage);
        myListHeadlineImage = new ArrayList<>();
        HeadlineImage image1 = new HeadlineImage(R.drawable.ho_guom2, R.drawable.ho_guom2, R.drawable.ho_guom2);
        HeadlineImage image2 = new HeadlineImage(R.drawable.ho_guom3, R.drawable.ho_guom3, R.drawable.ho_guom3);
        HeadlineImage image3 = new HeadlineImage(R.drawable.ho_guom4, R.drawable.ho_guom4, R.drawable.ho_guom4);


        headlineImageAdapter = new HeadlineImageAdapter(myListHeadlineImage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false);
        rcvHeadlineImage.setLayoutManager(linearLayoutManager);
        rcvHeadlineImage.setAdapter(headlineImageAdapter);
    }
}
