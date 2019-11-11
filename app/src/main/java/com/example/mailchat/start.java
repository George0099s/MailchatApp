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
import android.widget.ImageButton;
import android.widget.Toast;

public class start extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViewById(R.id.signUPBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(start.this, RegistrationActivity.class);
//                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            }
        });




    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.signUPBTN:
                Intent intent = new Intent(this, RegistrationActivity.class);
//                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.logINBTN:
                intent = new Intent(this, LogIn.class);
                startActivity(intent);
                break;
        }
        }

    }

