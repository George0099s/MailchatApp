package com.example.mailchat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mailchat.Functions;
import com.example.mailchat.R;

public class FailedConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_connection);


        findViewById(R.id.btnOk).setOnClickListener(v -> {
            if(Functions.internetIsConnected())
            {
                Intent intent = new Intent(FailedConnection.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Failed connection, try again",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

}
