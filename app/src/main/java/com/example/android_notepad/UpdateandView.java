package com.example.android_notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_notepad.until.nightmode;

public class UpdateandView extends com.example.android_notepad.until.nightmode {

    EditText title,notee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updateand_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        title = findViewById(R.id.title);
        notee = findViewById(R.id.notee);

        Intent intent=getIntent();
        String tittle = intent.getStringExtra("tittle");
        String note = intent.getStringExtra("note");

        title.setText(tittle);
        notee.setText(note);
    }
}