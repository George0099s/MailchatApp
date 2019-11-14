    package com.example.mailchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

    public class MainActivity extends AppCompatActivity {

    private EditText editTextPhone, editTextName, editTextLastName;
    private final String TAG = "Sign Up Private user";
    FirebaseAuth mAuth;
    String codeSent;
    CheckBox checkBox;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();


        editTextLastName = findViewById(R.id.lastNameTV);
        editTextPhone = findViewById(R.id.phoneTV);
        editTextName = findViewById(R.id.firstNameTV);
        checkBox = findViewById(R.id.checkBoxTerms);

        btn = findViewById(R.id.btnGetCodePrivate);
        btn.getBackground().setAlpha(128);


        findViewById(R.id.btnGetCodePrivate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                sendVerificationCode();
            }

        });


        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btn.getBackground().setAlpha(255);
            }
        });


    }



        String phone;
        String lastName;
        String name;
        private void sendVerificationCode(){

        phone = editTextPhone.getText().toString();
        name = editTextName.getText().toString();
        lastName = editTextLastName.getText().toString();

        if(phone.isEmpty() && name.isEmpty() )
        {
            editTextName.setError("Введите имя");
            editTextName.requestFocus();
            editTextPhone.setError("введите значение");
            editTextPhone.requestFocus();
            checkBox.setError("Подтвердите соглашение");
            checkBox.requestFocus();
            return;
        }
        if (checkBox == null)
        {
            checkBox.setError("Подтвердите соглашение");
            checkBox.requestFocus();
        }
        if (phone.isEmpty())
        {
            editTextName.setError("Введите имя");
            editTextName.requestFocus();
        }

        if (phone.length() < 11 )

        {
            editTextPhone.setError("введите правильно номер");
            editTextPhone.requestFocus();
            return;
        }
        if(name.isEmpty())
        {
            editTextName.setError("Введите имя");
            editTextName.requestFocus();
        }

        if (name != null && phone !=null && checkBox.isChecked() ) {
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
                Intent intent = new Intent(MainActivity.this, SecurityActivity.class);
                intent.putExtra("phone", phone);
                 intent.putExtra("firstName",name );
                intent.putExtra("lastName",lastName );
                intent.putExtra("codeSent", codeSent);
                startActivity(intent);
            }

        };

//        private void verifySignInCode()
//        {
//
//            String code = editTextCode.getText().toString();
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
//            Log.d("ABCD",credential.toString());
//            signInWithPhoneAuthCredential(credential);
//        }
//        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//            mAuth.signInWithCredential(credential)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(getApplicationContext(), "log success",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                if (task.getException() instanceof  FirebaseAuthInvalidCredentialsException)
//                                {
//                                    Toast.makeText(getApplicationContext(), "Incorrect verification code",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        }
//                    });
//        }

    }
