package com.example.mailchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    ArrayList<String> mNames;
    ArrayList<String> mPrice;
    ArrayList<android.graphics.drawable.Drawable> mImg;
    ArrayList<String> mJob;
    ArrayList<String> mType;
    ArrayList<String> mDate;
    ArrayList<String> mDate2;
    Context context;

    public MainAdapter(ArrayList<String> names,ArrayList<String> price,ArrayList<android.graphics.drawable.Drawable> img,ArrayList<String> job, ArrayList<String> type,ArrayList<String> date,ArrayList<String> date2, Context c){

        mNames = names;
        mPrice = price;
        mImg = img;
        mJob = job;
        mType = type;
        mDate = date;
        mDate2 = date2;
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

        holder.userName.setText(mNames.get(position));
//        holder.price.setText(mPrice.get(position));
//        holder.type.setText(mType.get(position));
        holder.date.setText(mDate.get(position));
//        holder.date2.setText(mDate2.get(position));
//        holder.image.setImageBitmap(mImg.get(position));


    }

    @Override
    public int getItemCount() {
        return mNames.size();    }

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
