package com.example.mailchat.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mailchat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewMessageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_message, container, false);
        return view ;

    }

    public static NewMessageFragment newInstance() {
        return new NewMessageFragment();
    }

}
