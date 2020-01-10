package com.example.mailchat.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mailchat.Models.User;
import com.example.mailchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProfileActivity";
    Spinner monthSpinner, daySpinner, yearSpinner, citySpinner;
    Button next, male, female;
    String date, city, gender;
    Boolean s = false;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        user = getIntent().getParcelableExtra("user");
        monthSpinner = findViewById(R.id.monthSspinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        citySpinner = findViewById(R.id.spinnerCity);
        male = findViewById(R.id.btnMale);
        female = findViewById(R.id.btnFemale);
        next = findViewById(R.id.btnNext);

        if(s){next.getBackground().setAlpha(255);}

        findViewById(R.id.btnMale).setOnClickListener(view -> {
            male.setBackgroundResource(R.drawable.okbtn);
            male.setTextColor(Color.parseColor("#ffffff"));

            female.setTextColor(Color.parseColor("#2592FB"));
            female.setBackgroundResource(R.drawable.rect_okbtn_small);
            gender = "male";
            s = true;
            user.setGender(gender);
        });
        findViewById(R.id.btnFemale).setOnClickListener(view -> {
            female.setBackgroundResource(R.drawable.okbtn);
            female.setTextColor(Color.parseColor("#ffffff"));
            male.setTextColor(Color.parseColor("#2592FB"));
            male.setBackgroundResource(R.drawable.rect_okbtn_small);
            s = true;
            gender = "female";
            user.setGender(gender);
        });

       findViewById(R.id.btnNext).setOnClickListener(v -> {
           if (s) {
               next.getBackground().setAlpha(255);

               addData();

           }
           if (s == false)
           {
               Toast.makeText(getApplicationContext(), "Choose gender", Toast.LENGTH_SHORT).show();
               next.getBackground().setAlpha(128);
           }
       });

        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayList<String> days = new ArrayList<>();

        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }


        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        yearSpinner.setAdapter(adapterYear);
        yearSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapterDay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        daySpinner.setAdapter(adapterDay);
        daySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        monthSpinner.setAdapter(adapterMonth);
        monthSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(adapterCity);
        citySpinner.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String t  = adapterView.getItemAtPosition(position).toString();
        String y = yearSpinner.getItemAtPosition(yearSpinner.getSelectedItemPosition()).toString();
        String d = daySpinner.getItemAtPosition(daySpinner.getSelectedItemPosition()).toString();
        String m = monthSpinner.getItemAtPosition(monthSpinner.getSelectedItemPosition()).toString();
        city = citySpinner.getItemAtPosition(citySpinner.getSelectedItemPosition()).toString();
        date = d + "." + m + ". " + y;
        user.setCity(city);
        Log.d(TAG, "onItemSelected: city" + user.getCity());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void addData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = mAuth.getCurrentUser();
        String uid = fUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        user.setDateOfBirth(date);
        user.setCity(city);
        reference.child(uid).setValue(user);
        Intent intent1 = new Intent(ProfileActivity.this, AddPrivateUserPhoto.class);
        intent1.putExtra("user", user);
        startActivity(intent1);
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
