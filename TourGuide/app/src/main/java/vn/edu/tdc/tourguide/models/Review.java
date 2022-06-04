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

public class Review {
    private String id;
    private String destination_id;
    private String name;
    private String email;
    private String content;
    private int rating;

    public static List<Review> list = new ArrayList<Review>();
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String getId() {
        return id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Review() {

    }

    public Review(String destination_id, String name, String email, String content, int rating) {
        this.destination_id = destination_id;
        this.name = name;
        this.email = email;
        this.content = content;
        this.rating = rating;
    }

    public Review(String id, String destination_id, String name, String email, String content, int rating) {
        this.id = id;
        this.destination_id = destination_id;
        this.name = name;
        this.email = email;
        this.content = content;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "reviewModel{" +
                "id=" + id +
                ", destination_id=" + destination_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                '}';
    }

    // Get all Review
    public static void getReview() {
        DatabaseReference myRef = database.getReference("list_review");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    Review review = snapshot.getValue(Review.class);
                    list.add(review);
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


    // Add review in list
    public static void addReview(String destination_id, String name, String email, String content, int rating) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // From table name city
        DatabaseReference myRef = database.getReference("list_review");

        String id = myRef.push().getKey();
        Review reviewModel = new Review(id, destination_id, name, email, content, rating);
        myRef.child(id).setValue(reviewModel);
    }

    // Update review (Truyền id vào và chuyền các giá trị muốn sửa)
    public static void updateReview(String idUpdate, String destination_id, String name, String email, String content, int rating) {
        DatabaseReference myRef = database.getReference("list_review");

        Review review = new Review(destination_id, name, email, content, rating);
        myRef.child(idUpdate).updateChildren(review.toMap());
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("destination_id", destination_id);
        result.put("name", name);
        result.put("email", email);
        result.put("content", content);
        result.put("rating", rating);
        return result;
    }

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
