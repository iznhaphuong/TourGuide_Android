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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    String destinationId;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_schedule_layout);
        Intent intent = getIntent();
        scheduleId = intent.getStringExtra(ScheduleFragment.EXTRA_ID);
        Log.d("id", "event has id " + scheduleId);
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
                int month = eventSchedule.getMonthEvent() - 1;
                placeName.setText(eventSchedule.getNameDestination());
                destinationId = eventSchedule.getDestinationId();
                datePicker.init(eventSchedule.getYearEvent(), month, eventSchedule.getDateEvent(), null);


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
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }


    private void updateSchedule() {

        String userEmail = SideMenuActivity.user.getEmail();
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
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentTime = ft.format(fullTime);//02/06/2022
        String[] separated = currentTime.split("/");
        String current = separated[0];
        if (Integer.parseInt(current) <= dateEvent && Integer.parseInt(separated[1]) <= monthEvent && Integer.parseInt(separated[2]) <= yearEvent) {
            String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            String[] sepaTime = time.split(":");
            String curHour = sepaTime[0];

            if (Integer.parseInt(current) == dateEvent) {
                if (hour >= Integer.parseInt(curHour)) {

                    if (minute >= Integer.parseInt(sepaTime[1])) {
                        EventSchedule updateEvent = new EventSchedule(scheduleId, destinationId, userEmail, nameDesti, timeEvent, dateEvent, monthEvent, yearEvent, noteEvent);

                        // pushing user to 'users' node using the userId
                        mDatabase.child(scheduleId).setValue(updateEvent);
                        // create intent to show Schedule Activity

                        SideMenuActivity.checkSchedule = true;
                        Intent intent = new Intent(this, SideMenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);

                        Toast toast = Toast.makeText(this, "Sửa lịch trình thành công!!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
                        toast.show();
                        clear();
                    } else {
                        Toast toast = Toast.makeText(this, "Ngày giờ của lịch trình không được nhỏ hơn ngày hiện tại!!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
                        toast.show();
                        cancel();

                    }
                }
            } else {
                EventSchedule updateEvent = new EventSchedule(scheduleId, destinationId, userEmail, nameDesti, timeEvent, dateEvent, monthEvent, yearEvent, noteEvent);
                mDatabase.child(scheduleId).setValue(updateEvent);
                // create intent to show Schedule Activity
                Log.d("time", "hour +" + curHour);
                Log.d("hour", "hour+ " + hour);
                SideMenuActivity.checkSchedule = true;
                Intent intent = new Intent(this, SideMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

                Toast toast = Toast.makeText(this, "Sửa lịch trình thành công!!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
                toast.show();
                clear();
            }


        } else {
            Toast toast = Toast.makeText(this, "Ngày giờ của lịch trình không được nhỏ hơn ngày hiện tại!!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
            toast.show();
            cancel();
        }


    }

    private void clear() {
        //TODO clear
        edtNote.setText("");
    }

    private void cancel() {
        clear();
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SideMenuActivity.checkSchedule) {
            // create intent to show Schedule Activity

            Intent intent = new Intent(this, SideMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

            Toast toast = Toast.makeText(this, "Sửa lịch trình thành công!!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
            toast.show();
            clear();
        }
    }
}
