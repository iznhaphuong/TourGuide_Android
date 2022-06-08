package vn.edu.tdc.tourguide.models;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.SideMenuActivity;

public class User {
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    String email, logoPersional, nameOfUser;

    public User() {
    }

    public User(String email, String logoPersional, String nameOfUser) {
        this.email = email;
        this.logoPersional = logoPersional;
        this.nameOfUser = nameOfUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogoPersional() {
        return logoPersional;
    }

    public void setLogoPersional(String logoPersional) {
        this.logoPersional = logoPersional;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("logoPersional", logoPersional);
        result.put("nameOfUser", nameOfUser);
        return result;
    }


    // Get all City
    public static void getUser() {
        String TAG = "TAG";
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {

                    assert userCurrent != null;
                    if (userCurrent.getUid().equals(snapshot.getKey())) {
//                        Log.d(TAG, "onDataChange: " + snapshot.getValue(User.class).email);
                        User user = snapshot.getValue(User.class);
                        assert user != null;
//
                        Glide.with(SideMenuActivity.headerLayout).load(user.getLogoPersional()).error(R.drawable.avarta2).into(SideMenuActivity.imgAvatar);
                        SideMenuActivity.user = user;
//
                        SideMenuActivity.txtEmail.setText(user.getEmail());
                        SideMenuActivity.txtName.setText(user.getNameOfUser());
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("OB", "Failed to read value.", error.toException());
            }
        });
    }

    public static void updateUser(String idUpdate, String email, String logoPersional, String nameOfUser) {
        DatabaseReference myRef = database.getReference("users");

        User user = new User(email, logoPersional, nameOfUser);
        myRef.child(idUpdate).updateChildren(user.toMap());
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", logoPersional='" + logoPersional + '\'' +
                ", nameOfUser='" + nameOfUser + '\'' +
                '}';
    }
}
