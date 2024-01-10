package com.learning.personal_expense_management.controller.profile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityNotifyProfileBinding;

public class NotifyActivity extends AppCompatActivity {
    ActivityNotifyProfileBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotifyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
