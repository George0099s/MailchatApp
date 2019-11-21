package com.example.mailchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class LogIn extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText editTextPhone;
    String codeSent;
    String phone;
    TextView weWill;
    TextView validNum;
    private final String TAG = "Login Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mAuth = FirebaseAuth.getInstance();

        editTextPhone = findViewById(R.id.lastNameTV);
         validNum = findViewById(R.id.validNumTV);
        validNum.setVisibility(View.INVISIBLE);
         weWill = findViewById(R.id.weWillSendTV);
        Button btn =  findViewById(R.id.btnlogIn);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        findViewById(R.id.btnlogIn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              sendVerificationCode();
            }
        });
        Functions.isChecked(editTextPhone, btn);


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
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    private void sendVerificationCode(){
        phone = editTextPhone.getText().toString();

        if (phone.isEmpty() || phone.length() < 11)
        {
            editTextPhone.setError("Incorrect phone number");
            editTextPhone.requestFocus();
            weWill.setVisibility(View.INVISIBLE);
            validNum.setVisibility(View.VISIBLE);
        }

        if (phone !=null ) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks

            TextView s = findViewById(R.id.phone_text_view);
//            Button login = findViewById(R.id.btnlogIn);
//
//            login.setTextAppearance(this, R.style.btnLogOn);
//            login.setBackground(#48C8DB);


//            Intent intent = new Intent(this, SecurityActivity.class);
//            intent.putExtra("phone",phone);
//            startActivity(intent);


        }

    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG,"Verification completed"+phoneAuthCredential.toString());
            Toast.makeText(getApplicationContext(), "Verification completed",
                    Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d(TAG,"Verification failed"+e.getMessage());
            Toast.makeText(getApplicationContext(), "Incorrect verification code",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(TAG,"Code sent "+s);
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            Intent intent = new Intent(LogIn.this, SecurityActivity.class);
            intent.putExtra("phone",phone);
            startActivity(intent);
        }

    };






}
