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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.edu.tdc.tourguide.models.Destination;
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
    public String destinaionId;
    private DatabaseReference mDatabase;
    private String userEmail ;
    private String desID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule_layout);
        Intent intent = getIntent();
//        destinaionId = intent.getStringExtra(ScheduleFragment.EXTRA_ID);

        placeName = findViewById(R.id.placeName);
        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);
        btnCancel = findViewById(R.id.btnCancelEvent);
        btnDone = findViewById(R.id.btnDoneEvent);
        edtNote = findViewById(R.id.edtNote);
        desID = intent.getStringExtra(DetailScreenActivity.EXTRA_ID_DES);
        Destination desti = new Destination();

        Destination destination = desti.getDestination(desID);
        placeName.setText(destination.getName());


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule();
                clear();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });


    }
    private void addSchedule(){
        String nameDesti = placeName.getText().toString();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String timeEvent = hour+":"+minute;
        int dateEvent ;
        dateEvent= datePicker.getDayOfMonth();
        userEmail = SideMenuActivity.user.getEmail();
        int yearEvent ;
        yearEvent = datePicker.getYear();
        int monthEvent;//thang bat dau tu 0 hong biet nua
        monthEvent = datePicker.getMonth() +1;
        String noteEvent =  edtNote.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference("events/");
        // new event node would be /events/$eventId/
        String eventId = mDatabase.push().getKey();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentTime = ft.format(fullTime) ;//02/06/2022
        String[] separated = currentTime.split("/");
        String current = separated[0];

        if (Integer.parseInt(current)<=dateEvent&& Integer.parseInt(separated[1])<= monthEvent && Integer.parseInt(separated[2])<= yearEvent) {
            String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            String[] sepaTime = time.split(":");
            String curHour = sepaTime[0];
            Log.d("time","hour +" +curHour);
            if(hour>=Integer.parseInt(curHour) && minute>=Integer.parseInt(sepaTime[1])){
                EventSchedule newEvent = new EventSchedule(eventId, desID, userEmail, nameDesti, timeEvent, dateEvent, monthEvent, yearEvent, noteEvent);
                // pushing user to 'users' node using the userId
                mDatabase.child(eventId).setValue(newEvent);
                Toast toast =  Toast.makeText(this,"Thêm lịch trình thành công!!",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
                toast.show();
                // create intent to show Schedule Activity
                Intent intent = new Intent(this, SideMenuActivity.class);
                // start Main Activity
                startActivity(intent);
            }else{
                Toast toast =  Toast.makeText(this,"Ngày giờ của lịch trình không được nhỏ hơn ngày hiện tại!!",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
                toast.show();
                cancel();
            }
        }else{
            Toast toast =  Toast.makeText(this,"Ngày giờ của lịch trình không được nhỏ hơn ngày hiện tại!!",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
            toast.show();
            cancel();
        }




    }
    private void cancel(){
        clear();
        super.onBackPressed();

    }
    private void clear() {
        //TODO clear
        edtNote.setText("");

    }
}
