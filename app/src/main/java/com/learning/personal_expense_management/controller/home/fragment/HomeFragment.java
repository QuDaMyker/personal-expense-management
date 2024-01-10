package com.learning.personal_expense_management.controller.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.learning.personal_expense_management.controller.category.CategoriesActivity;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.AccountActivity;
import com.learning.personal_expense_management.controller.loan.LoanActivity;
import com.learning.personal_expense_management.controller.home.adapter.home.HomeRecentlyActivityAdapter;
import com.learning.personal_expense_management.controller.home.adapter.home.HomeTargetAdapter;
import com.learning.personal_expense_management.controller.home.adapter.home.ObjectListener;
import com.learning.personal_expense_management.controller.wallet.WalletActivity;
import com.learning.personal_expense_management.databinding.FragmentHomeBinding;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.FirestoreCallback;
import com.learning.personal_expense_management.services.TransactionListener;
import com.learning.personal_expense_management.utilities.Constants;
import com.learning.personal_expense_management.utilities.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private List<Wallet> listTarget;
    private List<Transaction> listRecently;
    private HomeTargetAdapter homeTargetAdapter;
    private HomeRecentlyActivityAdapter homeRecentlyActivityAdapter;
    private PreferenceManager preferenceManager;

    private CardView cardCategory;
    private boolean isHidden = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        init();
        setListeners();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getListRecentlyActivity();
    }

    private void init() {
        preferenceManager = new PreferenceManager(getContext());
        listTarget = new ArrayList<>();
        listRecently = new ArrayList<>();
        getListRecentlyActivity();

        homeTargetAdapter = new HomeTargetAdapter(getContext(), listTarget, new ObjectListener() {
            @Override
            public void onClick(Object o) {
                Wallet wallet = (Wallet) o;
            }
        });

        if (preferenceManager.getBoolean(Constants.KEY_ANONYMOUS_PROFILE)) {
            binding.profileImage.setImageResource(R.drawable.ic_example);
        } else if(preferenceManager.getBoolean(Constants.KEY_GOOGLE_PROFILE)){
            Picasso.get().load(preferenceManager.getString(Constants.KEY_IMAGE_PROFILE)).into(binding.profileImage);
//            Log.d("image - profile", FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
        }

        NumberFormat formatter = new DecimalFormat("#,###");
        FireStoreService.getSumAmountAllAccountByUserId(FirebaseAuth.getInstance().getUid(), new FirestoreCallback() {
            @Override
            public void onCallback(String result) {
                binding.tvSoDuHienTai.setText(String.format("%sđ", formatter.format(Integer.parseInt(result))));
            }
        });

    }

    private void setListeners() {
        binding.hiddenCurrentBalance.setOnClickListener(v-> {
            isHidden = !isHidden;
            if(isHidden) {
                binding.hiddenCurrentBalance.setImageResource(R.drawable.ic_visibility_off);
                binding.tvSoDuHienTai.setText("******");
            }else {
                binding.hiddenCurrentBalance.setImageResource(R.drawable.ic_visible_on);
                NumberFormat formatter = new DecimalFormat("#,###");
                FireStoreService.getSumAmountAllAccountByUserId(FirebaseAuth.getInstance().getUid(), new FirestoreCallback() {
                    @Override
                    public void onCallback(String result) {
                        //binding.tvSoDuHienTai.setText(String.format("%sđ", formatter.format(Integer.parseInt(result))));
                    }
                });
            }
        });
        binding.btnWalletaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });

        binding.cardVDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoriesActivity.class);
                startActivity(intent);
            }
        });
        binding.accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });

        binding.cardVKhoanVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoanActivity.class));
            }
        });
    }

    private void getListTarget() {

    }

    private void getListRecentlyActivity() {
        FireStoreService.getAllTransaction(FirebaseAuth.getInstance().getUid(), new TransactionListener() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                listRecently = transactions;

                if (listRecently.isEmpty()) {
                    binding.tvRevHoatdonggandayNull.setVisibility(View.VISIBLE);
                    binding.revHoatdongganday.setVisibility(View.GONE);
                } else {
                    binding.tvRevHoatdonggandayNull.setVisibility(View.GONE);
                    binding.revHoatdongganday.setVisibility(View.VISIBLE);
                }

                homeRecentlyActivityAdapter = new HomeRecentlyActivityAdapter(getContext(), listRecently,
                        new ObjectListener() {
                            @Override
                            public void onClick(Object o) {
                                Transaction transaction = (Transaction) o;
                                Toast.makeText(getContext(), transaction.getTransactionTime(), Toast.LENGTH_SHORT).show();
                            }
                        });

                binding.revHoatdongganday.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.revHoatdongganday.setAdapter(homeRecentlyActivityAdapter);
                homeRecentlyActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }

        });
    }

}