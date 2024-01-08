package com.learning.personal_expense_management.controller.profile;

import static android.os.Build.VERSION_CODES.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.controller.wallet.WalletActivity;
import com.learning.personal_expense_management.databinding.ActivityUpdateProfileBinding;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.services.FireStoreService;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;

public class UpdateProfileActivity extends AppCompatActivity {
    ActivityUpdateProfileBinding binding;
    UserProfile userProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userProfile = (UserProfile) intent.getSerializableExtra("userProfile");

        binding.etUserName.setText(userProfile.getName());
        binding.etUserEmail.setText(userProfile.getEmail());

        if(!binding.etUserName.getText().toString().isEmpty()){
            binding.tvRemindName.setVisibility(View.GONE);
        }
        if(!binding.etUserEmail.getText().toString().isEmpty()){
            binding.tvRemindEmail.setVisibility(View.GONE);
        }

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.tvRemindEmail.getVisibility()== View.GONE &&
                    binding.tvRemindName.getVisibility() == View.GONE){
                    UpdateProfile();
                }
                else {
                    showDialog();
                }
            }
        });

        binding.etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()){
                    binding.tvRemindName.setVisibility(View.GONE);
                }
                else {
                    binding.tvRemindName.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.etUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {    }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().isEmpty()){
                    binding.tvRemindEmail.setVisibility(View.VISIBLE);
                    binding.tvRemindEmail.setText("Vui lòng nhập email");
                }
                else {
                    try {
                        InternetAddress internetAddress = new InternetAddress(editable.toString());
                        internetAddress.validate();
                        binding.tvRemindEmail.setVisibility(View.GONE);
                    } catch (AddressException e) {
                        binding.tvRemindEmail.setVisibility(View.VISIBLE);
                        binding.tvRemindEmail.setText("Vui lòng nhập đúng email");
                    }
                }
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập đầy đủ và hợp lệ tất cả các thông tin!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void UpdateProfile(){
        try {
            userProfile.setName(binding.etUserName.getText().toString());
            userProfile.setEmail(binding.etUserEmail.getText().toString());
            String res = FireStoreService.updateUserProfile(userProfile);
            Toast.makeText(this, "Cập nhật hồ sơ thành công!",Toast.LENGTH_SHORT).show();

            finish();
            Intent intent = new Intent(UpdateProfileActivity.this, RootActivity.class);
            intent.putExtra("fragment", "ProfileFragment");
            startActivity(intent);
        }
        catch (Exception e){}
    }
}
