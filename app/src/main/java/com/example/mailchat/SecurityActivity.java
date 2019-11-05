package com.example.mailchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SecurityActivity extends AppCompatActivity {
    EditText editTextCode;
    FirebaseAuth mAuth;
    private TextView sendAgain;

    private CountDownTimer countDownTimer;
    String phone;
    private Boolean mTimerRunning;
    private long mTimeLeft = Constants.START_TIME;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        phone = getIntent().getStringExtra("phone");
        startTimer();
       sendAgain = findViewById(R.id.send_again_btn);
        sendAgain.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v){
                                          if (mTimerRunning)
                                          {
                                              Toast toast = Toast.makeText(SecurityActivity.this, "Please wait", Toast.LENGTH_SHORT);
                                              toast.setGravity(Gravity.CENTER, 0, 0);
                                          } else {

                                              verify(phone);
                                              Toast toast = Toast.makeText(SecurityActivity.this, "Verification Code has sent", Toast.LENGTH_SHORT);
                                              toast.setGravity(Gravity.CENTER, 0, 0);
                                              resetTimer();
                                              mTimerRunning = false;
                                              startTimer();

                                          }

                                      }
                                     }
        );


        editTextCode = findViewById(R.id.codeTV);
        mAuth = FirebaseAuth.getInstance();
        TextView phonetv = findViewById(R.id.phone_text_view);
        phonetv.setText(phone);


        findViewById(R.id.btnVerifyCode).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void verify(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
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
            Toast.makeText(getApplicationContext(), "Incorrect verification code",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d("ABCD","Code sent "+s);
            super.onCodeSent(s, forceResendingToken);
        }

    };
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

    private void verifySignInCode() {

        String code = editTextCode.getText().toString();
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(MainActivity.codeSent, code);
//            Log.d("ABCD", credential.toString());
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
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

                            Toast.makeText(getApplicationContext(), "number" + user.getPhoneNumber(),
                                    Toast.LENGTH_SHORT).show();



                            startActivity(new Intent(SecurityActivity.this, ProfileActivity.class));

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "Incorrect verification code",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }





}


