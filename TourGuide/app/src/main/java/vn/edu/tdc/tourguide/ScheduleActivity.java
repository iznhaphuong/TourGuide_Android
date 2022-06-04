package vn.edu.tdc.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;


import vn.edu.tdc.tourguide.adapter.ScheduleAdapter;
import vn.edu.tdc.tourguide.model.EventSchedule;


public class ScheduleActivity extends AppCompatActivity {

    protected TextView currentTime;

    private String TAG = "schedule";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        currentTime = findViewById(R.id.currentDate);



        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentDate = ft.format(fullTime) ;//02/06/2022

        Log.d("timeHH", String.valueOf(fullTime));
        Log.d("current",currentDate);


        RecyclerView scheduleList = findViewById(R.id.schedule_list);
        ArrayList<EventSchedule> arrEvents = new ArrayList<EventSchedule>();
        EventSchedule eventSchedule;
        //Load data event from firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                EventSchedule eventSchedule = snapshot.getValue(EventSchedule.class);
                if (eventSchedule != null) {
                    Log.d("test", "onChildAdded: " + eventSchedule.toString());
                    arrEvents.add(eventSchedule);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//

//
//        EventSchedule event1 = new EventSchedule("Hồ Gươm","8:30",1, 6,"Đi chơi dui dẻ");
//        EventSchedule event2 = new EventSchedule("Quảng trường Ba Đình","8:30",2,6,"Đi chơi dui dẻ");
//
//
//
//        arrEvents.add(event1);
//        arrEvents.add(event2);
        Log.d(TAG, String.valueOf(arrEvents.size()));
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(arrEvents);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        scheduleList.setLayoutManager(linearLayoutManager);


        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        scheduleList.addItemDecoration(itemDecoration);

        scheduleList.setAdapter(scheduleAdapter);
//        scheduleAdapter.setOnItemClickListener(new AttractionAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Log.d(TAG, "onItemClick: Chuyen qua dau gio");
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_option_menu, menu);
        return true;
    }

}
