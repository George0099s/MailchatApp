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

public class AddRepresentativeUser extends AppCompatActivity {

    EditText firstName, lastName, mailchatID, phoneNumber;
    String namefirst, nameLast, mailchat, number;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_representative_user);

        mAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        mailchatID = findViewById(R.id.maichatID);
        phoneNumber = findViewById(R.id.phoneNumber);



        findViewById(R.id.addRepresentative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }






    public void addData(){


        namefirst = firstName.getText().toString();
        nameLast= lastName.getText().toString();
        mailchat = mailchatID.getText().toString();
        number= phoneNumber.getText().toString();


        FirebaseUser user = mAuth.getCurrentUser();


        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();



    }
}
