package com.example.android_notepad.until;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.android_notepad.R;

public class nightmode extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    boolean nightmode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        nightmode=sharedPreferences.getBoolean("nightmode",false);

        if(nightmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
