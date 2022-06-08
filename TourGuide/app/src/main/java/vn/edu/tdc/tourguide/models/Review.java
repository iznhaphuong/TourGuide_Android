package vn.edu.tdc.tourguide.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Review {

    private String id;
    private String destination_id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String userName;
    private String email;



    private static int sort;

    public int getSapXep() {
        return sapXep;
    }

    public void setSapXep(int sapXep) {
        this.sapXep = sapXep;
    }

    private int sapXep;

    public Review(String id, String destination_id, String userName, String email, String content, String timeReview, float rating) {
        sort++;
        this.id = id;
        this.destination_id = destination_id;
        this.userName = userName;
        this.email = email;
        this.content = content;
        this.timeReview = timeReview;
        this.rating = rating;
        this.sapXep=sort;
    }

    private String content;
    private String timeReview;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private float rating;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeReview() {
        return timeReview;
    }

    public void setTimeReview(String timeReview) {
        this.timeReview = timeReview;
    }





    public static List<Review> list = new ArrayList<Review>();
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();



    public Review() {

    }



//    @Override
//    public String toString() {
//        return "reviewModel{" +
//                "id=" + id +
//                ", destination_id=" + destination_id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", content='" + content + '\'' +
//                ", rating=" + rating +
//                '}';
//    }

    // Get all Review
    public static void getReview(String destination_id) {
        DatabaseReference myRef = database.getReference("list_review");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    Review review = snapshot.getValue(Review.class);
                    if(review.getDestination_id().equals(destination_id)){
                        list.add(review);
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
//    public static Review getReview(String destination_id) {
//        DatabaseReference myRef = database.getReference("list_review");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
//                    Review review = snapshot.getValue(Review.class);
//                    list.add(review);
//                }
//                Log.d("MinhDuc", list.toString());
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.d("OB", "Failed to read value.", error.toException());
//            }
//        });
//    }

    // Add review in list
    public static void addReview(String destination_id, String userName,String email, String content, String timeReview, float rating) {
       DatabaseReference myRef;
        // From table name city
        myRef = FirebaseDatabase.getInstance().getReference("list_review");

        String id = myRef.push().getKey();

        Review reviewModel = new Review(id,destination_id,userName,email, content,timeReview, rating);
        if(id != null){
            myRef.child(id).setValue(reviewModel);
        }else {
            Log.d("addReview","addReview"+id);
        }

    }

    // Update review (Truyền id vào và chuyền các giá trị muốn sửa)
    public static void updateReview(String id,String destination_id, String userName,String email, String content, String timeReview, float rating) {
        DatabaseReference myRef = database.getReference("list_review");

        Review review = new Review(id,destination_id,userName,email, content,timeReview, rating);
        myRef.child(id).setValue(review);
    }

//    public Map<String, Object> toMap(){
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("destination_id", destination_id);
//        result.put("userId", userId);
//        result.put("timeReview", timeReview);
//        result.put("content", content);
//        result.put("rating", rating);
//        return result;
//    }

    // Delete review
    public static void deleteReview(String idDelete) {
        DatabaseReference myRef = database.getReference("list_review");

        myRef.child(idDelete).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity, "alo", Toast.LENGTH_LONG).show();
                Log.d("Thongbao", "Xóa thành công");
            }
        });
    }
}
