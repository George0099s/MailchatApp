package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddNewCompany extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    Spinner spinnerCity, spinnerCategory;
    String city, category;
    FirebaseAuth mAuth;
    EditText companyName;
    String cpName;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_company);


        spinnerCategory = findViewById(R.id.spinnerCategoty);
        spinnerCity = findViewById(R.id.citySpinner);
        mAuth = FirebaseAuth.getInstance();

        companyName = findViewById(R.id.cpName);

        next = findViewById(R.id.goToChoooseCompanyID);
        next.getBackground().setAlpha(128);
        ArrayAdapter<CharSequence> adapterCity= ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerCity.setAdapter(adapterCity);
        spinnerCity.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(this);




        findViewById(R.id.goToChoooseCompanyID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addData();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String t  = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), t, Toast.LENGTH_LONG).show();

         city = spinnerCity.getItemAtPosition(spinnerCity.getSelectedItemPosition()).toString();
         category = spinnerCategory.getItemAtPosition(spinnerCategory.getSelectedItemPosition()).toString();
//        String m = monthSpinner.getItemAtPosition(monthSpinner.getSelectedItemPosition()).toString();
//        city = citySpinner.getItemAtPosition(citySpinner.getSelectedItemPosition()).toString();
//        date = d + "." + m + ". " + y;


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addData(){
        FirebaseUser user = mAuth.getCurrentUser();

        cpName = companyName.getText().toString();
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference reference = database.getReference("Business users/" + uid + "/Company");

        Users.businessCompanyInfo.put("City",city);
        Users.businessCompanyInfo.put("Category",category);
        Users.businessCompanyInfo.put("Company name",cpName);
        if (cpName.isEmpty())
        {
            companyName.setError("Fill the field");
            companyName.requestFocus();
            next.getBackground().setAlpha(128);
        } else {
            reference.child(cpName).setValue(Users.businessCompanyInfo);
            next.getBackground().setAlpha(255);
            Intent intent = new Intent(AddNewCompany.this, ChooseBusinessID.class);
            startActivity(intent);
        }
    }
}
