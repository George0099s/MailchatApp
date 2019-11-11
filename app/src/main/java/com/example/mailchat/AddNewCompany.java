package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddNewCompany extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText companyName;
    String cpName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_company);

        mAuth = FirebaseAuth.getInstance();

        companyName = findViewById(R.id.cpName);




        findViewById(R.id.goToChoooseCompanyID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            Intent intent = new Intent(AddNewCompany.this, CompanyProfile.class);
            startActivity(intent);
            }
        });
    }


    public void addData(){
        FirebaseUser user = mAuth.getCurrentUser();


        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        cpName = companyName.getText().toString();
        DatabaseReference reference = database.getReference("Business users");
        Users.businessUserInfo.put("Company name",cpName);
        reference.child(uid).setValue(Users.businessUserInfo);
    }
}
