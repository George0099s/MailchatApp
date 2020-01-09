package com.example.mailchat.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mailchat.Activities.MailchatFragment;
import com.example.mailchat.Adapters.ChatsAdapter;
import com.example.mailchat.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {
    private ChatsAdapter mChatsAdapter;
    private RecyclerView mRecyclerView;

    private ArrayList<String> mChatNameList;
    private ArrayList<String> mSubjectMsgList;
    private ArrayList<String> mTextMsgList;
    private ArrayList<String> mRecievedTimeList;
    private BottomNavigationItemView mNewMessage;
    public ChatsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new ChatsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        mRecyclerView = container.findViewById(R.id.chats_recycler_view);

        mChatsAdapter = new ChatsAdapter(mChatNameList, mSubjectMsgList, mTextMsgList, mRecievedTimeList, getContext());
        mRecyclerView.setAdapter(mChatsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }
}
