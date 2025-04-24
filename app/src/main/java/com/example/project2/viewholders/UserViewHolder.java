package com.example.project2.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView iconView;
    TextView usernameTextView;
    TextView passwordTextView;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView = itemView.findViewById(R.id.usernameRecyclerViewText);
        passwordTextView = itemView.findViewById(R.id.passwordRecyclerViewText);
    }
}
