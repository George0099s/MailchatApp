package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class AddUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        findViewById(R.id.goToUserAdd2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(AddUser.this, AddUser2.class);
                startActivity(intent);
            }

        });
    }

}
