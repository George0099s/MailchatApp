package com.example.mailchat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mailchat.MessageActivity;
import com.example.mailchat.Models.User;
import com.example.mailchat.R;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

//    private ArrayList<String> mChatNameList;
//    private ArrayList<String> mSybjectMsgList;
//    private ArrayList<String> mTextMsgList;
//    private ArrayList<String> mRecievedTimeList;
    private List<User> mUsers;
    private Context mContext;

    public ChatsAdapter( List<User> mUsers , Context context) {
        this.mUsers = mUsers;
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
        final User user = mUsers.get(position);

        holder.mUserName.setText(user.getName() + " " + user.getLastName());
        if(user.getImageURL().equals("default")){
            holder.img.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Glide.with(mContext).load(user.getImageURL()).into(holder.img);
        }
        holder.itemView.setOnClickListener(v-> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra("userId", user.getUserId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder {
        TextView mUserName, mMsgSubject, mTextMsg, mTimeMsgRecieved;
        public ImageView img;
        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            mUserName = itemView.findViewById(R.id.user_name);
            mMsgSubject = itemView.findViewById(R.id.msg_subject);
            mTextMsg = itemView.findViewById(R.id.msg_text);
            mTimeMsgRecieved = itemView.findViewById(R.id.msg_time);
        }

    }
}
