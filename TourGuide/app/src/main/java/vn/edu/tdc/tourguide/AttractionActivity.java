package vn.edu.tdc.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.tourguide.adapter.AttractionAdapter;
import vn.edu.tdc.tourguide.adapter.HomeAdapter;
import vn.edu.tdc.tourguide.modle.Attraction;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;

public class AttractionActivity extends AppCompatActivity {
    private final String TAG = "TAG";
    private SearchView searchView;
    private AttractionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_layout);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String title = intent.getStringExtra(HomeFragment.EXTRA_TITLE);
        int city_id = intent.getIntExtra(HomeFragment.EXTRA_ID, 0);
        Log.d(TAG, "onCreate: " + city_id);
        // Capture the layout's TextView and set the string as its text
        setTitle(title);


        RecyclerView rcvAttraction = findViewById(R.id.rcv_attraction);
        List<Attraction> mAttractionList = new ArrayList<>();

        Attraction attraction1 = new Attraction(
                1,
                2,
                "Lang Bac va Quang truong Ba Dinh",
                5,
                0,
                "2 Hung Vuong, Dien Ban, Ba Dinh, Ha Noi");

        Attraction attraction2 = new Attraction(
                2,
                2,
                "Hoang Thanh Thang Long",
                3.5F,
                0,
                "19C Hoang Dieu, Dien Ban, Ba Dinh, Ha Noi");

        mAttractionList.add(attraction1);
        mAttractionList.add(attraction2);

        adapter = new AttractionAdapter(mAttractionList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAttraction.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AttractionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick: Chuyen qua dau gio - " + position);
            }
        });

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvAttraction.addItemDecoration(itemDecoration);

        rcvAttraction.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        String TAG = "TAG";
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }

        super.onBackPressed();
    }
}