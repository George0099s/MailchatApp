package com.example.mailchat.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mailchat.Activities.Start;
import com.example.mailchat.Models.User;
import com.example.mailchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MyProfileFragment extends Fragment {
    private static final String TAG = "MyProfileFragment";

    private ImageView signOut;
    private ImageView profileImg;
    private DatabaseReference mReference;
    private FirebaseUser mUser;
    private StorageReference mStorageReference;
    private TextView userName;


    public MyProfileFragment() {
        // Required empty public constructor
    }


    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
        }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        signOut = view.findViewById(R.id.sign_out_IV);
        signOut.setOnClickListener(v -> signOut());
        userName = view.findViewById(R.id.user_nameTV);
        profileImg = view.findViewById(R.id.profile_img);
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                userName.setText(user.getLastName() + " " + user.getName());

                if (user.getImageURL().equals("default")) {
                    profileImg.setImageResource(R.mipmap.ic_launcher_round);
                } else {
                    Glide.with(getContext()).load(user.getImageURL()).into(profileImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            return view;
    }


    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), Start.class));

    }

}
