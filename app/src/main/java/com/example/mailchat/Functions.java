package com.example.mailchat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;

public class Functions {

//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

//
//    private boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        return cm.getActiveNetworkInfo() != null;
//    }

    public static boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static void isNotEmpty(String s, Button btn)
    {

            if (s.isEmpty()) {
                btn.getBackground().setAlpha(128);
            }
            if (s.length() > 0) {
                btn.getBackground().setAlpha(255);
            }

    }
}
