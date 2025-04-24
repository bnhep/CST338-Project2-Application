package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.databinding.ActivityUserLandingBinding;
import com.example.project2.databinding.ActivityViewUsersBinding;
import com.example.project2.viewholders.UserViewAdapter;

import java.util.ArrayList;

public class ViewUsersActivity extends AppCompatActivity {

    private static final String TAG = "VIEW_USERS_ACTIVITY_TAG";

    ActivityViewUsersBinding binding;
    private ApplicationRepository appRepository; //Performs the queries for the database

    private AccountStatusCheck accountManager;

    private UserViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserViewAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        appRepository = ApplicationRepository.getInstance();
        appRepository.getAllUsers().observe(this, users -> {
            Log.d(TAG, "Size of the list " + users.size() + " users");
            adapter.setUsers(users);
            Log.d(TAG, "Size of the adapter " + adapter.getItemCount());
        });
    }


    public static Intent userViewAdminIntentFactory(Context context){
        Intent intent = new Intent(context, ViewUsersActivity.class);
        return intent;
    }
}