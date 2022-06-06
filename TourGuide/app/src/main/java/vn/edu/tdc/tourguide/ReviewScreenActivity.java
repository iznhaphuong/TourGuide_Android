package vn.edu.tdc.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.edu.tdc.tourguide.adapter.CommentsAdapter;
import vn.edu.tdc.tourguide.adapter.ScheduleAdapter;
import vn.edu.tdc.tourguide.models.Comments;
import vn.edu.tdc.tourguide.models.EventSchedule;
import vn.edu.tdc.tourguide.models.Review;
import vn.edu.tdc.tourguide.models.User;
import vn.edu.tdc.tourguide.ui.schedule.ScheduleFragment;

public class ReviewScreenActivity extends AppCompatActivity {
    private RecyclerView rcvComment;
    private List<Comments> myCommentList;
    private CommentsAdapter commentsAdapter;
    private TextView userNamne;
    private RatingBar ratingBar;
    private EditText edtCmt;
    private ImageView imgUser;
    private String userId = "4hj4s4ov9lb6tq2LGRb2Q2IusWI3";
    private Button btnSend;
    private String desID;
    private DatabaseReference mDatabase;
    private TextView amountReview;
    public static String EXTRA_ID_DES_REVIEW = "EXTRA_ID_DES_REVIEW";
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_screen);

        userNamne = findViewById(R.id.review_user_name);
        ratingBar = findViewById(R.id.review_rating);
        edtCmt = findViewById(R.id.review_edtComment);
        imgUser = findViewById(R.id.review_imgUser);
        btnSend = findViewById(R.id.btnSendReview);
        amountReview = findViewById(R.id.amountCmt);
        Intent intent = getIntent();
        desID = intent.getStringExtra(DetailScreenActivity.EXTRA_ID_DES);


        rcvComment = findViewById(R.id.rcv_cmt);
        myCommentList = new ArrayList<>();
        //du lieu gia
//        Comments comment1 = new Comments("Christiana", R.drawable.avarta2, "17 April 2015", "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an ", 3);
//        Comments comment2 = new Comments("Chrana", R.drawable.avarta2, "17 April 2015", "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an ", 3);
//        Comments comment3 = new Comments("Boulkd Che", R.drawable.avarta2, "17 April 2015", "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an ", 3);
//        myCommentList.add(comment1);
//        myCommentList.add(comment2);
//        myCommentList.add(comment3);
        ArrayList<User> users = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);

                }
////                Load database
                mDatabase = FirebaseDatabase.getInstance().getReference("list_review");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        myCommentList.clear();
                        Log.d("Users1","user1 "+ users.size() );
                        Log.d("desID"," des+ " + desID);
                        for (DataSnapshot snapshot: datasnapshot.getChildren() ) {
                            Review review = snapshot.getValue(Review.class);

                            Log.d("desID"," des+ " + desID);

                            if(review.getDestination_id().equals(desID)){

                                Log.d("Users1","user1 "+ users.size() );
                                for (User user : users) {
                                    Log.d("desID","review des+ " + user.getIdUser());
                                    if (user.getEmail().equals(review.getEmail())) {
                                        Log.d("User","user+ " + user.getNameOfUser());
                                        Comments newComment = new Comments(user.getNameOfUser(),review.getRating(),review.getTimeReview(),review.getContent());
                                        myCommentList.add(newComment);
                                    }
                                }

                            }
                        }
                        commentsAdapter = new CommentsAdapter(myCommentList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReviewScreenActivity.this,  LinearLayoutManager.VERTICAL, false);
                        rcvComment.setLayoutManager(linearLayoutManager);
                        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(ReviewScreenActivity.this, DividerItemDecoration.VERTICAL);
                        rcvComment.addItemDecoration(decoration);
                        rcvComment.setAdapter(commentsAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("ERROR", "Failed to read value.", error.toException());
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("OB", "Failed to read value.", error.toException());
            }
        });




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReview();
                Intent intent = new Intent(ReviewScreenActivity.this, DetailScreenActivity.class);
                intent.putExtra(EXTRA_ID_DES_REVIEW, desID);

                startActivity(intent);
            }
        });
    }
    private void sendReview(){
         Review review = new Review();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentTime = ft.format(fullTime) ;//02/06/2022
        String userName = userNamne.getText().toString();
        String comment = edtCmt.getText().toString();
        float rating = ratingBar.getRating();
        Log.d("des","Send desId "+desID);
        review.addReview(desID,userId,userName, comment,currentTime, rating);
        Toast toast =  Toast.makeText(this,"Add review successfully!!",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
        toast.show();

    }
}
