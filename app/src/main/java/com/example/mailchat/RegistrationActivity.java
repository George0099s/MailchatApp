package com.example.mailchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        findViewById(R.id.back_btn).setOnClickListener(v -> onBackPressed());


        findViewById(R.id.private_userBTN).setOnClickListener(v -> {

            if(Functions.internetIsConnected())
            {
            initActivity();
            } else {
                Intent intent = new Intent(RegistrationActivity.this, FailedConnection.class);
                startActivity(intent);
            }

        });


        findViewById(R.id.BusinessBTN).setOnClickListener(v -> {
//                if(Functions.internetIsConnected())
//                {
                Intent intent = new Intent(RegistrationActivity.this, SignUpBusiness.class);
                startActivity(intent);
//                } else {
//                    Intent intent = new Intent(RegistrationActivity.this, FailedConnection.class);
//                    startActivity(intent);
//                }
        });
    }

    public void initActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
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
