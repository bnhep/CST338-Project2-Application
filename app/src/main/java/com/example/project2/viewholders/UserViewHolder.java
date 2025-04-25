package com.example.project2.viewholders;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.activities.UserLandingActivity;

import org.w3c.dom.Text;

public class UserViewHolder extends RecyclerView.ViewHolder {

    TextView usernameTextView;
    TextView passwordTextView;
    TextView userNameTitleTextView;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView = itemView.findViewById(R.id.usernameRecyclerViewText);
        passwordTextView = itemView.findViewById(R.id.passwordRecyclerViewText);
        userNameTitleTextView = itemView.findViewById(R.id.usernameTitleRecyclerTextView);
    }




}
