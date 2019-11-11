package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.HashMap;

public class AddNewCompany extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_company);
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        String phoneNum = user.getPhoneNumber();
//        String uid = user.getUid();
//
//
//        HashMap<Object, String> userInfo = new HashMap<>();
//
//        userInfo.put("phone number", phoneNum);
//        userInfo.put("uid", uid);
//        userInfo.put("first name", "asd"); // передается из signUpbusiness puExtra
//        userInfo.put("last name", "123");
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        DatabaseReference myRef = database.getReference("Users");
//
////        myRef.setValue("Hello, World!");
        findViewById(R.id.goToChoooseCompanyID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(AddNewCompany.this, CompanyProfile.class);
            startActivity(intent);
            }
        });
    }

}
