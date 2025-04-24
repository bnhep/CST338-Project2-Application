package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.R;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.databinding.ActivitySelectAbilityToAddBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectAbilityToAddActivity extends AppCompatActivity {

    ActivitySelectAbilityToAddBinding binding;
    private List<CreatureEntity> abilityEntities = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectAbilityToAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpData();


    }

    private void setUpData() {
    }

    public static Intent SelectAbilityToAddIntentFactory(Context context) {
        Intent intent = new Intent(context, SelectAbilityToAddActivity.class);
        return intent;
    }
}