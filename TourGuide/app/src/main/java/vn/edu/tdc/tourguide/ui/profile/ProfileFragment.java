package vn.edu.tdc.tourguide.ui.profile;

import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("users");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileScreenBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();
        String TAG = "TAG";

        binding.txtEmailProfile.setText(SideMenuActivity.user.getEmail());
        binding.txtNameProfile.setText(SideMenuActivity.user.getNameOfUser());
        Glide.with(rootView.getContext()).load(SideMenuActivity.user.getLogoPersional()).error(R.drawable.avarta2).into(binding.avartaImage);

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imageUri = result;
                        Glide.with(rootView.getContext()).load(result.toString()).error(R.drawable.avarta2).into(binding.avartaImage);
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
                String nameOfUser = binding.txtNameProfile.getText().toString().trim();
                if (nameOfUser.isEmpty()) {
                    Toast.makeText(rootView.getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    SideMenuActivity.user.setNameOfUser(nameOfUser);
                    if (imageUri != null) {
                        uploadToFirebase(imageUri, nameOfUser);
                    }
                }
            }
        });
        Log.d(TAG, "onCreateView: 4414");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private String getFileExtension(Uri mUri, String nameOfUser) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void uploadToFirebase(Uri uri, String nameOfUser) {
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri, nameOfUser));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
                        User.updateUser(userCurrent.getUid(), SideMenuActivity.user.getEmail(), uri.toString(), nameOfUser);
                        Toast.makeText(rootView.getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //FireStorage
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(rootView.getContext(), "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
