package vn.edu.tdc.tourguide.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    private String id, name, image;

    public static List<City> list = new ArrayList<City>();
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public City() {

    }

    public City(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public City(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    // Get all City
    public static void getCities() {
        DatabaseReference myRef = database.getReference("list_city");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    City city = snapshot.getValue(City.class);
                    list.add(city);
                }
                Log.d("MinhDuc", list.toString());
                //datanotyfi
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("OB", "Failed to read value.", error.toException());
            }
        });
    }


    // Add city in list
    public static void addCity(String name, String image) {
        DatabaseReference myRef = database.getReference("list_city");

        String id = myRef.push().getKey();
        City city = new City(id, name, image);
        myRef.child(id).setValue(city);
    }

    // Update city (Truyền id muốn sửa và các gtri mới)
    public static void updateCity(String idUpdate, String name, String image) {
        DatabaseReference myRef = database.getReference("list_city");

        City city = new City(name, image);
        myRef.child(idUpdate).updateChildren(city.toMap());
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("image", image);
        return result;
    }

    // Delete city
    public static void deleteCity(String idDelete) {
        DatabaseReference myRef = database.getReference("list_city");

        myRef.child(idDelete).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity, "alo", Toast.LENGTH_LONG).show();
                Log.d("Thongbao", "Xóa city thành công");
            }
        });
    }

    //Dựa vào name ảnh ở Storage firebase lấy ra ảnh (vd: truyền vào 2 tham số boy.png và ImageView)
    public static void getImage(String nameImg, ImageView imageView){
        // Create a Cloud Storage reference from the app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("picture/"+nameImg);

        try {
            String[] split = nameImg.split("\\.");
            final File localFile = File.createTempFile(split[0], split[1]);
            storageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(MainActivity.this, "ERROR Connect Image", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
