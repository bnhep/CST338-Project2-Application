package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.database.ApplicationRepository;
import com.example.project2.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private ApplicationRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ApplicationRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: TEMPORARY NEED TO ADD A VERIFICATION IF USER/PASSWORD CORRECT OR EVEN EXISTS
                Toast.makeText(LoginActivity.this, "Somehow you logged in", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        binding.signUpButtonLoginMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: THIS WILL GO TO THE SIGNUP PAGE USER WILL HAVE OPTION TO COME BACK
                Intent intent = SignupActivity.signUpIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }


    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }


}