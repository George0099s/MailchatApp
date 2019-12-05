package com.example.mailchat;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    private static final String TAG = "MessageFragment";
    ArrayList<String> mNamesList;
    ArrayList<String> mPriceList;
    ArrayList<String> mImgList;
    ArrayList<String> mJob;
    ArrayList<String> mType;
    ArrayList<String> mDateList;
    ArrayList<String> mDate2;
    Context context;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManger;
    MainAdapter mAdapter;
    JSONArray arr = new JSONArray();

    private List listItems;
    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_message, container, false);
        context = getActivity().getApplicationContext();
        mLayoutManger = new LinearLayoutManager(context);
        mImgList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.rcView);
        mNamesList = new ArrayList<>();
        mDateList = new ArrayList<>();
        mPriceList = new ArrayList<>();
        listItems = new ArrayList<>();

        Api();
        return view;

    }

    public void Api(){



        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = "https://api.manana.life/v2/search.getlist";

        mAdapter = new MainAdapter(mNamesList, mPriceList, mImgList, mJob, mType, mDateList, mDate2, context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String mStartTime, mEndTime, mName, mDate, mPosition, mPrice, mImg;


                        try {
                            JSONObject object = response.getJSONObject("response");
                            arr = object.getJSONArray("items");




                            for (int s = 0; s < arr.length(); s++) {

                                JSONObject o = (JSONObject) arr.get(s);


                                listItems.add(o);

                                mStartTime = o.getString("start_time");
                                mDate = o.getString("date");
                                mEndTime = o.getString("end_time");
                                mPrice = o.getString("salary");
                                mImg = o.getString("avatar");
                                mImgList.add(mImg);
                                mDateList.add(mDate);
                                Log.d(TAG, "onResponse: " + mDate);
                                mNamesList.add(mDate);
                                mPriceList.add(mPrice);

                            }



                            mRecyclerView.setHasFixedSize(true);
                            mRecyclerView.setLayoutManager(mLayoutManger);
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        Log.e("response error", error.toString());
                    }
                }


        );

        requestQueue.add(objectRequest);

    }
}
