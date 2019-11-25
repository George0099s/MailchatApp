package com.example.mailchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class start extends AppCompatActivity{
    ImageView isChecked;
    EditText logED;
    String login;
    Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        logED = findViewById(R.id.loginED);



        log = findViewById(R.id.logINBTN);
        log.getBackground().setAlpha(128);


        findViewById(R.id.signUPBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(start.this, RegistrationActivity.class);
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




        Functions.isChecked(logED, log);

    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }

        return super.dispatchTouchEvent(event);
    }



    }

