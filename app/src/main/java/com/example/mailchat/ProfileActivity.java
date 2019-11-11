package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner monthSpinner, daySpinner, yearSpinner;
    Button male, female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        monthSpinner = findViewById(R.id.monthSspinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        daySpinner = findViewById(R.id.daySpinner);

        male = findViewById(R.id.btnMale);
        female = findViewById(R.id.btnFemale);

        findViewById(R.id.btnMale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.btnFemale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



       findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(ProfileActivity.this, AddPrivateUserPhoto.class);
//               intent.putExtra("");
               startActivity(intent);
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String t  = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), t, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
