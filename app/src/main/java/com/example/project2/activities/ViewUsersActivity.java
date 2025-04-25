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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });

        binding.changePasswordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

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
                editUserDisplayUI();
            }
        });
        alertBuilder.show();
    }

    private void editUserDisplayUI() {
        binding.usernameLoginEditText.setVisibility(View.VISIBLE);
        binding.passwordLoginEditTextView.setVisibility(View.VISIBLE);
        binding.changePasswordsButton.setVisibility(View.VISIBLE);
        binding.changeUsernameButton.setVisibility(View.VISIBLE);
        binding.editingUserPrompt.setVisibility(View.VISIBLE);
        binding.editingUserPrompt.setText("You are editing " + usernameFromRecycler);
    }

    private void editUserHideUI() {
        binding.usernameLoginEditText.setVisibility(View.INVISIBLE);
        binding.passwordLoginEditTextView.setVisibility(View.INVISIBLE);
        binding.changePasswordsButton.setVisibility(View.INVISIBLE);
        binding.changeUsernameButton.setVisibility(View.INVISIBLE);
        binding.editingUserPrompt.setVisibility(View.INVISIBLE);

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
                            "Can not delete an Admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changeUsername() {
        usernameEntered = binding.usernameLoginEditText.getText().toString();
        if (usernameEntered.isEmpty()) {
            Toast.makeText(this,
                    "username is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> observeUser = appRepository.getUserByUserName(usernameEntered);
        observeUser.observe(this, new Observer<>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Toast.makeText(ViewUsersActivity.this,
                            "Username already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    appRepository.setUsernameByUsername(usernameEntered, usernameFromRecycler);
                    editUserHideUI();
                    Toast.makeText(ViewUsersActivity.this,
                            "Changing username.", Toast.LENGTH_SHORT).show();
                }

                observeUser.removeObserver(this);
            }
        });
    }


    private void changePassword() {
        passwordEntered = binding.passwordLoginEditTextView.getText().toString();
        if (passwordEntered.isEmpty()) {
            Toast.makeText(this,
                    "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> observeUser = appRepository.getUserByUserName(usernameFromRecycler);
        observeUser.observe(this, new Observer<>() {
            @Override
            public void onChanged(User user) {
                if (!passwordEntered.equalsIgnoreCase(user.getPassword())) {
                    appRepository.setPasswordByUsername(passwordEntered, usernameFromRecycler);
                    editUserHideUI();
                    Toast.makeText(ViewUsersActivity.this,
                            "Success. Password has been changed.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewUsersActivity.this,
                            "Enter a new password.",
                            Toast.LENGTH_SHORT).show();
                }

                observeUser.removeObserver(this);
            }
        });
    }


}