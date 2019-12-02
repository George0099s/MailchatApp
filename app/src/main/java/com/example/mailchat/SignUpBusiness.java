package com.example.mailchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

public class SignUpBusiness extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner numsSpinner;
    EditText firsName, lastName, phoneNumber;
    TextView firstNameTV, lastNameTV, phoneNumberTV;
    private String namefirst, nameLast, phone, codeSent;
    CheckBox checkBox;
    private FirebaseAuth mAuth;
    Button login;
    String t;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_business);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        String[] nums_arr = {"+7", "+1", "+1", "+4"};

         numsSpinner = findViewById(R.id.spinner_nums);
         login = findViewById(R.id.btnOk);
         checkBox = findViewById(R.id.checkBoxTerms);
         firsName = findViewById(R.id.firstNameTV);
         lastName = findViewById(R.id.lastNameTV);
         phoneNumber = findViewById(R.id.phoneTV);
         firstNameTV = findViewById(R.id.first_name_tv);
         lastNameTV = findViewById(R.id.last_name_tv);
         phoneNumberTV = findViewById(R.id.phone_number_tv);

        Button btn = findViewById(R.id.btnOk);
        btn.getBackground().setAlpha(128);
        btn.setEnabled(false);
         mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.logIn).setOnClickListener(view -> startActivity(new Intent(SignUpBusiness.this, LogIn.class)));
        findViewById(R.id.btnOk).setOnClickListener(view -> sendVerificationCode());

        Functions.isChecked2(firsName,login,firstNameTV);
        Functions.isChecked2(lastName, login, lastNameTV);
        Functions.isChecked2(phoneNumber, login, phoneNumberTV);

        spinner = findViewById(R.id.spinner_nums);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nums_arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         t  = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void sendVerificationCode(){
    namefirst = firsName.getText().toString();
    nameLast = lastName.getText().toString();
    phone = t + phoneNumber.getText().toString();



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
            Log.d("!23", "onVerificationFailed: " + phone);
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
