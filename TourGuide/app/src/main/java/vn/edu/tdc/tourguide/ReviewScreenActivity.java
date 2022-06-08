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

import com.bumptech.glide.Glide;
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
import vn.edu.tdc.tourguide.models.Comments;
import vn.edu.tdc.tourguide.models.Review;
import vn.edu.tdc.tourguide.models.User;

public class ReviewScreenActivity extends AppCompatActivity {
    public static RecyclerView rcvComment;
    private List<Comments> myCommentList;
    private CommentsAdapter commentsAdapter;
    private TextView userNamne;
    private RatingBar ratingBar;
    private EditText edtCmt;
    private ImageView imgUser;
    private Button btnSend;
    private String desID;
    private DatabaseReference mDatabase;
    private TextView amountReview;
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
        Log.d("desDetail","desDetail+ "+desID);
        userNamne.setText(SideMenuActivity.user.getNameOfUser());
        Glide.with(this).load(SideMenuActivity.user.getLogoPersional()).error(R.drawable.avarta2).into(imgUser);

        rcvComment = findViewById(R.id.rcv_cmt);
        myCommentList = new ArrayList<>();

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
                            String nameOfUser = "";
                            String imgUrl = "";
                            Log.d("desID"," des+ " + review.getDestination_id());

                            if(review.getDestination_id().equals(desID)){

                                Log.d("Users1","user1 "+ users.size() );
                                for (User user : users) {

                                    if (user.getEmail().equals(review.getEmail())) {
                                        Log.d("User","user+ " + user.getNameOfUser());
                                        nameOfUser = user.getNameOfUser();
                                        imgUrl = user.getLogoPersional();

                                    }
                                }
                                Comments newComment = new Comments(nameOfUser, imgUrl, review.getRating(),review.getTimeReview(),review.getContent());
                                myCommentList.add(newComment);

                            }
                        }
                        amountReview.setText(String.valueOf(myCommentList.size()+ " Đánh giá "));
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
//                Intent intent = new Intent(ReviewScreenActivity.this, DetailScreenActivity.class);
//                DetailScreenActivity.id = desID;
//
//                startActivity(intent);
            }
        });
    }
    private void sendReview(){
         Review review = new Review();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentTime = ft.format(fullTime) ;//02/06/2022
        String userName = userNamne.getText().toString();
        String email = SideMenuActivity.user.getEmail();
        String comment = edtCmt.getText().toString();
        float rating = ratingBar.getRating();
        Log.d("des","Send desId "+desID);
        review.addReview(desID,userName,email, comment,currentTime, rating);
        Toast toast =  Toast.makeText(this,"Thêm đánh giá thành công!!",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
        toast.show();

    }
}
