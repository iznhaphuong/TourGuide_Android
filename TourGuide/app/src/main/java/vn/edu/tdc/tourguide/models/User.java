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

<<<<<<< HEAD
import java.util.ArrayList;

=======
import vn.edu.tdc.tourguide.R;
>>>>>>> main
import vn.edu.tdc.tourguide.SideMenuActivity;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;

public class User {
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String idUser, nameOfUser, email, logoPersional;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getLogoPersional() {
        return logoPersional;
    }

    public void setLogoPersional(String logoPersional) {
        this.logoPersional = logoPersional;
    }

    public User() {
    }

    public User(String nameOfUser, String email) {
        this.nameOfUser = nameOfUser;
        this.email = email;
    }

    public User(String nameOfUser, String email, String logoPersional) {
        this.nameOfUser = nameOfUser;
        this.email = email;
        this.logoPersional = logoPersional;
    }

    public User(String id, String nameOfUser, String email, String logoPersional) {
        this.idUser = id;
        this.nameOfUser = nameOfUser;
        this.email = email;
        this.logoPersional = logoPersional;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", nameOfUser='" + nameOfUser + '\'' +
                ", email='" + email + '\'' +
                ", logoPersional='" + logoPersional + '\'' +
                '}';
    }
    public static void getUserFromList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_review");
        ArrayList<User> users = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
<<<<<<< HEAD
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
=======
                FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {

                    assert userCurrent != null;
                    if (userCurrent.getUid().equals(snapshot.getKey())) {
                        User user = snapshot.getValue(User.class);
                        assert user != null;
                        result.email = user.email;
                        result.nameOfUser = user.nameOfUser;

                        Glide.with(SideMenuActivity.headerLayout).load(userCurrent.getPhotoUrl()).error(R.drawable.user_logo).into(SideMenuActivity.imgAvatar);


                        SideMenuActivity.txtEmail.setText(user.getEmail());
                        SideMenuActivity.txtName.setText(user.getNameOfUser());
                        String TAG = "TAG";
                        Log.d(TAG, "onDataChange: da-" + userCurrent.toString());

                        break;
                    }

>>>>>>> main
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("OB", "Failed to read value.", error.toException());
            }
        });
    }
            // Get all City
            public static User getUser() {
                User result = new User();
                DatabaseReference myRef = database.getReference("users");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

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

//    public static User getUserWithId(String id) {
//        for (User user : users) {
//            if (user.getIdUser().equals(id)) {
//                return user;
//            }
//        }
//        return null;
//    }