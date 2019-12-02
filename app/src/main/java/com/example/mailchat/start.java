package com.example.mailchat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
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




        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete() && task.isSuccessful()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Users"); // reference is 'chat' because we created the database at /chat
                }
            }
        });

        MyTask myTask = new MyTask();
        myTask.execute("gp");
//        sendData();

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


//    public void sendData(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        reference = database.getReference("Users"); // reference is 'chat' because we created the database at /chat
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                String value = dataSnapshot.getValue(String.class);
//                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                Log.d("123", "Value is: " + map);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }


    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            reference = database.getReference("Users"); // reference is 'chat' because we created the database at /chat
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                    Map<String, Object> info = (Map<String, Object>) dataSnapshot.getValue();
                    Map<String, Object> infouser = new HashMap<>();

                    for (int i = 0; i < info.size(); i++) {
                    }
                    String id = mAuth.getCurrentUser().getUid();

                    String s = "abc";


//                       String f = (String) info.get("");
                       Log.d(TAG, "onDataChange: " + info);
//                      Log.d("123", "Value is: " + map);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

  }

