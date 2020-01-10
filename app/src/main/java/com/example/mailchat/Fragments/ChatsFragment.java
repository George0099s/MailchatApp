package com.example.mailchat.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mailchat.Activities.MailchatFragment;
import com.example.mailchat.Adapters.ChatsAdapter;
import com.example.mailchat.Models.User;
import com.example.mailchat.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {
    private static final String TAG = "ChatsFragment";

    private ChatsAdapter mChatsAdapter;
    private RecyclerView mRecyclerView;

    private ArrayList<String> mChatNameList;
    private List<User> mUsersList = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_chats, container, false);




        readUsers();
        mRecyclerView = view.findViewById(R.id.chats_recycler_view1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    private void readUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsersList.clear();

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if(!user.getUserId().equals(firebaseUser.getUid())){
                        mUsersList.add(user);
                    }
                }

                mChatsAdapter = new ChatsAdapter(mUsersList, getContext());
                mRecyclerView.setAdapter(mChatsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
