package com.learning.personal_expense_management.controller.profile.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.login.LoginActivity;
import com.learning.personal_expense_management.controller.profile.activity.CurrencyUnitActivity;
import com.learning.personal_expense_management.controller.profile.activity.LanguageActivity;
import com.learning.personal_expense_management.controller.profile.activity.NotifyActivity;
import com.learning.personal_expense_management.controller.profile.activity.PolicyActivity;
import com.learning.personal_expense_management.controller.profile.activity.SecurityActivity;
import com.learning.personal_expense_management.controller.profile.activity.UpdateProfileActivity;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.UserProfileListener;
import com.learning.personal_expense_management.databinding.FragmentProfileBinding;
import com.learning.personal_expense_management.utilities.Constants;
import com.learning.personal_expense_management.utilities.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private PreferenceManager preferenceManager;
    private ActivityResultLauncher<Intent> updateAccountLaunch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        init();
        setListeners();
        getFunctions();


        return view;
    }

    private void init() {

        binding.btnUpdate.setVisibility(View.GONE);
        preferenceManager = new PreferenceManager(getContext());

        updateAccountLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    getProfile();
                }
            }
        });
    }

    private void setListeners() {
        binding.switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        binding.lineCurrencyUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CurrencyUnitActivity.class);
                startActivity(intent);
            }
        });

        binding.lineLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LanguageActivity.class);
                startActivity(intent);
            }
        });

        binding.lineNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotifyActivity.class);
                startActivity(intent);
            }
        });

        binding.linePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PolicyActivity.class);
                startActivity(intent);
            }
        });

        binding.lineSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SecurityActivity.class);
                startActivity(intent);
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
                updateAccountLaunch.launch(intent);
            }
        });

        binding.lineLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferenceManager.getBoolean(Constants.KEY_GOOGLE_PROFILE)) {
                    signOutGoogle();
                }else {
                    signOutAnonymous();
                }
            }
        });
    }

    private void getFunctions() {
        getProfile();
    }

    private void getProfile() {
        if (preferenceManager.getBoolean(Constants.KEY_ANONYMOUS_PROFILE)) {
            binding.btnUpdate.setVisibility(View.GONE);
            binding.civUserImage.setImageResource(R.drawable.ic_example);
            binding.tvUserName.setText("Người dùng ẩn danh");
            binding.tvUserEmail.setVisibility(View.GONE);
        } else if (preferenceManager.getBoolean(Constants.KEY_GOOGLE_PROFILE)) {
//            Picasso.get().load(preferenceManager.getString(Constants.KEY_IMAGE_PROFILE)).into(binding.profileImage);
//            Log.d("image - profile", FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
            binding.btnUpdate.setVisibility(View.VISIBLE);
            Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(binding.civUserImage);
            binding.tvUserName.setText(preferenceManager.getString(Constants.KEY_NAME_PROFILE));
            binding.tvUserEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
    }

    private void signOutAnonymous() {
        FirebaseAuth.getInstance().signOut();
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {


                    // Xóa AuthStateListener sau khi đã chuyển activity để tránh lắng nghe thêm sự thay đổi
                    FirebaseAuth.getInstance().removeAuthStateListener(this);
                }
            }
        });

        preferenceManager.clear();
        // Người dùng đã đăng xuất, chuyển đến LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa các activity trước
        startActivity(intent);
    }

    private void signOutGoogle() {
        GoogleSignInOptions gso;
        GoogleSignInClient gsc = null;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);
        gsc.signOut();

        preferenceManager.clear();
        // Người dùng đã đăng xuất, chuyển đến LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa các activity trước
        startActivity(intent);
    }

}