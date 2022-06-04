package vn.edu.tdc.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.tdc.tourguide.models.User;

public class SignUpActivity extends AppCompatActivity {
    private String TAG = "ERROR";
    private FirebaseAuth mAuth;
    private EditText edtName, edtEmail, edtPassword, edtRe_Password;
    private TextView signInTV;
    private Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get data based on id
        signInTV = findViewById(R.id.to_sign_in);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRe_Password = findViewById(R.id.edtRe_Password);
        signUp = findViewById(R.id.btnSignUp);

        // Button Register user
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        // Button Go to Sign In Activity
        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTabSignIn();
            }
        });
    }

    public void goToTabSignIn(){
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    //Processing register
    public void registerUser() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String re_password = edtRe_Password.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError("Name is required!");
            edtName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email is required!");
            edtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email provide valid email! ");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Password is required!");
            edtPassword.requestFocus();
            return;
        }
        if (password.length() <= 6) {
            edtPassword.setError("Password must have at least 6 characters!");
            edtPassword.requestFocus();
            return;
        }

        if (password.equals(re_password) == false){
            edtRe_Password.setError("Password and re-enter password are different");
            edtRe_Password.setText("");
            edtRe_Password.requestFocus();
            return;
        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(name, email);
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignUpActivity.this, "User has been registed successfully!", Toast.LENGTH_LONG).show();
                                                    goToTabSignIn();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Failed to register! Try again! ", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Register failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}