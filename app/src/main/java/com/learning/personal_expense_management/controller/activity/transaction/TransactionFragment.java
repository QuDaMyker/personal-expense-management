package com.learning.personal_expense_management.controller.activity.transaction;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.type.DateTime;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.FragmentTransactionBinding;

import java.time.Year;

public class TransactionFragment extends Fragment {
    private FragmentTransactionBinding binding;
    private int currentYear;
    private int currentMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_transaction, container, false);
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        init();
        setListeners();


        return view;
    }

    public void init() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        setYear(currentYear);
        setMonth(currentMonth);
    }

    private void setListeners() {
        binding.imgFilter.setOnClickListener(v -> {

        });

        binding.imgPreYear.setOnClickListener(v -> {
            int showYear = Integer.parseInt(binding.tvYear.getText().toString());
            setYear(--showYear);
        });

        binding.imgNextYear.setOnClickListener(v -> {
            int showYear = Integer.parseInt(binding.tvYear.getText().toString());
            setYear(++showYear);
        });

        binding.imgPreMonth.setOnClickListener(v -> {
            String s = binding.tvMonth.getText().toString();
            String ss = s.replaceAll("\\D+", "");
            Log.d("m", ss);

            try {
                int showMonth = Integer.parseInt(ss);
                showMonth--;

                if (showMonth < 1) {
                    showMonth = 12;
                }

                setMonth(showMonth);
            } catch (NumberFormatException e) {

            }
        });

        binding.imgNextMonth.setOnClickListener(v -> {
            String s = binding.tvMonth.getText().toString();
            String ss = s.replaceAll("\\D+", "");

            try {
                int showMonth = Integer.parseInt(ss);
                showMonth++;

                if (showMonth > 12) {
                    showMonth = 1;
                }

                setMonth(showMonth);
            } catch (NumberFormatException e) {

            }
        });


    }

    private void setYear(int year) {
        binding.tvYear.setText(String.valueOf(year));
    }


    private void setMonth(int month) {
        int previousMonth = month - 1;
        int nextMonth = month + 1;

        if (previousMonth < 1) {
            previousMonth = 12;
        }

        if (nextMonth > 12) {
            nextMonth = 1;
        }

        binding.tvMonthPre.setText("Tháng " + previousMonth);
        binding.tvMonth.setText("Tháng " + month);
        binding.tvMonthNext.setText("Tháng " + nextMonth);
    }

}