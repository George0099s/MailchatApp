package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpBusiness extends AppCompatActivity {

    EditText firsName, lastName, phoneNumber;
    private String namefirst, nameLast, phone, codeSent;
    CheckBox checkBox;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_business);


        checkBox = findViewById(R.id.checkBoxTerms);
         firsName = findViewById(R.id.firstNameTV);
         lastName = findViewById(R.id.lastNameTV);
         phoneNumber = findViewById(R.id.phoneTV);

         mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.btn123).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });
    }

    public void sendVerificationCode(){
    namefirst = firsName.getText().toString();
    nameLast = lastName.getText().toString();
    phone = phoneNumber.getText().toString();




    if (namefirst.isEmpty() && nameLast.isEmpty() && phone.isEmpty() || phone.length() < 11) {
    firsName.setError("Incorrect first name");
    firsName.requestFocus();

    lastName.setError("Incorrect last name");
    lastName.requestFocus();

    phoneNumber.setError("Incorrect number");
    phoneNumber.requestFocus();
    }

    if (namefirst.isEmpty()) {
        firsName.setError("Incorrect first name");
        firsName.requestFocus();
    }
    if (nameLast.isEmpty()) {
        lastName.setError("Incorrect last name");
        lastName.requestFocus();
    }
    if (phone.isEmpty()) {
        phoneNumber.setError("Incorrect number");
        phoneNumber.requestFocus();
    }
    if(checkBox.isChecked())
    {

    } else {
        checkBox.setError("Confirm terms");
        checkBox.requestFocus();
    }

        if (namefirst !=null && nameLast !=null && phone != null && checkBox.isChecked()) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks


    }



    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d("ABCD","Verification completed"+phoneAuthCredential.toString());
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
            Log.d("ABCD","Code sent "+ s);
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            Intent intent = new Intent(SignUpBusiness.this, SecurityCodeBusiness.class);
            intent.putExtra("firstName", namefirst);
            intent.putExtra("lastName", nameLast);
            intent.putExtra("phoneNumber", phone);
            intent.putExtra("codeSent", codeSent);

            startActivity(intent);
        }

    };

}
