package com.example.mailchat;

import android.app.ProgressDialog;
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
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_private_mailchat_id);

        userNameTV = findViewById(R.id.nameUser);

        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference ref;
        String name = Users.userInfo.get("first name");
        userNameTV.setText(name);

        recomendationED = findViewById(R.id.ourRec);

        findViewById(R.id.goToCongrats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recomendation = recomendationED.getText().toString().replaceAll("\\s","_");

                recomendationED.setText(recomendation+"#");
                rec = recomendation +"#";
                addData();
            }
        });
    }


    public void addData(){

//        Users.userInfo.put("maichatID", rec);
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();


       FirebaseDatabase db =  FirebaseDatabase.getInstance();
        DatabaseReference ref =   db.getReference("Users");




//        ref.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Users user = dataSnapshot.getValue(Users.class);
//
//                Log.d("123", "User name: " + user.getName() + ", email " + user.getEmail());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//// Failed to read value
//                Log.w("123", "Failed to read value.", error.toException());
//            }
//        });
    }

}
