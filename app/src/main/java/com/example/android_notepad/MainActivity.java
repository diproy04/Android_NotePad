package com.example.android_notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_notepad.Adapter.UserAdapter;
import com.example.android_notepad.Model.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView search;
    EditText searchbar;

    Switch darkmode;
    FloatingActionButton addbutton;
    boolean nightmode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    ArrayList<Users> usersArrayList;
    ArrayList<Users> fullUsersList;
    UserAdapter userAdapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        searchbar=findViewById(R.id.searchbar);
        search=findViewById(R.id.search);
        addbutton=findViewById(R.id.add);
        darkmode=findViewById(R.id.darkmode);
        sharedPreferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        editor=sharedPreferences.edit();
        nightmode=sharedPreferences.getBoolean("nightMode",false);

        recyclerView=findViewById(R.id.recycler);
        usersArrayList=new ArrayList<>();
        fullUsersList = new ArrayList<>();
        userAdapter=new UserAdapter(this,usersArrayList);
        recyclerView.setAdapter(userAdapter);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        search.setOnClickListener(view ->{
            String searchT=searchbar.getText().toString().trim();

            if (searchT.isEmpty()) {
                usersArrayList.clear();
                usersArrayList.addAll(fullUsersList);
                userAdapter.notifyDataSetChanged();
                return;
            }

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usersArrayList.clear();
                    boolean check = false;
                    for (DataSnapshot x : snapshot.getChildren()) {
                        Users user = x.getValue(Users.class);

                        if (user.getTittle().equalsIgnoreCase(searchbar.getText().toString())) {
                            check = true;
                            usersArrayList.add(user);
                        }
                    }
                    userAdapter.notifyDataSetChanged();

                    if (!check) {
                        usersArrayList.clear();
                        usersArrayList.addAll(fullUsersList);
                        Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });










        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear();
                fullUsersList.clear();
                for(DataSnapshot x:snapshot.getChildren()){
                    Users users=x.getValue(Users.class);
                    if (users != null) {
                        usersArrayList.add(users);
                        fullUsersList.add(users);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });

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