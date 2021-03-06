package vn.edu.tdc.tourguide;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.edu.tdc.tourguide.models.City;
import vn.edu.tdc.tourguide.models.Destination;
import vn.edu.tdc.tourguide.models.User;
import vn.edu.tdc.tourguide.ui.home.HomeFragment;
import vn.edu.tdc.tourguide.ui.profile.ProfileFragment;
import vn.edu.tdc.tourguide.ui.schedule.ScheduleFragment;

public class SideMenuActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    public static boolean checkLogin = true;
    private NavigationView nvDrawer;
    private boolean checkFragment = true;
    private final Fragment fragment = null;
    Class fragmentClass = null;
    public static TextView txtName, txtEmail;
    public static ImageView imgAvatar;
    public static User user;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    private boolean isPermission = false;
    private int REQ_CODE = 123;
    private TextView txtMyLocation;
    public static View headerLayout;
    private SearchView searchView;
    private boolean checkSearch = true;
    public static boolean checkHome = false;
    public static boolean checkSchedule = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_menu_layout);

        City.list = new ArrayList<>();
        Destination.list = new ArrayList<>();
        City.getCities();
        Destination.getDestination();

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        // ...From section above...
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Inflate the header view at runtime
        headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header_side_menu);
        // We can now look up items within the header if needed
        txtEmail = headerLayout.findViewById(R.id.txtEmail);
        txtName = headerLayout.findViewById(R.id.txtName);
        imgAvatar = headerLayout.findViewById(R.id.imageView);

        //My location processing
        txtMyLocation = headerLayout.findViewById(R.id.txt_my_location);

        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)||!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            //Yeu cau cap quyen
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_CODE);
            if (isPermission == false) {
                txtMyLocation.setText("Vietnam");
            }
        } else {
            performAction();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (checkSearch){
            String TAG = "TAG";
            Log.d(TAG, "onCreateOptionsMenu: " + menu.toString());
            getMenuInflater().inflate(R.menu.side_menu, menu);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    HomeFragment.homeAdapter.getFilter().filter(query);
                    Log.d(TAG, "onQueryTextSubmit: " + query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    HomeFragment.homeAdapter.getFilter().filter(newText);
                    Log.d(TAG, "onQueryTextChange: " + newText);
                    return false;
                }
            });
            return true;
        }
        return false;
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    selectDrawerItem(menuItem);
                    return true;
                }
            }
        );
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        String TAG = "TAG";
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                checkHome = true;
                checkSearch = true;
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_profile:
                checkSearch = false;
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_schedule:
                checkSearch = false;
                fragmentClass = ScheduleFragment.class;
                break;
            default:
                checkSearch = false;
                checkFragment = true;
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
        }
        if (!checkFragment) {
            pressesFragment(fragmentClass, fragment, menuItem);
        }

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void pressesFragment(Class fragmentClass, Fragment fragment, MenuItem menuItem) {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_side_menu, fragment).commit();

        // Set action bar title
        setTitle(menuItem.getTitle());

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!checkLogin)
            return;
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String TAG = "TAG";
        if (checkSchedule) {
            MenuItem item = nvDrawer.getMenu().findItem(R.id.nav_schedule);
            pressesFragment(ScheduleFragment.class, fragment, item);
            checkSchedule = false;
            Log.d(TAG, "onStart: " + checkSchedule);
        } else if (checkFragment) {
            User.getUser();
            MenuItem item = nvDrawer.getMenu().findItem(R.id.nav_home);
            pressesFragment(HomeFragment.class, fragment, item);
            checkFragment = false;
            checkSearch = true;
            checkHome = true;
        }
        if (SignInActivity.edtEmail != null) {
            SignInActivity.edtEmail.setText("");
            SignInActivity.edtPassword.setText("");
        }
    }


    //Function to my location processing
    @SuppressLint("MissingPermission")
    private void performAction() {
        isPermission = true;
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Address address = getAddress(SideMenuActivity.this, location.getLatitude(), location.getLongitude());
                            if (address != null) {
                                Log.d("TAGADDRESS", "onSuccess: " + address.getAddressLine(0));
                                txtMyLocation.setText(address.getAddressLine(0));
                            }
                        }
                    }
                });
    }

    public boolean checkPermission(String permission) {
        int check = checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }

    private Address getAddress(Context context, double LATITUDE, double LONGITUDE) {
        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE) {
            if (permissions.length == grantResults.length) {
                for (int i = 0; i < permissions.length; ++i) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        String recommendation  = getResources().getString(R.string.recommendation);
                        Toast.makeText(SideMenuActivity.this, recommendation, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                String permission  = getResources().getString(R.string.permission);
                Toast.makeText(SideMenuActivity.this, permission, Toast.LENGTH_SHORT).show();
                performAction();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

