package com.example.mailchat.Activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mailchat.R;


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
