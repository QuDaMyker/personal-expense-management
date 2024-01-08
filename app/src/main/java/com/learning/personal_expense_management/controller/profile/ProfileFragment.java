package com.learning.personal_expense_management.controller.profile;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.recreate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.controller.login.LoginActivity;
import com.learning.personal_expense_management.controller.wallet.WalletActivity;
import com.learning.personal_expense_management.databinding.FragmentProfileBinding;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.UserProfileListener;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private UserProfile userProfile;
    private FragmentProfileBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

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

        FireStoreService.getUserProfile(FirebaseAuth.getInstance().getUid(), new UserProfileListener() {
            @Override
            public void onExist(boolean isExist) {  }
            @Override
            public void onUserProfilesLoaded(List<UserProfile> userProfiles) {
                userProfile = new UserProfile();
                userProfile = userProfiles.get(0);

                String userName;
                if (userProfile.getName().isEmpty()) {
                    userName = "Your name";
                }
                else{
                    userName = userProfile.getName().toString();
                }
                binding.tvUserName.setText(userName);

                String userEmail;
                if (userProfile.getEmail().isEmpty()) {
                    userEmail = "youremail@gmail.com";
                }
                else{
                    userEmail = userProfile.getEmail().toString();
                }
                binding.tvUserEmail.setText(userEmail);
            }
            @Override
            public void onError(String errorMessage) { }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
                intent.putExtra("userProfile",(Serializable) userProfile);
                startActivity(intent);
            }
        });

        binding.lineLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

         return view;
    }

}