package com.example.mailchat;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MailchatFragment extends Fragment {


    public MailchatFragment() {
        // Required empty public constructor
    }
    public static MailchatFragment newInstance(){
        return new MailchatFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mailchat, container, false);
    }

}
