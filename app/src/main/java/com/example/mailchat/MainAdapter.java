package com.example.mailchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    ArrayList<String> mNamesList;
    ArrayList<String> mPriceList;
    ArrayList<String> mImgList;
    ArrayList<String> mJobList;
    ArrayList<String> mTypeList;
    ArrayList<String> mDateList;
    ArrayList<String> mStartTime;
    Context context;

    public MainAdapter(ArrayList<String> names,ArrayList<String> price,ArrayList<String> img,ArrayList<String> job, ArrayList<String> type,ArrayList<String> date,ArrayList<String> startTime, Context c){
        mNamesList = names;
        mPriceList = price;
        mImgList = img;
        mJobList = job;
        mTypeList = type;
        mDateList = date;
        mStartTime = startTime;
        this.context = c;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {

        holder.userName.setText(mNamesList.get(position));
        holder.price.setText(mPriceList.get(position));
//        holder.type.setText(mType.get(position));
        holder.date.setText(mDateList.get(position));
//        holder.date2.setText(mDate2.get(position));
//        holder.image.setImageBitmap(mImg.get(position));

        Glide.with(this.context)
                .load(mImgList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mNamesList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        public TextView userName;
        public TextView price;
        public ImageView image;


        public TextView type;
        public TextView date2;
        public TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price =  itemView.findViewById(R.id.price);
            userName = itemView.findViewById(R.id.textView25);
            image = itemView.findViewById(R.id.imageView10);

            type  = itemView.findViewById(R.id.textView43);
            date  = itemView.findViewById(R.id.date);
        }
    }
}
