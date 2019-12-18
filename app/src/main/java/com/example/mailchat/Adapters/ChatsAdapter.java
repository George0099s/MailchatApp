package com.example.mailchat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mailchat.MessageActivity;
import com.example.mailchat.Models.Users;
import com.example.mailchat.R;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

    private ArrayList<String> mChatNameList;
    private ArrayList<String> mSybjectMsgList;
    private ArrayList<String> mTextMsgList;
    private ArrayList<String> mRecievedTimeList;
    private Context mContext;
    Users users = new Users();
    public ChatsAdapter(ArrayList<String> mChatNameList, ArrayList<String> mSybjectMsgList, ArrayList<String> mTextMsgList, ArrayList<String> mRecievedTimeList, Context context) {
        this.mChatNameList = mChatNameList;
        this.mSybjectMsgList = mSybjectMsgList;
        this.mTextMsgList = mTextMsgList;
        this.mRecievedTimeList = mRecievedTimeList;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        holder.mUserName.setText(mChatNameList.get(position));
        holder.mMsgSubject.setText(mSybjectMsgList.get(position));
        holder.mTextMsg.setText(mTextMsgList.get(position));
        holder.mTimeMsgRecieved.setText(mRecievedTimeList.get(position));
        holder.itemView.setOnClickListener(v-> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra("userId", users.getUserId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mChatNameList.size();
    }


    public class ChatsViewHolder extends RecyclerView.ViewHolder {

        TextView mUserName, mMsgSubject, mTextMsg, mTimeMsgRecieved;


        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.user_name);
            mMsgSubject = itemView.findViewById(R.id.msg_subject);
            mTextMsg = itemView.findViewById(R.id.msg_text);
            mTimeMsgRecieved = itemView.findViewById(R.id.msg_time);
        }

    }
}
