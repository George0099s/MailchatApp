package com.example.mailchat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Functions {
   public static String[] nums_arr = {"+7", "+1", "+1", "+4"};
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

    public static void isChecked(final EditText editText, final Button btn) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 10) {

                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    btn.getBackground().setAlpha(128);
                    btn.setEnabled(false);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 9) {

                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    btn.getBackground().setAlpha(128);
                    btn.setEnabled(false);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                }
            }
        });
    }

    public static void isChecked2(final EditText editText, final Button btn, final TextView textView) {
        textView.setVisibility(View.INVISIBLE);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    textView.setVisibility(View.VISIBLE);
                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    textView.setVisibility(View.INVISIBLE);
                    btn.getBackground().setAlpha(128);
                    btn.setEnabled(false);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                }
            }
        });
    }

    public static void isCheckedPhone(final EditText editText, final Button btn, final TextView textView) {
        textView.setVisibility(View.INVISIBLE);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 10) {
                    textView.setVisibility(View.VISIBLE);
                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    textView.setVisibility(View.INVISIBLE);
                    btn.getBackground().setAlpha(128);
                    btn.setEnabled(false);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                }
            }
        });
    }

    public static void isCheckedNums(final EditText editText, final Button btn, int s) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == s) {

                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);

                } else {
                    btn.getBackground().setAlpha(128);
                    btn.setEnabled(false);
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                }
            }
        });
    }

    public static String firstUpperCase(String word){
        if (word == null || word.isEmpty()){
            return "String is empty";
        }
        return word.substring(0,1).toUpperCase()+word.substring(1);
    }

    public static void textAppearence(EditText ed, TextView tv){
        tv.setVisibility(View.INVISIBLE);
        if (ed.length() > 0){
            tv.setVisibility(View.VISIBLE);
        }
    }

}
