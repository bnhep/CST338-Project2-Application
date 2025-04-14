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

import com.example.project2.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TESTING PURPOSES this will just go to the mainactivity for now but should go to a
        //Login screen
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling intent factory in main
                Intent intent = MainActivity.MainIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }


    /**
     * Creating Intent Factory to swap over to other activities
     */
    static Intent signUpIntentFactory(Context context){
        return new Intent(context, SignupActivity.class);
    }
}