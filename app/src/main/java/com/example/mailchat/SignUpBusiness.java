package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpBusiness extends AppCompatActivity {

    EditText firsName, lastName, phoneNumber;
    private String namefirst, nameLast, phone;
    CheckBox checkBox;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_business);



         firsName = findViewById(R.id.firstNameTV);
         lastName = findViewById(R.id.lastNameTV);
         phoneNumber = findViewById(R.id.phoneTV);

         mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.btnVerifyCodeBusiness).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificateData();
            }
        });
    }

    public void verificateData(){
    namefirst = firsName.getText().toString();
    nameLast = lastName.getText().toString();
    phone = phoneNumber.getText().toString();

    if (namefirst.isEmpty() && nameLast.isEmpty() && phone.isEmpty() || phone.length() < 11) {
    firsName.setError("Incorrect first name");
    firsName.requestFocus();

    lastName.setError("Incorrect last name");
    lastName.requestFocus();

    phoneNumber.setError("Incorrect number");
    phoneNumber.requestFocus();
    }

    if (namefirst.isEmpty()) {
        firsName.setError("Incorrect first name");
        firsName.requestFocus();
    }
    if (nameLast.isEmpty()) {
        lastName.setError("Incorrect last name");
        lastName.requestFocus();
    }
    if (phone.isEmpty()) {
        phoneNumber.setError("Incorrect number");
        phoneNumber.requestFocus();
    }
//    if (namefirst !=null && nameLast !=null && phone != null) {
//        Intent intent = new Intent(SignUpBusiness.this, SecurityCodeBusiness.class);
//        intent.putExtra("first name", namefirst);
//        intent.putExtra("last name", nameLast);
//        intent.putExtra("phoneNumber", phone);
//        startActivity(intent);
//    }



    }

}
