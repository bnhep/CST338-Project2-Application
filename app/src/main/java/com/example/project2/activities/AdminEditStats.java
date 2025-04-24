package com.example.project2.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.R;
import com.example.project2.databinding.ActivityAddMonstersBinding;
import com.example.project2.databinding.ActivityAdminEditStatsBinding;

public class AdminEditStats extends AppCompatActivity {

    ActivityAdminEditStatsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminEditStatsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}