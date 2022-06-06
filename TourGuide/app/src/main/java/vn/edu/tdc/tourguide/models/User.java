package vn.edu.tdc.tourguide.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User {
    private String idUser, nameOfUser, email;
    public static ArrayList<User> users = new ArrayList<>();
    public String getIdUser() {
        return idUser;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String nameOfUser, String email) {
        this.nameOfUser = nameOfUser;
        this.email = email;
    }

    public User(String id, String nameOfUser, String email) {
        this.idUser = id;
        this.nameOfUser = nameOfUser;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", nameOfUser='" + nameOfUser + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    public static void getUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_review");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("OB", "Failed to read value.", error.toException());
            }
        });
    }
    public static User getUserWithId(String id) {
        for (User user : users) {
            if (user.getIdUser().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
