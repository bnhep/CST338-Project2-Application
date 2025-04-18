package com.example.project2;

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
import com.example.project2.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private ApplicationRepository appRepository;
    String usernameEnter = ""; //Stores the input from textedit
    String passwordEnter = ""; //Stores the input from textedit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appRepository = ApplicationRepository.getRepository(getApplication());

        /*
         * This button calls getUserCredentials(), the method will validate username, if it exists
         * in the database then prompt the user to enter something else. If it does not exist,
         * the user will need to provide a password. The method will call insertCredentials() to
         * store the new account information to the database when user enters valid username and
         * password. Calls intent function to swap to the login screen.
         */
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserCredentials();
            }
        });

        /*
         * This button calls the intent method of LoginActivity and swaps back to the login screen.
         */
        binding.backButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /**
     * Reads in the User's potential username and password. Checks if both EditTexts are empty,
     * won't allow sign up if both are empty, username is empty, or password is empty.
     * If not empty it checks the database by using an observer, calls an onChanged function so that
     * it checks when LiveData changes, if username exists prompts user to enter a new username.
     * calls insertCredentials() to store to the data base then swaps to login screen.
     */
    private void getUserCredentials() {
        usernameEnter = binding.usernameSignUpEditText.getText().toString();
        passwordEnter = binding.passwordSignUpEditTextView.getText().toString();

        if (usernameEnter.isEmpty() && passwordEnter.isEmpty()){
            Toast.makeText(SignupActivity.this,
                    "Username and Password are blank.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (usernameEnter.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Username is blank. \nPlease enter a username",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordEnter.isEmpty()) {
            Toast.makeText(this, "Please enter a password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = appRepository.getUserByUserName(usernameEnter);
        //This has override onChanged because before the
        userObserver.observe(this, new Observer<>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    // Username exists so displays a toast to prompt user and goes back to the top
                    Toast.makeText(SignupActivity.this, "Username already exists",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Username does not exist, insert credentials
                    insertCredentials();
                    Toast.makeText(SignupActivity.this, "Sign up successful",
                            Toast.LENGTH_SHORT).show();
                    //Takes you back to the login screen after a successful account creation
                    Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                    startActivity(intent);
                }
                // Remove the observer after the first change to avoid repeated checks
                userObserver.removeObserver(this);
            }
        });
    }

    /**
     * Enters the stored values to the database
     */
    private void insertCredentials() {
        if (usernameEnter.isEmpty()) {
            return;
        }
        User userInfo = new User(usernameEnter, passwordEnter);
        appRepository.insertUser(userInfo);
    }

    /**
     * Creating Intent Factory to swap over to other activities
     */
    static Intent signUpIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }

}