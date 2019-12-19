package com.example.mailchat.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mailchat.Functions;
import com.example.mailchat.InboxActivity;
import com.example.mailchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class start extends AppCompatActivity{
    ImageView isChecked;
    EditText logED;
    String login, userID;
    private Button log;
    private Button signUpBtn;
    ArrayList<String> mailchatIdList = new ArrayList<>();
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
//        Functions.isChecked(logED, log);


        Functions.isCheckedLogin(logED, log);
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(task -> {
            if (task.isComplete() && task.isSuccessful()){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                reference = database.getReference("Users");
            }
        });
    }

   private void goToRegistration(View view) {
       Intent intent = new Intent(start.this, RegistrationActivity.class);
        startActivity(intent);
    }

   private void goToLogIn(View view) {

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            reference = database.getReference("Users"); // reference is 'chat' because we created the database at /chat
            reference.addValueEventListener(new ValueEventListener() {
                String userMailchatId;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        userMailchatId = (String) messageSnapshot.child("maichatID").getValue();
                        mailchatIdList.add(userMailchatId);
                }
                    for (int i = 0; i < mailchatIdList.size(); i++) {
                        if (logED.getText().toString().equals(mailchatIdList.get(i))){
                            Intent intent = new Intent(start.this, LogIn.class);
                            startActivity(intent);
                            Log.d(TAG, "onDataChange: yes");
                        }
                        if (logED.getText().toString() != mailchatIdList.get(i)){
                           Toast.makeText(getApplicationContext(),"Incorrect user name", Toast.LENGTH_SHORT);
                            Log.d(TAG, "onDataChange: no");

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
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

