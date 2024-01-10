package com.learning.personal_expense_management.controller.profile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityCurrencyUnitProfileBinding;

public class CurrencyUnitActivity extends AppCompatActivity {
    ActivityCurrencyUnitProfileBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyUnitProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
