package com.example.project2;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView iconView;
    TextView usernameTextView;
    TextView passwordTextView;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        iconView = itemView.findViewById(R.id.iconForUsers);
        usernameTextView = itemView.findViewById(R.id.usernameRecyclerViewText);
        passwordTextView = itemView.findViewById(R.id.passwordRecyclerViewText);
    }
}
