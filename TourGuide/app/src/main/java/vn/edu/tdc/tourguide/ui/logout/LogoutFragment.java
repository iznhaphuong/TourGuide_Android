package vn.edu.tdc.tourguide.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


import vn.edu.tdc.tourguide.SideMenuActivity;
import vn.edu.tdc.tourguide.SignInActivity;
import vn.edu.tdc.tourguide.databinding.FragmentHomeBinding;
import vn.edu.tdc.tourguide.databinding.ProfileScreenBinding;

public class LogoutFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, true);
        View root = binding.getRoot();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(root.getContext(), SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

        return root;
    }
}
