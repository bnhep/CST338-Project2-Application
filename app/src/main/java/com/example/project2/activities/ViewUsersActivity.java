package com.example.project2.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.UserDAO;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityUserLandingBinding;
import com.example.project2.databinding.ActivityViewUsersBinding;
import com.example.project2.viewholders.UserSelectListener;
import com.example.project2.viewholders.UserViewAdapter;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ViewUsersActivity extends AppCompatActivity implements UserSelectListener {


    private static final String TAG = "VIEW_USERS_ACTIVITY_TAG";

    ActivityViewUsersBinding binding;
    private ApplicationRepository appRepository; //Performs the queries for the database

    RecyclerView recyclerView;
    private UserViewAdapter adapter;

    String usernameFromRecycler;
    String usernameEntered;
    String passwordEntered;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appRepository = ApplicationRepository.getInstance();
        recyclerViewCreate();
    }

    private void recyclerViewCreate() {
        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserViewAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        appRepository.getAllUsers().observe(this, users -> {
            adapter.setUsers(users);
            Log.d(TAG, "Size of the adapter " + adapter.getItemCount());
            Log.d(TAG, "Size of the list " + users.size() + " users");
        });
    }


    public static Intent userViewAdminIntentFactory(Context context) {
        Intent intent = new Intent(context, ViewUsersActivity.class);
        return intent;
    }

    @Override
    public void onUserClicked(User user) {
        userId = user.getId();
        usernameFromRecycler = user.getUsername();
        deleteUserAlertDialog();
    }

    private void deleteUserAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("What do you want to do?");
        alertBuilder.setMessage("Do you want to modify the user?");

        alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser();
            }
        });
        alertBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editUser();
            }
        });
        alertBuilder.show();
    }

    private void editUser() {
        binding.usernameLoginEditText.setVisibility(View.VISIBLE);
        binding.passwordLoginEditTextView.setVisibility(View.VISIBLE);
        binding.changePasswordsButton.setVisibility(View.VISIBLE);
        binding.changeUsernameButton.setVisibility(View.VISIBLE);
        binding.editingUserPrompt.setVisibility(View.VISIBLE);
        binding.editingUserPrompt.setText("You are editing " + usernameFromRecycler);
    }

    private void deleteUser() {
        appRepository.getUserByUserName(usernameFromRecycler).observe(this, users -> {
            if (users != null) {
                if (!users.isAdmin()) {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
                        creatureDAO.deleteAllCreaturesByUserId(String.valueOf(userId));
                    });
                    appRepository.deleteUser(users);
                    Toast.makeText(this,
                            "User has been deleted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,
                            "Can't Delete an Admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}