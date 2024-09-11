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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateandView extends com.example.android_notepad.until.nightmode {

    EditText title,notee;
    ImageView delete,backs;
    TextView update,back;

    DatabaseReference databaseReference;

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
        title=findViewById(R.id.title);
        notee=findViewById(R.id.notee);
        delete=findViewById(R.id.delete);
        update=findViewById(R.id.update);
        backs=findViewById(R.id.backs);
        back=findViewById(R.id.back);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        Intent intent=getIntent();
        String tittle = intent.getStringExtra("tittle");
        String note = intent.getStringExtra("note");

        title.setText(tittle);
        notee.setText(note);

        delete.setOnClickListener(view ->{
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean  check=false;
                    for(DataSnapshot x:snapshot.getChildren()){
                        Users users=x.getValue(Users.class);
                        if(users.getNote().equals(notee.getText().toString())){
                            x.getRef().removeValue();
                            check=true;
                            startActivity(new Intent(UpdateandView.this,MainActivity.class));
                            Toast.makeText(UpdateandView.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(!check){
                        Toast.makeText(UpdateandView.this, "Deleted Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        update.setOnClickListener(view ->{
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean check = false;
                    for (DataSnapshot x:snapshot.getChildren()) {
                        Users user=x.getValue(Users.class);
                        if (user != null && user.getNote().equals(notee.getText().toString())) {
                            Users updatedUser = new Users(title.getText().toString(), notee.getText().toString());
                            x.getRef().setValue(updatedUser);
                            check = true;
                        }
                    }
                    if (check) {
                        Toast.makeText(UpdateandView.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateandView.this, MainActivity.class));

                    } else {
                        Toast.makeText(UpdateandView.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        backs.setOnClickListener(view ->{
            startActivity(new Intent(UpdateandView.this,MainActivity.class));
        });
        back.setOnClickListener(view ->{
            startActivity(new Intent(UpdateandView.this,MainActivity.class));
        });
    }
}