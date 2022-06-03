package vn.edu.tdc.tourguide.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.AttractionActivity;
import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.adapter.HomeAdapter;
import vn.edu.tdc.tourguide.databinding.FragmentHomeBinding;
import vn.edu.tdc.tourguide.modle.Home;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_ID = "ID";
    public static HomeAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView rcvHome = binding.rcvHome;
        List<Home> myHomeList = new ArrayList<>();

        Home home1 = new Home(1, "Đà Lạt");
        Home home2 = new Home(2, "Ha Noi");

        myHomeList.add(home1);
        myHomeList.add(home2);

        String TAG = "TAG";
        homeAdapter = new HomeAdapter(myHomeList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        rcvHome.setLayoutManager(linearLayoutManager);

        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(root.getContext(), AttractionActivity.class);
                intent.putExtra(EXTRA_TITLE, homeAdapter.getmHomeList().get(position).getName());
                intent.putExtra(EXTRA_ID, homeAdapter.getmHomeList().get(position).getId());
                startActivity(intent);
            }
        });

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        rcvHome.addItemDecoration(itemDecoration);

        rcvHome.setAdapter(homeAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}