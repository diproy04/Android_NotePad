package com.example.android_notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Switch darkmode;

    FloatingActionButton addbutton;

    boolean nightmode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addbutton=findViewById(R.id.add);
        darkmode=findViewById(R.id.darkmode);
        sharedPreferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        editor=sharedPreferences.edit();
        nightmode=sharedPreferences.getBoolean("nightMode",false);

        addbutton.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this,CreateNote.class));
        });

        if(nightmode){
            darkmode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        darkmode.setOnClickListener(view ->{
            myThemes();
        });

    }

    private void myThemes() {
        if(nightmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("nightMode", false);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("nightMode", true);
        }
        editor.apply();
    }
}