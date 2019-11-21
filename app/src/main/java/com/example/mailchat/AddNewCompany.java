package com.example.mailchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddNewCompany extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    Spinner spinnerCity, spinnerCategory;
    String city, category;
    FirebaseAuth mAuth;
    EditText companyName, link;
    String cpName;
    TextView  linkss, cpNameTV;
    Button next;
    ListView list;
    ImageView add;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    int i = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_company);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        tv1 = findViewById(R.id.tv1);
//        tv2 = findViewById(R.id.tv2);
//        tv3 = findViewById(R.id.tv3);
        add = findViewById(R.id.addLink);

        linkss = findViewById(R.id.textView38);
        spinnerCategory = findViewById(R.id.spinnerCategoty);
        spinnerCity = findViewById(R.id.citySpinner);
        mAuth = FirebaseAuth.getInstance();
        list =  findViewById(R.id.list);
        companyName = findViewById(R.id.cpName);
        link = findViewById(R.id.editText7);
        cpNameTV = findViewById(R.id.company_name_tv);


        next = findViewById(R.id.goToChoooseCompanyID);
        next.getBackground().setAlpha(128);

        arrayList = new ArrayList<String>(3);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);



        ArrayAdapter<CharSequence> adapterCity= ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerCity.setAdapter(adapterCity);
        spinnerCity.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(this);




        Functions.isChecked2(companyName, next, cpNameTV);

        findViewById(R.id.addLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i >= 2) {
//                String link1 = link.getText().toString();
//                links[i] ="#" + link1;
//
//                tv1.setText(links[i]);
//                tv1.setVisibility(View.VISIBLE);
//                i++;
                    i--;
                    arrayList.add("#" + link.getText().toString().replaceAll("\\s","_"));
                    // next thing you have to do is check if your adapter has changed
                    adapter.notifyDataSetChanged();
                    linkss.setText("Add at least " + i + " links");


                } else {
                    arrayList.add("#" + link.getText().toString().replaceAll("\\s","_"));
                    // next thing you have to do is check if your adapter has changed
                    adapter.notifyDataSetChanged();
                    linkss.setText("Add at least " + i + " links");
                    add.setVisibility(View.INVISIBLE);
                    linkss.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"123", Toast.LENGTH_SHORT).show();
                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int si, long l) {
                i++;
                arrayList.remove(0);
                linkss.setText("Add at least " + i + " links");

//
                if (i >=0)
                {
                    linkss.setText("Add at least " + i + " links");
                    add.setVisibility(View.VISIBLE);
                    linkss.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }
        });

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
        Users.businessCompanyInfo.put("links",arrayList.get(0) + ", " + arrayList.get(1) + ", " + arrayList.get(2));
        if (cpName.isEmpty())
        {
            companyName.setError("Fill the field");
            companyName.requestFocus();
            next.getBackground().setAlpha(128);
        } else {
            reference.child(cpName).setValue(Users.businessCompanyInfo);

            Intent intent = new Intent(AddNewCompany.this, ChooseBusinessID.class);
            startActivity(intent);
        }
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
