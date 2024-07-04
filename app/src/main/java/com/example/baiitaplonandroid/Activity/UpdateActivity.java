package com.example.baiitaplonandroid.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baiitaplonandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateActivity extends BaseActivity {

    private TextView occupationTxtView, nameTxtView, workTxtView;
    private TextView emailTxtView, phoneTxtView, videoTxtView, facebookTxtView;
    private ImageView emailImageView, phoneImageView, videoImageView;
    private ImageView facebookImageView, backBtn;
    private CircleImageView userImageView;
    private DatabaseReference userRef;
    private String email;
    private static final String USERS = "users";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // Ánh xạ các phần tử giao diện
        occupationTxtView = findViewById(R.id.occupation_textview);
        nameTxtView = findViewById(R.id.name_textview);
        workTxtView = findViewById(R.id.workplace_textview);
        emailTxtView = findViewById(R.id.email_textview);
        phoneTxtView = findViewById(R.id.phone_textview);
        videoTxtView = findViewById(R.id.video_textview);
        facebookTxtView = findViewById(R.id.facebook_textview);
        userImageView = findViewById(R.id.user_imageview);
        emailImageView = findViewById(R.id.email_imageview);
        phoneImageView = findViewById(R.id.phone_imageview);
        videoImageView = findViewById(R.id.video_imageview);
        facebookImageView = findViewById(R.id.facebook_imageview);
        backBtn = findViewById(R.id.backBtn);

        // Thiết lập Firebase Database
        userRef = FirebaseDatabase.getInstance().getReference(USERS);

        // Đọc dữ liệu từ Firebase
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                    String userEmail = keyId.child("email").getValue(String.class);
                    if (userEmail != null && userEmail.equals(email)) {
                        String fname = keyId.child("fullName").getValue(String.class);
                        String profession = keyId.child("profession").getValue(String.class);
                        String workplace = keyId.child("workplace").getValue(String.class);
                        String phone = keyId.child("phone").getValue(String.class);
                        String facebook = keyId.child("facebook").getValue(String.class);

                        // Hiển thị thông tin lên giao diện
                        nameTxtView.setText(fname);
                        emailTxtView.setText(email);
                        occupationTxtView.setText(profession);
                        workTxtView.setText(workplace);
                        phoneTxtView.setText(phone);
                        videoTxtView.setText(phone); // Assuming videoTxtView displays phone
                        facebookTxtView.setText(facebook);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Không thể đọc giá trị
                Log.w("UpdateActivity", "Failed to read value.", error.toException());
            }
        });



        // Xử lý nút back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

    }

