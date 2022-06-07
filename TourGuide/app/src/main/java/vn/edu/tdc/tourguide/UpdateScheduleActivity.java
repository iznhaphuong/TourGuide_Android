package vn.edu.tdc.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.edu.tdc.tourguide.models.EventSchedule;
import vn.edu.tdc.tourguide.ui.schedule.ScheduleFragment;

public class UpdateScheduleActivity extends AppCompatActivity {
    public TextView placeName;
    public DatePicker datePicker;
    public TimePicker timePicker;
    public Button btnCancel;
    public Button btnDone;
    public EditText edtNote;
    public String TAG = "ERROR";
    String scheduleId;
    String destinationId ;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_schedule_layout);
        Intent intent = getIntent();
        scheduleId = intent.getStringExtra(ScheduleFragment.EXTRA_ID);
        Log.d("id","event has id "+scheduleId);
        mDatabase = FirebaseDatabase.getInstance().getReference("events");

        placeName = findViewById(R.id.update_place_name);
        datePicker = findViewById(R.id.update_date_picker);
        timePicker = findViewById(R.id.update_time_picker);
        btnCancel = findViewById(R.id.update_btnCancelEvent);
        btnDone = findViewById(R.id.update_btnDoneEvent);
        edtNote = findViewById(R.id.update_edtNote);
        btnDone = findViewById(R.id.update_btnDoneEvent);
        btnCancel = findViewById(R.id.update_btnCancelEvent);
        mDatabase.child(scheduleId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EventSchedule eventSchedule = snapshot.getValue(EventSchedule.class);
                String currentString = eventSchedule.getTimeEvent();
                String[] time = currentString.split(":");
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);
                int month = eventSchedule.getMonthEvent()-1;
                placeName.setText(eventSchedule.getNameDestination());
                destinationId = eventSchedule.getDestinationId();
                datePicker.init(eventSchedule.getYearEvent(),month,eventSchedule.getDateEvent(),null);
                Log.d("date","date update event: "+eventSchedule.getDateEvent());
                Log.d("month","month update event: "+(eventSchedule.getMonthEvent()-1));
                Log.d("year","year update event: "+eventSchedule.getYearEvent());

                datePicker.setSelected(true);
                timePicker.setHour(hour);
                timePicker.setMinute(minute);
                edtNote.setText(eventSchedule.getNoteEvent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule();
                clear();
                Intent intent = new Intent(UpdateScheduleActivity.this, SideMenuActivity.class);
                // start Main Activity
                startActivity(intent);

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }
    private void clear() {
        //TODO clear
        edtNote.setText("");
    }
    private void cancel(){
        clear();
        super.onBackPressed();
    }
    private void updateSchedule() {

        String userEmail =SideMenuActivity.user.getEmail();
        String nameDesti = placeName.getText().toString();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String timeEvent = hour + ":" + minute;
        int dateEvent = datePicker.getDayOfMonth();
        int yearEvent = datePicker.getYear();
        int monthEvent = datePicker.getMonth() + 1;//thang bat dau tu 0 hong biet nua
        String noteEvent = edtNote.getText().toString();


        mDatabase = FirebaseDatabase.getInstance().getReference("events/");
        // new event node would be /events/$eventId/
        EventSchedule updateEvent = new EventSchedule(scheduleId,destinationId,userEmail, nameDesti, timeEvent, dateEvent, monthEvent, yearEvent, noteEvent);

        // pushing user to 'users' node using the userId
        mDatabase.child(scheduleId).setValue(updateEvent);
        // create intent to show Schedule Activity

        Toast toast = Toast.makeText(this, "Sửa lịch trình thành công!!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
        toast.show();






    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}