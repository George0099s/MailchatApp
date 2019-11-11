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
import android.widget.EditText;

public class CompanyProfile extends AppCompatActivity {
    EditText webSite, cpNumber,address, workhours, aboutCompany;
    String web, companyNumber, companyAddress, companyWorkHours, about;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);


        webSite = findViewById(R.id.webSite);
        cpNumber = findViewById(R.id.companyPhoneNumber);
        address = findViewById(R.id.address);
        workhours = findViewById(R.id.workHours);
        aboutCompany = findViewById(R.id.aboutCompany);




        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.GoToAddUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                Intent intent = new Intent(CompanyProfile.this, AddUser.class);
                startActivity(intent);
            }
        });
    }


    public void addData(){
        FirebaseUser user = mAuth.getCurrentUser();


        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        web = webSite.getText().toString();
        companyNumber = cpNumber.getText().toString();
        companyAddress = address.getText().toString();
        companyWorkHours = workhours.getText().toString();
        about = aboutCompany.getText().toString();

        DatabaseReference reference = database.getReference("Business users");
        Users.businessUserInfo.put("web site",web);
        Users.businessUserInfo.put("company number",companyNumber);
        Users.businessUserInfo.put("company address",companyAddress);
        Users.businessUserInfo.put("work hours",companyWorkHours);
        Users.businessUserInfo.put("about company",about);

        reference.child(uid).setValue(Users.businessUserInfo);
    }
}
