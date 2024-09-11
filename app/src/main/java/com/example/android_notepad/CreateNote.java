package com.example.android_notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_notepad.Model.Users;
import com.example.android_notepad.until.nightmode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateNote extends com.example.android_notepad.until.nightmode {

    EditText tittle,note;
    ImageView backs;
    TextView backt;

    FloatingActionButton save;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_note);

        tittle=findViewById(R.id.tittle);
        note=findViewById(R.id.note);
        save=findViewById(R.id.save);
        backs=findViewById(R.id.backs);
        backt=findViewById(R.id.backt);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        save.setOnClickListener(view ->{
            if(tittle.getText().toString().isEmpty() || note.getText().toString().isEmpty()){
                Toast.makeText(this, "Title and Note cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                databaseReference.child(databaseReference.push().getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().setValue(new Users(tittle.getText().toString(),note.getText().toString()));
                        tittle.setText("");
                        note.setText("");
//                    startActivity(new Intent(CreateNote.this,MainActivity.class));
                        Toast.makeText(CreateNote.this, "Added Successfull", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CreateNote.this, "Created Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        backs.setOnClickListener(view ->{
            startActivity(new Intent(CreateNote.this,MainActivity.class));
        });
        backt.setOnClickListener(view ->{
            startActivity(new Intent(CreateNote.this,MainActivity.class));
        });

    }
}