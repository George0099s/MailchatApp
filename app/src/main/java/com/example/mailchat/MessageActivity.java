package com.example.mailchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mailchat.Adapters.MessageAdapter;
import com.example.mailchat.Models.Chat;
import com.example.mailchat.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";
    private ImageView mProfileImg;
    private FirebaseUser fUser;
    private DatabaseReference reference;
    private TextView mUserId, mUserName, mChatType;

    ImageButton mBtnSend;
    EditText mTextSend;
    DateFormat dateFormat;
    Date date;
    MessageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mUserName = findViewById(R.id.user_name);
        recyclerView = findViewById(R.id.recycler123);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutmanager = new LinearLayoutManager(getApplicationContext());
        linearLayoutmanager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutmanager);
        mTextSend = findViewById(R.id.text_send);
        mBtnSend = findViewById(R.id.btn_send);
        mProfileImg = findViewById(R.id.img_profile);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = getIntent().getStringExtra("userId");

        mBtnSend.setOnClickListener(v -> {
            String msg = mTextSend.getText().toString();
            if(!msg.equals("")){
                sendMessage(fUser.getUid(), userId, msg);
            } else {
                Toast.makeText(MessageActivity.this, "you cant send empty msg", Toast.LENGTH_SHORT).show();
            }
            mTextSend.setText("");
        });


        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mUserName.setText(user.getName() + " " + user.getLastName());
                if(user.getImageURL().equals("default")){
                    mProfileImg.setImageResource(R.mipmap.ic_launcher_round);
                } else {
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(mProfileImg);
                }
                readMessages(fUser.getUid(), userId, user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



         dateFormat = new SimpleDateFormat("HH:mm");


    }

    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        date = new Date();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("date",dateFormat.format(date));
        reference.child("Chats").push().setValue(hashMap);

    }
    private void readMessages(final String myId, final String userId, final String imageURL){
        mChat = new ArrayList<Chat>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) &&  chat.getSender().equals(myId)){
                        mChat.add(chat);
                    }


                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat, imageURL);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
