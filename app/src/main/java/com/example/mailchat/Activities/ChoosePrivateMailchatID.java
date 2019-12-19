package com.example.mailchat.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mailchat.Functions;
import com.example.mailchat.InboxActivity;
import com.example.mailchat.Models.Users;
import com.example.mailchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChoosePrivateMailchatID extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView userNameTV;
    EditText recomendationED;
    String recomendation;
    String rec;
    DatabaseReference ref;
    private static final String TAG = "ChoosePrivateMailchatID";
    ArrayList<String> mailchatIDList = new ArrayList<>();

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
        findViewById(R.id.go_to_inbox).setOnClickListener(view -> {
            recomendation = recomendationED.getText().toString().replaceAll("#","");
            recomendation = recomendationED.getText().toString().replaceAll("\\s","_");

            rec = recomendation +"#";

            recomendationED.setText(rec);
            addData();
            Toast.makeText(getApplicationContext(),"Your mailchatID added",Toast.LENGTH_SHORT);

            Intent intent = new Intent (ChoosePrivateMailchatID.this, InboxActivity.class);
            startActivity(intent);
        });
    }


    public void addData(){
//        Users.userInfo.put("maichatID", rec);
//        FirebaseUser user = mAuth.getCurrentUser();
//        String userId = user.getUid();
//
//
//        FirebaseDatabase db =  FirebaseDatabase.getInstance();
//        DatabaseReference ref =   db.getReference("Users");
//        ref.child(userId).setValue(Users.userInfo);
//        ref.push();
//        Button btn = findViewById(R.id.goToCongrats);
//        btn.setEnabled(false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users"); // reference is 'chat' because we created the database at /chat

        reference.addValueEventListener(new ValueEventListener() {
            String mailchatID;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    mailchatID = (String) messageSnapshot.child("maichatID").getValue();
                    mailchatIDList.add(mailchatID);
                }
                for (int i = 0; i < mailchatIDList.size(); i++) {
                    if (rec.equals(mailchatIDList.get(i))) {
                        Toast.makeText(ChoosePrivateMailchatID.this, "mailchatID already exist", Toast.LENGTH_SHORT).show();

                    }
                    if (rec != mailchatIDList.get(i)) {
                        Toast.makeText(ChoosePrivateMailchatID.this, "MailchayID added", Toast.LENGTH_SHORT).show();
                        Users.userInfo.put("maichatID", rec);
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userId = user.getUid();


                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference ref = db.getReference("Users");
                        ref.child(userId).setValue(Users.userInfo);
                        ref.push();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
