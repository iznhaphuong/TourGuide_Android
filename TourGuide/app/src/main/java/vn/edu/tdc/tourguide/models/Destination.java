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

import vn.edu.tdc.tourguide.AttractionActivity;

public class Destination {
    private String id;
    private String city_id;
    private String name;
    private String type;
    private String image;
    private long xLat;
    private long yLong;
    private String address;
    private int rating;

    public static List<Destination> list = new ArrayList<Destination>();
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setxLat(long xLat) {
        this.xLat = xLat;
    }

    public void setyLong(long yLong) {
        this.yLong = yLong;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public long getxLat() {
        return xLat;
    }

    public long getyLong() {
        return yLong;
    }

    public String getAddress() {
        return address;
    }

    public int getRating() {
        return rating;
    }

    public Destination() {

    }

    public Destination(String city_id, String name, String type, String image, long xLat, long yLong, String address, int rating) {
        this.city_id = city_id;
        this.name = name;
        this.type = type;
        this.image = image;
        this.xLat = xLat;
        this.yLong = yLong;
        this.address = address;
        this.rating = rating;
    }

    public Destination(String id, String city_id, String name, String type, String image, long xLat, long yLong, String address, int rating) {
        this.id = id;
        this.city_id = city_id;
        this.name = name;
        this.type = type;
        this.image = image;
        this.xLat = xLat;
        this.yLong = yLong;
        this.address = address;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "DestinationModel{" +
                "id=" + id +
                ", city_id=" + city_id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", xLat=" + xLat +
                ", yLong=" + yLong +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                '}';
    }

    // Get all Destination
    public static void getDestination() {
        DatabaseReference myRef = database.getReference("list_destination");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    Destination destination = snapshot.getValue(Destination.class);
                    list.add(destination);
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

    public static Destination getDestination(String id) {
        for (Destination destination : list) {
            if (destination.getId().equals(id)) {
                return destination;
            }
        }
        return null;
    }


    // Add Destination in list
    public static void addDestination(String city_id, String name, String type, String image, long xLat, long yLong, String address, int rating) {
        DatabaseReference myRef = database.getReference("list_destination");

        String id = myRef.push().getKey();
        Destination destination = new Destination(id, city_id, name, type, image, xLat, yLong, address, rating);
        myRef.child(id).setValue(destination);
    }

    // Update Destination (Truyền id vào và chuyền các giá trị muốn sửa)
    public static void updateDestination(String idUpdate, String city_id, String name, String type, String image, long xLat, long yLong, String address, int rating) {
        DatabaseReference myRef = database.getReference("list_destination");

        Destination destination = new Destination(city_id, name, type, image, xLat, yLong, address, rating);
        myRef.child(idUpdate).updateChildren(destination.toMap());
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("city_id", city_id);
        result.put("name", name);
        result.put("type", type);
        result.put("image", image);
        result.put("xLat", xLat);
        result.put("yLong", yLong);
        result.put("address", address);
        result.put("rating", rating);
        return result;
    }

    // Delete Destination
    public static void deleteDestination(String idDelete) {
        DatabaseReference myRef = database.getReference("list_destination");

        myRef.child(idDelete).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity, "alo", Toast.LENGTH_LONG).show();
                Log.d("Thongbao", "Xóa thành công");
            }
        });
    }
}
