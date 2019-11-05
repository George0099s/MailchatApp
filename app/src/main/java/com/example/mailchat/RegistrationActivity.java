package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();

            }

        });


        findViewById(R.id.private_userBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(Functions.internetIsConnected())
                {
                initActivity();
                } else {
                    Intent intent = new Intent(RegistrationActivity.this, FailedConnection.class);
                    startActivity(intent);
                }

            }

        });


        findViewById(R.id.BusinessBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(Functions.internetIsConnected())
                {
                    Intent intent = new Intent(RegistrationActivity.this, SignUpBusiness.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(RegistrationActivity.this, FailedConnection.class);
                    startActivity(intent);
                }

            }

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


}
