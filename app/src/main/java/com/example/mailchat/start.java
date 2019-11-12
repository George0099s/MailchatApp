package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class start extends AppCompatActivity{

    EditText logED;
    String login;
    Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



//        logED = findViewById(R.id.loginED);
//        login = logED.getText().toString();
//        log = findViewById(R.id.logINBTN);
//        Functions.isNotEmpty(login, log);

        findViewById(R.id.signUPBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(start.this, RegistrationActivity.class);
//                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logINBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logED = findViewById(R.id.loginED);
                login = logED.getText().toString();
                log = findViewById(R.id.logINBTN);

                Functions.isNotEmpty(login, log);

                Intent intent = new Intent(start.this, LogIn.class);

                startActivity(intent);
            }
        });




    }



    }

