package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class AddUser extends AppCompatActivity {
TextView userName;

FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        userName = findViewById(R.id.firstLastNameUser);
        userName.setText(name);
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
