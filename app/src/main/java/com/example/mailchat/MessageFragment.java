package com.example.mailchat;


import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    ArrayList<String> mNames;
    ArrayList<String> mPrice;
    ArrayList<String> mImg;
    ArrayList<String> mJob;
    ArrayList<String> mType;
    ArrayList<String> mDate;
    ArrayList<String> mDate2;
    Context context;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManger;
    RecyclerView.Adapter mAdapter;
    JSONArray arr = new JSONArray();

    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_message, container, false);
       mRecyclerView = view.findViewById(R.id.rcView);
        context = getActivity().getApplicationContext();
        mLayoutManger = new LinearLayoutManager(context);
      mNames = new ArrayList<>();
       mNames.add("ssss");




        mRecyclerView.setHasFixedSize(true);


        mAdapter = new MainAdapter(mNames, mPrice, mImg, mJob, mType, mDate, mDate2, context);
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);
        return view;



    }
}
