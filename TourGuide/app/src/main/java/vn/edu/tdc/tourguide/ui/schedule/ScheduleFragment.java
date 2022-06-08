package vn.edu.tdc.tourguide.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.SideMenuActivity;
import vn.edu.tdc.tourguide.UpdateScheduleActivity;
import vn.edu.tdc.tourguide.adapter.ScheduleAdapter;
import vn.edu.tdc.tourguide.databinding.ScheduleLayoutBinding;
import vn.edu.tdc.tourguide.models.EventSchedule;


public class ScheduleFragment extends Fragment {
    private ScheduleLayoutBinding binding;
    private TextView currentDate;
    private DatabaseReference mDatabase;
    private CardView priviousItem;


    private ScheduleAdapter.OnItemClickListener onItemClickListener;
    private String keyEvent = "-1";// cờ để xem đang chọn view nào
    private int backcolor;// biến lưu màu cũ để khi ko chọn nữa thì trả về màu cũ
    public static ScheduleAdapter scheduleAdapter;
    public String TAG = "ERROR";

    public static final String EXTRA_ID = "ID";

    TextView nameDestination;
    TextView noteEvent;
    TextView timeEvent;
    TextView dateEvent;
    TextView monthEvent;
    Button btnXoa;
    Button btnSua;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ScheduleLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Create current time
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentTime = ft.format(fullTime);//02/06/2022
        String[] separated = currentTime.split("/");
        String current = separated[0];
        currentDate = binding.currentDate;
        currentDate.setText(currentTime);
        String TAG = "schedule";
        // Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();
        String userEmail = SideMenuActivity.user.getEmail();


        RecyclerView scheduleList = binding.scheduleList;
        ArrayList<EventSchedule> arrEvents = new ArrayList<>();
        ArrayList<EventSchedule> userEvents = new ArrayList<>();
        //Load database
        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        Query query = mDatabase.orderByChild("monthEvent");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrEvents.clear();
                userEvents.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    EventSchedule eventSchedule = snap.getValue(EventSchedule.class);
                    arrEvents.add(eventSchedule);
                }
                for (EventSchedule event : arrEvents) {
                    Log.d(TAG, "log+" + arrEvents.size());
                    //If current pass date of event
                    if (Integer.parseInt(current) <= event.getDateEvent() && Integer.parseInt(separated[1]) <= event.getMonthEvent() && Integer.parseInt(separated[2]) <= event.getYearEvent()) {
                        if (event.getUserEmail().equals(userEmail)) {
                            userEvents.add(event);
                        }
                    }
                }

                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(userEvents);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
                scheduleList.setLayoutManager(linearLayoutManager);
                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
                scheduleList.addItemDecoration(itemDecoration);
                scheduleAdapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(EventSchedule eventSchedule, int position) {
                        Log.d("onClick", "click " + position);
                        //Đổi màu cho item
                        if (keyEvent.equals("-1")) {
                            Log.d("seletedGrow1", "seletedGrow " + position);
                            keyEvent = eventSchedule.getScheduleId();
                            Log.d("seletedGrow2", "seletedGrow2 " + keyEvent);
                        } else {
                            if (keyEvent.equals(eventSchedule.getScheduleId())) {
                                keyEvent = "-1";
                            } else {
                                keyEvent = eventSchedule.getScheduleId();
                            }
                        }
                    }
                });
                scheduleList.setAdapter(scheduleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.schedule_option_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //truong hop co the them hoac khong
    private void disableBtn() {
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);

    }

    private void enableBtn() {
        btnXoa.setEnabled(true);
        btnSua.setEnabled(true);
    }

    //xử lý hàm menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteEventSchedule();
                return true;
            case R.id.menu_update:
                updateEventSchedule();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateEventSchedule() {
        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        //TODO
        if (!keyEvent.equals("1")) {
            Log.d(TAG, "updateEventSchedule: 11s: " + keyEvent);
            if (keyEvent.equals("-1")) {
                keyEvent = UpdateScheduleActivity.scheduleId;
            }
            mDatabase.child(keyEvent).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    EventSchedule eventSchedule = dataSnapshot.getValue(EventSchedule.class);
                    Intent intent = new Intent(binding.getRoot().getContext(), UpdateScheduleActivity.class);
                    Log.d(TAG, "onDataChange: " + UpdateScheduleActivity.scheduleId);
//                intent.putExtra(EXTRA_TITLE, eventSchedule.getNameDestination() );
                    intent.putExtra(EXTRA_ID, eventSchedule.getScheduleId());
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
    }

    public void deleteEventSchedule() {
        if (!keyEvent.equals("-1")) {
            Log.d(TAG, "deleteEventSchedule: 123asd-" + keyEvent);
            mDatabase = FirebaseDatabase.getInstance().getReference("events");
            mDatabase.child(keyEvent).removeValue();
            Toast toast = Toast.makeText(binding.getRoot().getContext(), "Xóa lịch trình thành công!!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
            toast.show();
        } else {
            Toast toast = Toast.makeText(binding.getRoot().getContext(), "Vui lòng chọn một sự kiện để xóa!!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.RIGHT, 20, 40);
            toast.show();

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
