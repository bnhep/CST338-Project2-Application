package com.example.project2.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;
    private List<User> users;

    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public UserViewAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.user_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User current = users.get(position);
        holder.usernameTextView.setText(current.getUsername());
        holder.passwordTextView.setText(current.getPassword());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
