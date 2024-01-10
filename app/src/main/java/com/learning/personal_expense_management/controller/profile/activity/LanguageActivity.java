package com.learning.personal_expense_management.controller.profile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityLanguageProfileBinding;

public class LanguageActivity extends AppCompatActivity {
    ActivityLanguageProfileBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
