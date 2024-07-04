package com.example.baiitaplonandroid.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.baiitaplonandroid.Domain.User;
import com.example.baiitaplonandroid.R;
import com.example.baiitaplonandroid.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends BaseActivity {
    private TextInputLayout nameEditText, professionEditText, workEditText, passwordEditText;
    private TextInputLayout phoneEditText, emailEditText, facebookEditText, twitterEditText;
    private CircleImageView picImageView;
    private ImageView logoutBtn;
    private CheckBox maleCheckBox, femaleCheckBox;
    private Button registerButton;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    private String TAG = "ProfileActivity";
    private String fname, email, profession, phone, workplace, facebook, twitter;
    private String password;
    private User user;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        logoutBtn= findViewById(R.id.logoutBtn);
        nameEditText = findViewById(R.id.fullname_edittext);
        professionEditText = findViewById(R.id.profession_edittext);
        workEditText = findViewById(R.id.workplace_edittext);
        phoneEditText = findViewById(R.id.phone_edittext);
        passwordEditText = findViewById(R.id.enterpass_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        facebookEditText = findViewById(R.id.facebook_edittext);
        twitterEditText = findViewById(R.id.twitter_edittext);
        picImageView = findViewById(R.id.pic_imageview);
        maleCheckBox = findViewById(R.id.male_checkbox);
        femaleCheckBox = findViewById(R.id.female_checkbox);
        registerButton = findViewById(R.id.register_button);

        mDatabase = FirebaseDatabase.getInstance().getReference(USERS);
        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data into firebase database
                if (emailEditText.getEditText().getText().toString() != null && passwordEditText.getEditText().getText().toString() != null) {
                    fname = nameEditText.getEditText().getText().toString();
                    email = emailEditText.getEditText().getText().toString();
                    phone = phoneEditText.getEditText().getText().toString();
                    profession = professionEditText.getEditText().getText().toString();
                    workplace = workEditText.getEditText().getText().toString();
                    password = passwordEditText.getEditText().getText().toString();
                    facebook = facebookEditText.getEditText().getText().toString();
                    twitter = twitterEditText.getEditText().getText().toString();
                    user = new User(fname, email, profession, workplace, phone, facebook, twitter);
                    registerUser();
                }
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,IntroActivity.class);
                startActivity(intent);
            }
        });
    }

    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            String keyid = mDatabase.push().getKey();

            // Tạo một đối tượng User với thông tin người dùng
            User user = new User(fname, email, profession, workplace, phone, facebook, twitter);

            // Thêm thông tin người dùng vào cơ sở dữ liệu
            mDatabase.child(keyid).setValue(user);

            // Tạo Intent và truyền dữ liệu cần thiết (ví dụ: email)
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            intent.putExtra("email", currentUser.getEmail());
            startActivity(intent);
        }


    }


}