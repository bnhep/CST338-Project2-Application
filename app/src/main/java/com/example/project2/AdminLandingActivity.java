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

import com.example.project2.databinding.ActivityAdminLandingBinding;


public class AdminLandingActivity extends AppCompatActivity {

    ActivityAdminLandingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLandingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);


    }



    static Intent AdminLandingIntentFactory(Context context) {
        return new Intent(context, AdminLandingActivity.class);
    }

}