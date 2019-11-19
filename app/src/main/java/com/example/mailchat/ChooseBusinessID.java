package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChooseBusinessID extends AppCompatActivity {
    EditText mailchatID;
    String mailchat;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_business_id);
        mailchatID = findViewById(R.id.editText5);
        mailchatID.setText(Users.businessCompanyInfo.get("Company name"));
        mAuth = FirebaseAuth.getInstance();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        findViewById(R.id.goToCongrats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mailchat = mailchatID.getText().toString().replaceAll("#","");
                mailchat = mailchatID.getText().toString().replaceAll("\\s","_");

                String rec = mailchat +"#";

                mailchatID.setText(rec);
                addData();
                Toast.makeText(getApplicationContext(),"Your mailchatID added",Toast.LENGTH_SHORT);
                addData();




            }
        });
    }

    public void addData()
    {
        FirebaseUser user = mAuth.getCurrentUser();



        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();




            DatabaseReference reference = database.getReference("Business users/" + uid + "/Company");
        mailchat = mailchatID.getText().toString();
        if (mailchat.isEmpty())
        {
            mailchatID.setError("Fill the field");
            mailchatID.requestFocus();
        }
        if (mailchat.length() > 0) {


            Users.businessCompanyInfo.put("mailchatID", mailchat);


            reference.child(Users.businessCompanyInfo.get("Company name")).setValue(Users.businessCompanyInfo);

            startActivity(new Intent(ChooseBusinessID.this, CongratulationBusiness.class));
        }
    }


}
