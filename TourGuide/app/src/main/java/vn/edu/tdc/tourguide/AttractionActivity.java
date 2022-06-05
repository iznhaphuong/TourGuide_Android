package vn.edu.tdc.tourguide;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.edu.tdc.tourguide.adapter.AttractionAdapter;
import vn.edu.tdc.tourguide.models.Destination;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;

public class AttractionActivity extends AppCompatActivity {
    private final String TAG = "TAG";
    private SearchView searchView;
    public static AttractionAdapter adapter;
    private List<Destination> destinations = new ArrayList<>();
    public static String EXTRA_DESTINATION = "EXTRA_DESTINATION";
    public static String EXTRA_TITLE = "EXTRA_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_layout);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String title = intent.getStringExtra(HomeFragment.EXTRA_TITLE);
        String city_id = intent.getStringExtra(HomeFragment.EXTRA_ID);

        if (title != null) {
            setTitle(title);
        } else {
            setTitle(DetailScreenActivity.title);
        }

        RecyclerView rcvAttraction = findViewById(R.id.rcv_attraction);
        List<Destination> mAttractionListOld = Destination.list;

        for (Destination destination : mAttractionListOld) {
            if (Objects.equals(destination.getCity_id(), city_id)) {
                destinations.add(destination);
            }
        }

        adapter = new AttractionAdapter(destinations);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAttraction.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AttractionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(AttractionActivity.this, DetailScreenActivity.class);
                intent.putExtra(EXTRA_DESTINATION, destinations.get(position).getId());
                intent.putExtra(EXTRA_TITLE, title);
                startActivity(intent);
//                mActivityResultLauncher.launch(intent);
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