package com.example.mailchat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mailchat.Activities.MailchatFragment;
import com.example.mailchat.Adapters.ChatsAdapter;
import com.example.mailchat.Fragments.ChatsFragment;
import com.example.mailchat.Fragments.MyProfileFragment;
import com.example.mailchat.Fragments.NewMessageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {
    private ChatsAdapter mChatsAdapter;
    private RecyclerView mRecyclerView;

    private ArrayList<String> mChatNameList;
    private ArrayList<String> mSubjectMsgList;
    private ArrayList<String> mTextMsgList;
    private ArrayList<String> mRecievedTimeList;
    private BottomNavigationItemView mNewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        BottomNavigationView navigation =  findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        mChatNameList = new ArrayList<>();
//        mSubjectMsgList = new ArrayList<>();
//        mTextMsgList = new ArrayList<>();
//        mRecievedTimeList = new ArrayList<>();
//        mChatNameList.add("Artem");
//        mSubjectMsgList.add("Greetion");
//        mTextMsgList.add("123");
//        mRecievedTimeList.add("11:21");
//        mChatNameList.add("Artem");
//        mSubjectMsgList.add("Greetion");
//        mTextMsgList.add("123");
//        mRecievedTimeList.add("01:23");
//        mChatNameList.add("Artem");
//        mSubjectMsgList.add("Christmas instalation instead");
//        mTextMsgList.add("123");
//        mRecievedTimeList.add("21:43");
//
//        mRecyclerView = findViewById(R.id.chats_recycler_view);
//
//        mChatsAdapter = new ChatsAdapter(mChatNameList, mSubjectMsgList, mTextMsgList, mRecievedTimeList, this);
//        mRecyclerView.setAdapter(mChatsAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
//            case R.id.folder:
//                loadFragment(ChatsFragment.newInstance());
//                return true;
            case R.id.new_message:
                loadFragment(NewMessageFragment.newInstance());
                return true;
            case R.id.profile:
                loadFragment(MyProfileFragment.newInstance());
                return true;
        }
        return false;
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}
