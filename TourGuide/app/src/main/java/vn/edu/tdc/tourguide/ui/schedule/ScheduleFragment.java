package vn.edu.tdc.tourguide.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.edu.tdc.tourguide.adapter.ScheduleAdapter;
import vn.edu.tdc.tourguide.databinding.ScheduleLayoutBinding;
import vn.edu.tdc.tourguide.modle.EventSchedule;

public class ScheduleFragment extends Fragment {
    private ScheduleLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ScheduleLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date fullTime = Calendar.getInstance().getTime();
        String currentDate = ft.format(fullTime) ;//02/06/2022

        Log.d("timeHH", String.valueOf(fullTime));
        Log.d("current",currentDate);
        // Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();

        RecyclerView scheduleList = binding.scheduleList;

        ArrayList<EventSchedule> arrEvents = new ArrayList<>();

        EventSchedule event1 = new EventSchedule(1,"Hồ Gươm","8:30","01/06/2022","Đi chơi dui dẻ");
        EventSchedule event2 = new EventSchedule(2,"Quảng trường Ba Đình","8:30","02/06/2022","Đi chơi dui dẻ");



        arrEvents.add(event1);
        arrEvents.add(event2);

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(arrEvents);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        scheduleList.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        scheduleList.addItemDecoration(itemDecoration);

        scheduleList.setAdapter(scheduleAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
