package com.example.mailchat;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class ChoosePrivateMailchatID extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView userNameTV;
    EditText recomendationED;
    String recomendation;
    String rec;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_private_mailchat_id);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        userNameTV = findViewById(R.id.nameUser);
        mAuth = FirebaseAuth.getInstance();
        String name = Users.userInfo.get("first name");
        userNameTV.setText(name);
        recomendationED = findViewById(R.id.ourRec);
        recomendationED.setText(Functions.firstUpperCase(Users.userInfo.get("first name")+Functions.firstUpperCase(Users.userInfo.get("last name"))));
        findViewById(R.id.goToCongrats).setOnClickListener(view -> {
            recomendation = recomendationED.getText().toString().replaceAll("#","");
            recomendation = recomendationED.getText().toString().replaceAll("\\s","_");

            rec = recomendation +"#";

            recomendationED.setText(rec);
            addData();
            Toast.makeText(getApplicationContext(),"Your mailchatID added",Toast.LENGTH_SHORT);
        });
    }


    public void addData(){

        Users.userInfo.put("maichatID", rec);
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();


       FirebaseDatabase db =  FirebaseDatabase.getInstance();
        DatabaseReference ref =   db.getReference("Users");

        ref.child(userId).setValue(Users.userInfo);
        ref.push();
        Button btn = findViewById(R.id.goToCongrats);
        btn.setEnabled(false);

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
