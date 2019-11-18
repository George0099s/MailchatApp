package com.example.mailchat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
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




        logED = findViewById(R.id.loginED);
        log = findViewById(R.id.logINBTN);
        log.getBackground().setAlpha(128);

        findViewById(R.id.signUPBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(start.this, CompanyProfile.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logINBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login = logED.getText().toString();

                if (login.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Fill your login",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(start.this, LogIn.class);
                    startActivity(intent);
                }
            }
        });




        logED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

               log.getBackground().setAlpha(255);
               log.setEnabled(true);

            }
        });

    }



    }

