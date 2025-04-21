package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private ApplicationRepository repository;
    private AccountStatusCheck accountManager;
    boolean adminCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ApplicationRepository.getInstance();
        accountManager = AccountStatusCheck.getInstance();


        /*
         * This button calls userValidation(); the method will validate username and password,
         * If it does not exist in the database it won't allow the user to sign in. User must go
         * create an account. If it does exist then validates user entry by checking if
         * username and password combination is correctly entered.
         */
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValidation();
            }
        });

        /*
         * This button calls signupActivity intent, swaps to sign up menu
         */
        binding.signUpButtonLoginMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignupActivity.signUpIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    /**
     * This function serves to validate the user's login information. Checks if user enters a
     * username, if not prompt user to enter one and not leave it blank. Create an observer to
     * manage the live data. If user enters a password and its not equal to the password in the
     * database then prompt invalid password else it will successfully login.
     */
    private void userValidation() {
        String username = binding.usernameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditTextView.getText().toString();
        if (username.isEmpty() && password.isEmpty()){
            Toast.makeText(LoginActivity.this,
                    "Username and Password are blank.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username is blank. \nPlease enter a username",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this,
                    "Password is blank.\nPlease enter a password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (user != null) {
                    if (password.equals(user.getPassword())) {
                        //TODO: ADD A IF/ELSE TO DETERMINE IN USER IS AN ADMIN OR NOT
                        // IF THEY ARE AN ADMIN GO TO ADMIN SCREEN
                        Intent intent;
                        adminCheck = user.isAdmin();
                        if(!adminCheck) {
                            //moves to the MainActivity(basic user) page
                            accountManager.setUserID(user.getId());
                            accountManager.setUserName(user.getUsername());
                            accountManager.setIsAdminStatus(user.isAdmin());
                            intent = MainActivity.MainIntentFactory(getApplicationContext());

                        }
                        else{
                            //moves to the AdminLandingActivity page
                            accountManager.setUserID(user.getId());
                            accountManager.setUserName(user.getUsername());
                            accountManager.setIsAdminStatus(user.isAdmin());
                            intent = AdminLandingActivity.AdminLandingIntentFactory(getApplicationContext());
                        }
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Password Invalid. Please Enter a Password",
                                Toast.LENGTH_SHORT).show();
                        binding.passwordLoginEditTextView.setSelection(0);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, username + " is invalid.",
                            Toast.LENGTH_SHORT).show();
                    binding.usernameLoginEditText.setSelection(0);
                }
                // Remove the observer after the first change to avoid repeated checks
                userObserver.removeObserver(this);
            }
        });
    }

    /**
     * Creating Intent Factory to swap over to other activities
     */
    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }


}