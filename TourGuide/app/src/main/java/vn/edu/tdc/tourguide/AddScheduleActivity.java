package vn.edu.tdc.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.tdc.tourguide.models.EventSchedule;
import vn.edu.tdc.tourguide.ui.schedule.ScheduleFragment;


public class AddScheduleActivity extends AppCompatActivity {
    public String EVENT = "event";
    public TextView placeName;
    public DatePicker datePicker;
    public TimePicker timePicker;
    public Button btnCancel;
    public Button btnDone;
    public EditText edtNote;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule_layout);


        placeName = findViewById(R.id.placeName);
        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);
        btnCancel = findViewById(R.id.btnCancelEvent);
        btnDone = findViewById(R.id.btnDoneEvent);
        edtNote = findViewById(R.id.edtNote);


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule();
                clear();
            }
        });




    }
    private void addSchedule(){


        String nameDesti = placeName.getText().toString();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String timeEvent = hour+":"+minute;
        int dateEvent = datePicker.getDayOfMonth();
        int yearEvent = datePicker.getYear();
        int monthEvent = datePicker.getMonth() +1;//thang bat dau tu 0 hong biet nua
        String noteEvent =  edtNote.getText().toString();





        mDatabase = FirebaseDatabase.getInstance().getReference("events/");
        // new event node would be /events/$eventId/
        String eventId = mDatabase.push().getKey();
        EventSchedule newEvent = new EventSchedule(eventId,nameDesti,timeEvent,dateEvent,monthEvent,yearEvent,noteEvent);
        // pushing user to 'users' node using the userId
        mDatabase.child(eventId).setValue(newEvent);
        Toast toast =  Toast.makeText(this,"Add event to schedule successfully!!",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
        toast.show();


        // create intent to show Schedule Activity
        Intent intent = new Intent(this, SideMenuActivity.class);
        // start Main Activity
        startActivity(intent);
    }
    private void clear() {
        //TODO clear
        edtNote.setText("");
    }
}
