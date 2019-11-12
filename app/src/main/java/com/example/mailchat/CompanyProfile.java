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
import android.widget.TextView;

public class CompanyProfile extends AppCompatActivity {
    EditText webSite, cpNumber,address, workhours, aboutCompany;
    String web, companyNumber, companyAddress, companyWorkHours, about;
    FirebaseAuth mAuth;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);


        webSite = findViewById(R.id.webSite);
        cpNumber = findViewById(R.id.companyPhoneNumber);
        address = findViewById(R.id.address);
        workhours = findViewById(R.id.workHours);
        aboutCompany = findViewById(R.id.aboutCompany);
        skip = findViewById(R.id.skip);



        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.GoToAddUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
//                Intent intent = new Intent(CompanyProfile.this, AddUser.class);
//                startActivity(intent);
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
        if (web != null && companyNumber != null && companyAddress != null && companyWorkHours != null && about !=null) {
            if (web.isEmpty()) {
                webSite.setError("fill the field");
                webSite.requestFocus();
            }
            if (companyNumber.isEmpty()) {
                cpNumber.setError("fill the field");
                cpNumber.requestFocus();
            }
            if (companyAddress.isEmpty()) {
                address.setError("fill the field");
                address.requestFocus();
            }
            if (companyWorkHours.isEmpty()) {
                workhours.setError("fill the field");
                workhours.requestFocus();
            }
            if (about.isEmpty()) {
                aboutCompany.setError("fill the field");
                aboutCompany.requestFocus();
            }


            DatabaseReference reference = database.getReference("Business users/" + uid + "/Company");


            Users.businessCompanyInfo.put("web site", web);
            Users.businessCompanyInfo.put("company number", companyNumber);
            Users.businessCompanyInfo.put("company address", companyAddress);
            Users.businessCompanyInfo.put("work hours", companyWorkHours);
            Users.businessCompanyInfo.put("about company", about);

            reference.child(Users.businessCompanyInfo.get("Company name")).setValue(Users.businessCompanyInfo);
            }
        }
}
