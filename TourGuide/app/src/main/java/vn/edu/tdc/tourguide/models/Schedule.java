package vn.edu.tdc.tourguide.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private String id, email, datetime, note, destination_id;

    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public Schedule() {

    }

    public Schedule(String email, String datetime, String note, String destination_id) {
        this.email = email;
        this.datetime = datetime;
        this.note = note;
        this.destination_id = destination_id;
    }

    public Schedule(String id, String email, String datetime, String note, String destination_id) {
        this.id = id;
        this.email = email;
        this.datetime = datetime;
        this.note = note;
        this.destination_id = destination_id;
    }

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", datetime='" + datetime + '\'' +
                ", note='" + note + '\'' +
                ", destination_id=" + destination_id +
                '}';
    }

    // Get all Schedule
    public static void getSchedule() {
        DatabaseReference myRef = database.getReference("list_schedule");
        List<Schedule> list = new ArrayList<Schedule>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    Schedule schedule = snapshot.getValue(Schedule.class);
                    list.add(schedule);
                }
                Log.d("MinhDuc", list.toString());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("OB", "Failed to read value.", error.toException());
            }
        });
    }


    // Add Schedule in list
    public static void addSchedule(String email, String datetime, String note, String destination_id) {
        DatabaseReference myRef = database.getReference("list_schedule");

        String id = myRef.push().getKey();
        Schedule schedule = new Schedule(id, email, datetime, note, destination_id);
        myRef.child(id).setValue(schedule);
    }

    // Update Schedule (Truyền id vào và chuyền các giá trị muốn sửa)
    public static void updateSchedule(String idUpdate, String email, String datetime, String note, String destination_id) {
        DatabaseReference myRef = database.getReference("list_schedule");

        Schedule schedule = new Schedule(email, datetime, note, destination_id);
        myRef.child(idUpdate).updateChildren(schedule.toMap());
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("datetime", datetime);
        result.put("note", note);
        result.put("destination_id", destination_id);
        return result;
    }

    // Delete Schedule
    public static void deleteSchedule(String idDelete) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // From table name city
        DatabaseReference myRef = database.getReference("list_schedule");

        myRef.child(idDelete).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity, "alo", Toast.LENGTH_LONG).show();
                Log.d("Thongbao", "Xóa thành công");
            }
        });
    }
}