package com.example.mailchat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mailchat.Adapters.ChatsAdapter;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {
    private ChatsAdapter mChatsAdapter;
    private RecyclerView mRecyclerView;

    private ArrayList<String> mChatNameList;
    private ArrayList<String> mSubjectMsgList;
    private ArrayList<String> mTextMsgList;
    private ArrayList<String> mRecievedTimeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        mChatNameList = new ArrayList<>();
        mSubjectMsgList = new ArrayList<>();
        mTextMsgList = new ArrayList<>();
        mRecievedTimeList = new ArrayList<>();


        mChatNameList.add("Artem");
        mSubjectMsgList.add("Greetion");
        mTextMsgList.add("123");
        mRecievedTimeList.add("11:21");
        mChatNameList.add("Artem");
        mSubjectMsgList.add("Greetion");
        mTextMsgList.add("123");
        mRecievedTimeList.add("01:23");
        mChatNameList.add("Artem");
        mSubjectMsgList.add("Christmas instalation instead");
        mTextMsgList.add("123");
        mRecievedTimeList.add("21:43");

        mRecyclerView = findViewById(R.id.chats_recycler_view);

        mChatsAdapter = new ChatsAdapter(mChatNameList, mSubjectMsgList, mTextMsgList, mRecievedTimeList, this);
        mRecyclerView.setAdapter(mChatsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
