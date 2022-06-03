package vn.edu.tdc.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        CityModel.getCities();
//        CityModel.addCity("HCM", "boy.png");
//        CityModel.updateCity("1","Ha Noi", "boy.png");
//        CityModel.deleteCity("1");

//        DestinationModel.getDestination();
//        DestinationModel.addDestination("1", "Hoành Thành Thăng Long", "Du lịch","boy.png", 325245, 324234, "19C Hoàng Diệu, Điện Bàn, Ba Đình, Hà Nội", 5);
//        DestinationModel.updateDestination("2", "1", "Lăng Bác và Quảng Trường Ba Đình", "Chợ","boy.png", 3253245, 324234, "2 Hùng Vương, Điện Bà, Ba Đình, Hà Nội", 5);
//        DestinationModel.deleteDestination("2");

//        ReviewModel.getReview();
//        ReviewModel.addReview("1", "duc", "nguyenminhduc.tdc2020@gmail.com", "Tour rat tuyet", 5);
//        ReviewModel.updateReview("3", "1", "minh", "nguyenminhduc.tdc2020@gmail.com", "Tour tổ chức tốt", 5);
//        ReviewModel.deleteReview("3");

//        ScheduleModel.getSchedule();
//        ScheduleModel.addSchedule("nguyenminhduc.tdc2020@gmail.com", "12/04/2021", "du lịch", "2");
//        ScheduleModel.updateSchedule("5","nguyenminhduc.tdc2020@gmail.com", "12/04/2022", "Đi biển", "2");
//        ScheduleModel.deleteSchedule("5");
    }
}