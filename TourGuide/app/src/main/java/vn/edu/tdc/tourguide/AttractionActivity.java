package vn.edu.tdc.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.adapter.AttractionAdapter;
import vn.edu.tdc.tourguide.adapter.HomeAdapter;
import vn.edu.tdc.tourguide.modle.Attraction;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;

public class AttractionActivity extends AppCompatActivity {
    private final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_layout);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String title = intent.getStringExtra(HomeFragment.EXTRA_TITLE);
        int city_id = intent.getIntExtra(HomeFragment.EXTRA_ID, 0);
        Log.d(TAG, "onCreate: " + city_id);
        // Capture the layout's TextView and set the string as its text
        setTitle(title);


        RecyclerView rcvAttraction = findViewById(R.id.rcv_attraction);
        List<Attraction> mAttractionList = new ArrayList<>();

        Attraction attraction1 = new Attraction(
                1,
                2,
                "Lang Bac va Quang truong Ba Dinh",
                5,
                0,
                "2 Hung Vuong, Dien Ban, Ba Dinh, Ha Noi");

        Attraction attraction2 = new Attraction(
                2,
                2,
                "Hoang Thanh Thang Long",
                3.5F,
                0,
                "19C Hoang Dieu, Dien Ban, Ba Dinh, Ha Noi");

        mAttractionList.add(attraction1);
        mAttractionList.add(attraction2);

        AttractionAdapter adapter = new AttractionAdapter(mAttractionList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAttraction.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AttractionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick: Chuyen qua dau gio");
            }
        });

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvAttraction.addItemDecoration(itemDecoration);

        rcvAttraction.setAdapter(adapter);
    }
}