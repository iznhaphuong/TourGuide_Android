package vn.edu.tdc.tourguide.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.net.Uri;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;

import vn.edu.tdc.tourguide.R;
import vn.edu.tdc.tourguide.SideMenuActivity;
import vn.edu.tdc.tourguide.databinding.ProfileScreenBinding;
import vn.edu.tdc.tourguide.models.User;


public class ProfileFragment extends Fragment {
    private ProfileScreenBinding binding;
    private View rootView;
    private ActivityResultLauncher<String> mTakePhoto;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileScreenBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();

        binding.txtEmailProfile.setText(SideMenuActivity.user.getEmail());
        binding.txtNameProfile.setText(SideMenuActivity.user.getNameOfUser());
        Glide.with(rootView.getContext()).load(SideMenuActivity.user.getLogoPersional()).error(R.drawable.avarta2).into(binding.avartaImage);


        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imageUri = result;
                        binding.avartaImage.setImageURI(result);
                    }
                }
        );


        binding.avartaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTakePhoto.launch("image/*");
            }
        });

        binding.updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtEmailProfile.getText().toString().trim();
                String nameOfUser = binding.txtNameProfile.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(rootView.getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();

                } else if(!email.contains("@")) {
                    email.concat("@gmail.com");
                } else if (email.lastIndexOf(".") == -1) {
                    Toast.makeText(rootView.getContext(), "Please enter the correct email", Toast.LENGTH_SHORT).show();
                } else if (nameOfUser.isEmpty()) {
                    Toast.makeText(rootView.getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
                    User.updateUser(userCurrent.getUid(), email, imageUri.toString(), nameOfUser);
                    SideMenuActivity.user.setEmail(email);
                    SideMenuActivity.user.setNameOfUser(nameOfUser);
                    userCurrent.updateEmail(email);
                    Glide.with(SideMenuActivity.headerLayout).load(imageUri.toString()).error(R.drawable.avarta2).into(SideMenuActivity.imgAvatar);
                    Toast.makeText(rootView.getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

}
