package vn.edu.tdc.tourguide.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
import vn.edu.tdc.tourguide.models.City;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_ID = "ID";
    public static HomeAdapter homeAdapter;
    public static SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView rcvHome = binding.rcvHome;
        List<City> myHomeList = City.list;

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
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.side_menu, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        String TAG = "TAG";
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                HomeFragment.homeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                HomeFragment.homeAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}