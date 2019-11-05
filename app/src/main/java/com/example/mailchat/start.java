package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

        findViewById(R.id.signUPBTN).setOnClickListener(this);

        findViewById(R.id.logINBTN).setOnClickListener(this);



    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.signUPBTN:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;

            case R.id.logINBTN:
                intent = new Intent(this, LogIn.class);
                startActivity(intent);
                break;
        }
        }

    }

