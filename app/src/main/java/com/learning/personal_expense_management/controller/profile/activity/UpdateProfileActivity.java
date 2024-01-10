package com.learning.personal_expense_management.controller.profile.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.databinding.ActivityUpdateProfileBinding;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.UserProfileListener;
import com.learning.personal_expense_management.utilities.Constants;
import com.squareup.picasso.Picasso;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class UpdateProfileActivity extends AppCompatActivity {
    private ActivityUpdateProfileBinding binding;
    private UserProfile userProfile;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
        getFunctions();


    }

    private void init() {
        binding.textView2.setVisibility(View.GONE);
        binding.tvRemindEmail.setVisibility(View.GONE);
        binding.etUserEmail.setVisibility(View.GONE);
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.tvRemindEmail.getVisibility() == View.GONE &&
                        binding.tvRemindName.getVisibility() == View.GONE) {
                    UpdateProfile();
                } else {
                    showDialog();
                }
            }
        });

        binding.etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    binding.tvRemindName.setVisibility(View.GONE);
                } else {
                    binding.tvRemindName.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.etUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().isEmpty()) {
                    binding.tvRemindEmail.setVisibility(View.VISIBLE);
                    binding.tvRemindEmail.setText("Vui lòng nhập email");
                } else {
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

    private void getFunctions() {
        getProfile(FirebaseAuth.getInstance().getUid());
    }

    private void getProfile(String uid) {
        FireStoreService.isExistProfile(uid, new UserProfileListener() {
            @Override
            public void onExist(boolean isExist) {

            }

            @Override
            public void onUserProfilesLoaded(UserProfile userProfile) {
                Log.e("userProfile", userProfile.toString());
                Picasso.get().load(userProfile.getPhotoUrl()).into(binding.civUserImage);
                binding.etUserName.setText(userProfile.getName());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập đầy đủ và hợp lệ tất cả các thông tin!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void UpdateProfile() {
        try {
            userProfile.setName(binding.etUserName.getText().toString());
            userProfile.setEmail(binding.etUserEmail.getText().toString());
            String res = FireStoreService.updateUserProfile(userProfile);
            Toast.makeText(this, "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();

            finish();
            Intent intent = new Intent(UpdateProfileActivity.this, RootActivity.class);
            intent.putExtra("fragment", "ProfileFragment");
            startActivity(intent);
        } catch (Exception e) {
        }
    }
}
