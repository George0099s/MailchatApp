    package com.example.mailchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

    public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText editTextPhone, editTextName, editTextLastName;
    TextView firstNameTV, lastNameTV, phoneNumberTV;
    private final String TAG = "Sign Up Private user";
    FirebaseAuth mAuth;
    String codeSent, t;
    CheckBox checkBox;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        mAuth = FirebaseAuth.getInstance();

        firstNameTV = findViewById(R.id.first_name_tv);
        lastNameTV = findViewById(R.id.last_name_tv);
        phoneNumberTV = findViewById(R.id.phone_number_tv);
        editTextLastName = findViewById(R.id.lastNameTV);
        editTextPhone = findViewById(R.id.phoneTV);
        editTextName = findViewById(R.id.firstNameTV);
        checkBox = findViewById(R.id.checkBoxTerms);

        btn = findViewById(R.id.btnGetCodePrivate);
        btn.getBackground().setAlpha(128);



        Spinner spinner = (Spinner) findViewById(R.id.spinner_nums2);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Functions.nums_arr);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        findViewById(R.id.btnGetCodePrivate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                sendVerificationCode();
            }

        });


        Functions.isChecked2(editTextName, btn, firstNameTV);
        Functions.isChecked2(editTextLastName, btn, lastNameTV);
        Functions.isChecked2(editTextPhone, btn, phoneNumberTV);

    }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            t  = adapterView.getItemAtPosition(i).toString();



        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

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
                    t + phone,        // Phone number to verify
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
