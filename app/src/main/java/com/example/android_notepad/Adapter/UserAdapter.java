package com.example.android_notepad.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_notepad.MainActivity;
import com.example.android_notepad.MainActivity2;
import com.example.android_notepad.Model.Users;
import com.example.android_notepad.R;

import java.time.Instant;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVierHolder> {
    ArrayList<Users> usersArrayList;
    Context context;

    public UserAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.UserVierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notecard,parent,false);
        return new UserVierHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserVierHolder holder, int position) {
        holder.tittle.setText(usersArrayList.get(position).getTittle());
        holder.note.setText(usersArrayList.get(position).getNote());

        holder.itemView.setOnClickListener(view ->{
            Intent i=new Intent(context, MainActivity2.class);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class UserVierHolder extends RecyclerView.ViewHolder {
        TextView tittle,note;
        public UserVierHolder(@NonNull View itemView) {
            super(itemView);
            tittle=itemView.findViewById(R.id.tittle);
            note=itemView.findViewById(R.id.note);
        }
    }
}
