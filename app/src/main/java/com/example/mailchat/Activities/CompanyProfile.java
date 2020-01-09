package com.example.mailchat.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mailchat.Functions;
import com.example.mailchat.Models.User;
import com.example.mailchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class CompanyProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText webSite, cpNumber,address, workhours, aboutCompany;
    String web, companyNumber, companyAddress, companyWorkHours, about, t;
    FirebaseAuth mAuth;
    TextView skip,  webSiteTV, cpNumberTV,addressTV, workhoursTV, aboutCompanyTV;
    Button next, chooseImgbtn;
    Spinner spinerNums;


    ImageView userPhoto;
    private Uri filePath;
    String userID;

    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mAuth = FirebaseAuth.getInstance();
        webSiteTV= findViewById(R.id.web_site_tv);
        cpNumberTV= findViewById(R.id.phone_number_tv);
        addressTV= findViewById(R.id.address_tv);
        workhoursTV= findViewById(R.id.work_hours_tv);
        aboutCompanyTV= findViewById(R.id.about_company_tv);

        webSite = findViewById(R.id.webSite);
        cpNumber = findViewById(R.id.companyPhoneNumber);
        address = findViewById(R.id.address);
        workhours = findViewById(R.id.workHours);
        aboutCompany = findViewById(R.id.aboutCompany);
        skip = findViewById(R.id.skip);
        next = findViewById(R.id.GoToAddUser);
        spinerNums = findViewById(R.id.spinner_nums);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_nums);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Functions.nums_arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        Functions.isChecked2(webSite, next,webSiteTV);
        Functions.isChecked2(cpNumber, next,cpNumberTV);
        Functions.isChecked2(address, next,addressTV);
        Functions.isChecked2(workhours, next,workhoursTV);
        Functions.isChecked2(aboutCompany, next,aboutCompanyTV);
        findViewById(R.id.company_img).setOnClickListener(view -> chooseImage());
        findViewById(R.id.GoToAddUser).setOnClickListener(view -> {
            addData();
            Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();

            uploadImage();

        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        t  = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void addData(){

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        web = webSite.getText().toString();
        companyNumber = cpNumber.getText().toString() + t;
        companyAddress = address.getText().toString();
        companyWorkHours = workhours.getText().toString();
        about = aboutCompany.getText().toString();

        if (web != null && companyNumber != null && companyAddress != null && companyWorkHours != null && about !=null) {
            if (web.isEmpty()) {
                webSite.setError("fill the field");
                webSite.requestFocus();
            }
            if (companyNumber.isEmpty()) {
                cpNumber.setError("fill the field");
                cpNumber.requestFocus();
            }
            if (companyAddress.isEmpty()) {
                address.setError("fill the field");
                address.requestFocus();
            }
            if (companyWorkHours.isEmpty()) {
                workhours.setError("fill the field");
                workhours.requestFocus();
            }
            if (about.isEmpty()) {
                aboutCompany.setError("fill the field");
                aboutCompany.requestFocus();
            }

            DatabaseReference reference = database.getReference("Business users/" + uid + "/Company");
            User.businessCompanyInfo.put("web site", web);
            User.businessCompanyInfo.put("company number", companyNumber);
            User.businessCompanyInfo.put("company address", companyAddress);
            User.businessCompanyInfo.put("work hours", companyWorkHours);
            User.businessCompanyInfo.put("about company", about);

            reference.child(User.businessCompanyInfo.get("Company name")).setValue(User.businessCompanyInfo);
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

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                userPhoto = findViewById(R.id.company_img);
                userPhoto.setMinimumWidth(100);
                int dimensionInPixel = 100;
                int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());
                userPhoto.getLayoutParams().height = dimensionInDp;
                userPhoto.getLayoutParams().width = dimensionInDp;
                userPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                userPhoto.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ userID );
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        Log.d("123", "onSuccess: ");
                        progressDialog.dismiss();
                        Toast.makeText(CompanyProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.d("123", "onFailure: ");
                        progressDialog.dismiss();
                        Toast.makeText(CompanyProfile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        Log.d("123", "onProgress: ");
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    });
        }
    }
}
