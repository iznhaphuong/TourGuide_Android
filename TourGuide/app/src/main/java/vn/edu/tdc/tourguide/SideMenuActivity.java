package vn.edu.tdc.tourguide;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private Menu menu;
    private boolean checkFragment = true;
    private final Fragment fragment = null;
    Class fragmentClass = null;
    public static TextView txtName, txtEmail;
    public static ImageView imgAvatar;
    public static User user;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    public static boolean checkSearch = false;

    private boolean isPermission = false;
    private int REQ_CODE = 123;
    private TextView txtMyLocation;
    public static View headerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_menu_layout);
//        Destination.addDestination("-N3gxW_wr9pRqU1i763q", "Dinh Thống Nhất", "Di tích lịch sử","dinhthongnhat.jpg", 10.7770779, 106.6866713, "135 Đ. Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Vietnam", "Dinh Độc Lập hay Hội trường Thống nhất là một công trình kiến trúc, tòa nhà ở Thành phố Hồ Chí Minh. Đây từng là nơi ở và làm việc của Tổng thống Việt Nam Cộng hòa. Hiện nay, dinh đã được Chính phủ Việt Nam xếp hạng là di tích quốc gia đặc biệt.", 5);
//        Destination.addDestination("-N3qhkJUxMm7HSnHF2d5 ", "Chợ nổi Cái Răng", "Chợ","chonoicairang.jpg", 10.0050363, 105.7459816, "46 Đường Hai Bà Trưng, Tân An, Ninh Kiều, Cần Thơ", "Chợ nổi Cái Răng là chợ nổi đầu mối chuyên mua bán rau củ ở trên sông Cửu Long và là điểm tham quan đặc sắc của quận Cái Răng, thành phố Cần Thơ.", 4);
//        Destination.addDestination("-N3qhkJUxMm7HSnHF2d5 ", "Khu phố cổ Hà Nội", "Điểm du lịch","phocohanoi.jpg", 21.034059, 105.8506368, "P. Hàng Ngang, Hàng Đào, Hoàn Kiếm, Hà Nội", "Khu phố cổ Hà Nội là tên gọi thông thường của một khu vực đô thị có từ lâu đời của Hà Nội nằm ở ngoài hoàng thành Thăng Long", 4);

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
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        menu = nvDrawer.getMenu();

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
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_profile:
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_schedule:
                fragmentClass = ScheduleFragment.class;
                break;
            default:
                checkFragment = true;
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, SignInActivity.class);
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
        if (checkFragment) {
            user = User.getUser();
            MenuItem item = menu.findItem(R.id.nav_home);
            pressesFragment(HomeFragment.class, fragment, item);
            HomeFragment.homeAdapter.notifyDataSetChanged();
            checkFragment = false;
            SideMenuActivity.checkSearch = true;

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

