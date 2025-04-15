package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        //TODO THIS TAKES IN DATA FOR USER ENTITY, SHOULD VALIDATE USERNAME IF ONE IS ALREADY CREATED
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserCredentials(); //User input in the textedit of the menu username/password
                insertCredentials(); //Inserts them into the database

                //TODO VALIDATE METHOD HERE IF SIGNUP IS GOOG THEN GO TO LOGIN MENU(simple...I think)
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        appRepository = ApplicationRepository.getRepository(getApplication());
    }



    /**
     * Reads in the User's potential username and password
     */
    private void getUserCredentials() {
        usernameEnter = binding.usernameSignUpEditText.getText().toString();
        passwordEnter = binding.passwordSignUpEditTextView.getText().toString();

    }

    /**
     * Enters the stored values to the repository
     */
    private void insertCredentials() {
        if(usernameEnter.isEmpty()){
            return;
        }
        User userInfo = new User(usernameEnter, passwordEnter);
        appRepository.insertUser(userInfo);
    }

    /**
     * Creating Intent Factory to swap over to other activities
     */
    static Intent signUpIntentFactory(Context context){
        return new Intent(context, SignupActivity.class);
    }


    /**
     * verify the user for signup
     */
}