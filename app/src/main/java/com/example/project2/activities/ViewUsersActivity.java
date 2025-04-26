package com.example.project2.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityViewUsersBinding;
import com.example.project2.viewholders.UserSelectListener;
import com.example.project2.viewholders.UserViewAdapter;

import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * Activity will be for the admin user. Allows them to check the list of users currently registered
 * or essentially what user's are stored in the database. Uses a recycler view to display the
 * user's username and password. When the user clicks on a username in the recycler view they will
 * get an alert dialog that prompts the user to delete or edit the user. A layout will display
 * if they choose to edit. Deleting or editing the data will update the ui and display on the
 * view.
 * @author Brandon Nhep
 * Date: 4/24/25
 */
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

        //Creates the recycler view
        recyclerViewCreate();

        /*
            back button to go back to the admin landing page.
         */
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
            button to to activate the change the username method
         */
        binding.changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });

        /*
            button to activate the change the password method
         */
        binding.changePasswordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }

    /**
     * Creates the recycler view by setting up the layout, then attaching an adapter to the view.
     * And setting up the data that needs to be displayed. Observes the userTable with
     * getAllUsers() in the instance of appRepository.
     */
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

    /**
     * This is the intent factory method for this activity. If the method is called and the return
     * value is stored in an intent. This will be passed to a start activity method in order to
     * swap to this activity.
     * @param context
     * @return new instantiated intent
     */
    public static Intent userViewAdminIntentFactory(Context context) {
        Intent intent = new Intent(context, ViewUsersActivity.class);
        return intent;
    }

    /**
     * Override onClick from the UserViewAdapter the click will function on the username text view
     * in the recycler view. When the user clicks it, it takes the instance of the user and receives
     * its data such as their ID and username. Store those values to use later in the change
     * password and username methods. Calls the alert dialog to prompt if the user want to delete,
     * edit or exit the menu.
     * @param user
     */
    @Override
    public void onUserClicked(User user) {
        userId = user.getId();
        usernameFromRecycler = user.getUsername();
        deleteUserAlertDialog();
    }

    /**
     * Prompts the user if they want to delete a user, edit, or exit the dialog. If a user chooses
     * delete the deleteUser() method will be called. Else if the user chooses to edit a layout
     * to edit username and password displays.
     */
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

    /**
     * Makes the layout for the change username and password visible
     */
    private void editUserDisplayUI() {
        binding.usernameLoginEditText.setVisibility(View.VISIBLE);
        binding.passwordLoginEditTextView.setVisibility(View.VISIBLE);
        binding.changePasswordsButton.setVisibility(View.VISIBLE);
        binding.changeUsernameButton.setVisibility(View.VISIBLE);
        binding.editingUserPrompt.setVisibility(View.VISIBLE);
        binding.editingUserPrompt.setText("You are editing " + usernameFromRecycler);
    }

    /**
     * Hides the layout for the change username and password menu.
     */
    private void editUserHideUI() {
        binding.usernameLoginEditText.setVisibility(View.INVISIBLE);
        binding.passwordLoginEditTextView.setVisibility(View.INVISIBLE);
        binding.changePasswordsButton.setVisibility(View.INVISIBLE);
        binding.changeUsernameButton.setVisibility(View.INVISIBLE);
        binding.editingUserPrompt.setVisibility(View.INVISIBLE);

    }

    /**
     * Observes the user by taking the username received from the click on the username in the
     * recycler view. Observes the user by getUserByUsername(usernameFromRecycler), the user
     * associated from that is validated if they are an admin, if they are they won't be deleted
     * else if they aren't then it deletes the associated creatures with that user's uniqueID
     * received from the recycler view click. Then deletes the user from the database.
     */
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

    /**
     * Prompts the admin to change the username. If the username edit text is empty then prompt
     * that it is empty. Observe with LiveData on the User by getUserByUserName check is the user
     * exists. If they don't then change the username by calling the appRepository instance
     * setUsernameByUsername(newPassword, username) and prompt user that name was changed.
     */
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

    /**
     * Prompts the admin to change the password. If the password edit text is empty then prompt
     * that it is empty. Observe with LiveData on the User by getUserByUserName check is the user
     * exists. Check is the password they entered is the same as the old one. If they are the same
     * prompt user to pick a new password. Else set the pass with a call from the appRepository
     * instance setPasswordByUsername(newPassword, username)
     */
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