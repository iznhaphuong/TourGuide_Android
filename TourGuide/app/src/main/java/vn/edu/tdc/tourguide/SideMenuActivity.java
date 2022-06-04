package vn.edu.tdc.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import vn.edu.tdc.tourguide.adapter.HomeAdapter;
import vn.edu.tdc.tourguide.databinding.SideMenuLayoutBinding;
import vn.edu.tdc.tourguide.models.City;
import vn.edu.tdc.tourguide.models.Destination;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;

public class SideMenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SideMenuLayoutBinding binding;
    public static boolean checkLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        City.list = new ArrayList<>();
        Destination.list = new ArrayList<>();
        City.getCities();
        Destination.getDestination();

        binding = SideMenuLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarSideMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_schedule)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_side_menu);

        for (int i = 0; i < 1; i++) {
            processNavController(this, navController, mAppBarConfiguration, navigationView);
        }

        String home = getResources().getString(R.string.menu_home);
        String profile = getResources().getString(R.string.menu_profile);
        String schedule = getResources().getString(R.string.menu_schedule);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (home == navDestination.getLabel()) {
                    processNavController(SideMenuActivity.this, navController, mAppBarConfiguration, navigationView);
                } else if (profile == navDestination.getLabel()) {
                    processNavController(SideMenuActivity.this, navController, mAppBarConfiguration, navigationView);
                } else if (schedule == navDestination.getLabel()) {
                    processNavController(SideMenuActivity.this, navController, mAppBarConfiguration, navigationView);
                } else {
                    Intent intent = new Intent(binding.getRoot().getContext(), SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }
        });

        HomeFragment.homeAdapter.notifyDataSetChanged();

    }

    private void processNavController(AppCompatActivity activity, NavController navController, AppBarConfiguration appBarConfiguration, NavigationView navigationView) {
        NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        String TAG = "TAG";
        Log.d(TAG, "onBackPressed: 3");
        if (!HomeFragment.searchView.isIconified()) {
            HomeFragment.searchView.setIconified(true);
            Log.d(TAG, "onBackPressed: 1");
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_side_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}