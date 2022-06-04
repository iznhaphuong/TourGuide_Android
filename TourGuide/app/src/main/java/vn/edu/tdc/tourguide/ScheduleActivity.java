package vn.edu.tdc.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import vn.edu.tdc.tourguide.adapter.AttractionAdapter;
import vn.edu.tdc.tourguide.adapter.ScheduleAdapter;
import vn.edu.tdc.tourguide.modle.Attraction;
import vn.edu.tdc.tourguide.modle.EventSchedule;


public class ScheduleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentDate = ft.format(fullTime) ;//02/06/2022

        Log.d("timeHH", String.valueOf(fullTime));
        Log.d("current",currentDate);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();



//        int city_id = intent.getIntExtra(HomeFragment.EXTRA_ID, 0);
//        Log.d(TAG, "onCreate: " + city_id);
//         Capture the layout's TextView and set the string as its text
        setTitle("Schedule");


        RecyclerView scheduleList = findViewById(R.id.schedule_list);
        ArrayList<EventSchedule> arrEvents = new ArrayList<>();

        EventSchedule event1 = new EventSchedule(1,"Hồ Gươm","8:30","01/06/2022","Đi chơi dui dẻ");
        EventSchedule event2 = new EventSchedule(2,"Quảng trường Ba Đình","8:30","02/06/2022","Đi chơi dui dẻ");



        arrEvents.add(event1);
        arrEvents.add(event2);

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(arrEvents);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        scheduleList.setLayoutManager(linearLayoutManager);

//        scheduleAdapter.setOnItemClickListener(new AttractionAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Log.d(TAG, "onItemClick: Chuyen qua dau gio");
//            }
//        });

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        scheduleList.addItemDecoration(itemDecoration);

        scheduleList.setAdapter(scheduleAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_option_menu, menu);
        return true;
    }

}
