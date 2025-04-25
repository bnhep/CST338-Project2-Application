package com.example.project2.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.database.entities.User;

import java.util.List;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;
    private List<User> users;
    private UserSelectListener listener;

    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public UserViewAdapter(Context context, List<User> users, UserSelectListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
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


        holder.usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClicked(users.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }





}
