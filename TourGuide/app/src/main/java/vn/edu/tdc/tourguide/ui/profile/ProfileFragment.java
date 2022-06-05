package vn.edu.tdc.tourguide.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.SideMenuActivity;
import vn.edu.tdc.tourguide.databinding.ProfileScreenBinding;


public class ProfileFragment extends Fragment {
    private ProfileScreenBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileScreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ImageView avartaImage;
        TextView txtName = root.findViewById(R.id.txtNameProfile);
        TextView txtEmail = root.findViewById(R.id.txtEmailProfile);



        txtEmail.setText(SideMenuActivity.user.getEmail());
        txtName.setText(SideMenuActivity.user.getNameOfUser());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}
