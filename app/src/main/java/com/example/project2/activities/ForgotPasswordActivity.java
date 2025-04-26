package com.example.project2.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityForgotPasswordBinding;

/**
 * This class handles the activity where a user will have the option to change their passwords
 * based off of the username they entered. The user will be prompted with a message to enter their
 * username. Prompt the user if the username does not exist, prompt the user if they leave the edit
 * text blank. If they successfully enter a valid username give them the option to type a new
 * password and type it again to confirm if that's the password they want to. Goes back to login
 * screen on successful password change.
 * @author Brandon Nhep
 * Date: 4/23/25
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    private ApplicationRepository repository;
    private String usernameEnter = "";
    boolean clickCondition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = ApplicationRepository.getInstance();

        /*
          back button to go back to the login screen
         */
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
          button that calls validate username based on the click condition.
          on first click will confirm that the username exists in the database, if not repeat the
          click. If the username exists change the layout and prompt the user to enter the new
          password.
         */
        binding.enterUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clickCondition) {
                    validateUsername();
                } else {
                    updatePassword();
                }
            }
        });

    }

    /**
     * Validates the user for a username to enter. If the username exists in the database then
     * check if the user is an admin so they cannot change an admins password, when a username
     * is valid then hide the layout and open a new layout for the new password. This class utilizes
     * LiveData as a way to observe a user based on the username. By doing this the ui will
     * auto-update based on the changes made in the user-table.
     */
    private void validateUsername() {
        usernameEnter = binding.usernameSignUpEditText.getText().toString();

        if (usernameEnter.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this,
                    "Username is blank.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(usernameEnter);
        userObserver.observe(this, new Observer<>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    if (!user.isAdmin()) {
                        binding.enterUsernameButton.setText("Reset Password");
                        binding.usernameSignUpEditText.setVisibility(View.GONE);
                        binding.newPasswordEditText.setVisibility(View.VISIBLE);
                        binding.confirmPasswordSignUpEditTextView.setVisibility(View.VISIBLE);
                        clickCondition = true;
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Success. Username is valid.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Username is invalid",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Username does not exist",
                            Toast.LENGTH_SHORT).show();
                }
                // Remove the observer after the first change to avoid repeated checks
                userObserver.removeObserver(this);
            }
        });


    }

    /**
     * This method validates the user's entered new password and the confirm password they type
     * in the edit texts. It will check if the edit texts are empty, then use live data to observe
     * and validate if both the new password and the confirm password are a match. If so then
     * change the password in the userTable in room database and exit the activity.
     */
    private void updatePassword() {
        String newPassword = binding.newPasswordEditText.getText().toString();
        String confirmPassword = binding.confirmPasswordSignUpEditTextView.getText().toString();
        if (newPassword.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this,
                    "New Password is blank.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this,
                    "Confirm Password is blank.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(usernameEnter);
        userObserver.observe(this, new Observer<>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(User user) {
                if (newPassword.equalsIgnoreCase(confirmPassword)) {
                    repository.setPasswordByUsername(newPassword, user.getUsername());
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Success. Password has been changed.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Passwords do not match.",
                            Toast.LENGTH_SHORT).show();
                }
                userObserver.removeObserver(this);
            }
        });

    }

    /**
     * This is the intent factory method for this activity. If the method is called and the return
     * value is stored in an intent. This will be passed to a start activity method in order to
     * swap to this activity.
     * @param context
     * @return new instantiated intent
     */
    public static Intent forgotPasswordIntentFactory(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

}