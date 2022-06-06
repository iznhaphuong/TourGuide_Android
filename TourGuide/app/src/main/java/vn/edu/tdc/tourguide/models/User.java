package vn.edu.tdc.tourguide.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.edu.tdc.tourguide.SideMenuActivity;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;

public class User {
    private String idUser, nameOfUser, email, logoPerson;
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

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


    public String getLogoPerson() {
        return logoPerson;
    }

    public void setLogoPerson(String logoPerson) {
        this.logoPerson = logoPerson;
    }


    public User() {}

    public User(String nameOfUser, String email) {
        this.nameOfUser = nameOfUser;
        this.email = email;
    }

    public User(String id, String nameOfUser, String email, String logoPerson) {
        this.idUser = id;
        this.nameOfUser = nameOfUser;
        this.email = email;
        this.logoPerson = logoPerson;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", nameOfUser='" + nameOfUser + '\'' +
                ", email='" + email + '\'' +
                ", logoPerson='" + logoPerson + '\'' +
                '}';
    }

    // Get all City
    public static User getUser() {
        User result = new User();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {

                    assert userCurrent != null;
                    if (userCurrent.getUid().equals(snapshot.getKey())) {
                        User user = snapshot.getValue(User.class);
                        assert user != null;
                        result.email = user.email;
                        result.nameOfUser = user.nameOfUser;

                        SideMenuActivity.txtEmail.setText(user.getEmail());
                        SideMenuActivity.txtName.setText(user.getNameOfUser());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("OB", "Failed to read value.", error.toException());
            }
        });
        return result;
    }
}
