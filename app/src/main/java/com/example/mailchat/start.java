package com.example.mailchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbRequest;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.service.autofill.Dataset;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public class start extends AppCompatActivity{
    ImageView isChecked;
    EditText logED;
    String login, userID;
   private Button log;
   private Button signUpBtn;

    private final String TAG = getClass().toString();
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        logED = findViewById(R.id.loginED);
        log = findViewById(R.id.logINBTN);
        log.getBackground().setAlpha(128);
        signUpBtn = findViewById(R.id.signUPBTN);
        signUpBtn.setOnClickListener(this::goToRegistration);
        log.setOnClickListener(this::goToLogIn);
          Functions.isChecked(logED, log);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete() && task.isSuccessful()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Users"); // reference is 'chat' because we created the database at /chat
                }
            }
        });
        sendData();

    }
    public void sendData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users"); // reference is 'chat' because we created the database at /chat
        Log.d(TAG, "sendData: 123");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d("123", "Value is: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("123", "Failed to read value.", error.toException());
            }
        });
    }






    private void goToRegistration(View view) {
        Intent intent = new Intent(start.this, RegistrationActivity.class);
        startActivity(intent);
    }




    private void goToLogIn(View view) {
        login = logED.getText().toString();

        if (login.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Fill your login",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(start.this, LogIn.class);
            startActivity(intent);
        }
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

