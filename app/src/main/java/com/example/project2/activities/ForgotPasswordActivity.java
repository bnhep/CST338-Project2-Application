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


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
                    if (!user.isAdmin()) {//Changes the menu to a menu to enter the passwords and confirm.
                        binding.enterUsernameButton.setText("Reset Password");
                        binding.usernameSignUpEditText.setVisibility(View.GONE);
                        binding.newPasswordEditText.setVisibility(View.VISIBLE);
                        binding.confirmPasswordSignUpEditTextView.setVisibility(View.VISIBLE);
                        clickCondition = true;
                        Toast.makeText(ForgotPasswordActivity.this, "Success. Username is valid.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Username is invalid",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Username does not exist",
                            Toast.LENGTH_SHORT).show();
                }
                // Remove the observer after the first change to avoid repeated checks
                userObserver.removeObserver(this);
            }
        });


    }

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


    public static Intent forgotPasswordIntentFactory(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

}