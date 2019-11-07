package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SecurityCodeBusiness extends AppCompatActivity {
    EditText editTextCode;
    FirebaseAuth mAuth;
    private TextView sendAgain, phoneTV;

    private CountDownTimer countDownTimer;
    String phone, codeSent, firsName, lastName;
    private Boolean mTimerRunning;
    private long mTimeLeft = Constants.START_TIME;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code_business);

        mAuth = FirebaseAuth.getInstance();

        firsName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        phone = getIntent().getStringExtra("phoneNumber");
        codeSent = getIntent().getStringExtra("codeSent");
        startTimer();
        editTextCode = findViewById(R.id.codeBusinessTV);


        phoneTV = findViewById(R.id.phone_text_view);
        phoneTV.setText(phone);

        sendAgain = findViewById(R.id.send_again_business_btn);
        sendAgain.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v){
                                             if (mTimerRunning)
                                             {
                                                 Toast toast = Toast.makeText(SecurityCodeBusiness.this, "Please wait", Toast.LENGTH_SHORT);
                                                 toast.setGravity(Gravity.CENTER, 0, 0);
                                             } else {

                                                 verify(phone);
                                                 Toast toast = Toast.makeText(SecurityCodeBusiness.this, "Verification Code has sent", Toast.LENGTH_SHORT);
                                                 toast.setGravity(Gravity.CENTER, 0, 0);
                                                 resetTimer();
                                                 mTimerRunning = false;
                                                 startTimer();

                                             }

                                         }
                                     }
        );


        findViewById(R.id.btnVerifyCodeBusiness).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               verifySignInCode();
            }
        });
    }
    //send again
    public void verify(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void startTimer()  {
        countDownTimer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeft = millisUntilFinished;
                updateCountOnText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                sendAgain.setText("sendAgain");

            }
        }.start();
        mTimerRunning = true;

    }


    private void resetTimer()  {
        mTimeLeft = Constants.START_TIME;
        updateCountOnText();
    }
    private void updateCountOnText(){
        int minutes = (int) (mTimeLeft/1000) / 60;
        int sec = (int) (mTimeLeft/1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, sec);

        sendAgain.setText(timeLeftFormatted);
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d("ABCD","Verification completed" + phoneAuthCredential.toString());
            Toast.makeText(getApplicationContext(), "Verification completed",
                    Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("ABCD","Verification failed"+e.getMessage());
            Toast.makeText(getApplicationContext(), "Too many tries, try later",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d("ABCD","Code sent "+s);
            super.onCodeSent(s, forceResendingToken);
        }

    };
    private void verifySignInCode() {

        String code = editTextCode.getText().toString();
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            Log.d("BBB", credential.toString());
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            Log.d("BBB", "verifySignInCode: " + e.getMessage());
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            String phoneNum = user.getPhoneNumber();
                            String uid = user.getUid();


                            HashMap<Object, String> userInfo = new HashMap<>();

                            userInfo.put("phone number", phoneNum);
                            userInfo.put("uid", uid);
                            userInfo.put("first name", firsName); // передается из signUpbusiness puExtra
                            userInfo.put("last name", lastName);

//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//                            DatabaseReference reference = database.getReference("Users");
//
//                            reference.child(uid).setValue(userInfo);
//
//                            reference.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    // This method is called once with the initial value and again
//                                    // whenever data at this location is updated.
//                                    String value = dataSnapshot.getValue(String.class);
//                                    Log.d("database", "Value is: " + value);
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError error) {
//                                    // Failed to read value
//                                    Log.w("database", "Failed to read value.", error.toException());
//                                }
//                            });


                            Toast.makeText(getApplicationContext(), "number" + user.getPhoneNumber(),
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(SecurityCodeBusiness.this, AddNewCompany.class));

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "Incorrect verification code",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

}
