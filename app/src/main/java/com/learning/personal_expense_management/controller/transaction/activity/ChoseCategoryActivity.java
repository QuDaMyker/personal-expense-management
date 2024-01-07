package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.CategoryAdapter;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.controller.transaction.adapter.ChoseCategoryAdapter;
import com.learning.personal_expense_management.controller.transaction.adapter.ObjectListener;
import com.learning.personal_expense_management.databinding.ActivityChoseCategoryBinding;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.services.CategoryListener;
import com.learning.personal_expense_management.services.FireStoreService;

import java.util.ArrayList;
import java.util.List;

public class ChoseCategoryActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private List<Category> listDisplay;
    private List<Category> listCats;
    private List<Category> listIncome;
    private List<Category> listOutCome;

    private RecyclerView rcvCats;

    private TabLayout tabInOut;
    private ChoseCategoryAdapter choseCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_category);

        init();
        setListeners();
        setFunction();
    }

    private void init() {

        listCats = new ArrayList<>();
        listDisplay = new ArrayList<>();
        listIncome = new ArrayList<>();
        listOutCome = new ArrayList<>();
        btnBack = findViewById(R.id.btn_Back);
        tabInOut = findViewById(R.id.InOut);
        rcvCats = findViewById(R.id.rcvCategories);
        choseCategoryAdapter = new ChoseCategoryAdapter(getApplicationContext(), listDisplay, new ObjectListener() {
            @Override
            public void onClick(Object o) {
                Category category = (Category) o;
                Intent intent = new Intent(ChoseCategoryActivity.this, RootActivity.class);
                intent.putExtra("ownerIdCategory", category.getOwnerId());
                intent.putExtra("idCategory", category.getId());
                intent.putExtra("nameCategory", category.getName());
                intent.putExtra("backgroundCategory", category.getBackGround());
                intent.putExtra("iconCategory", category.getIcon());
                intent.putExtra("colorIconCategory", category.getColorIcon());
                intent.putExtra("isInComeCategory", category.getIsIncome());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        rcvCats.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        rcvCats.setAdapter(choseCategoryAdapter);
    }

    private void setListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });

        tabInOut.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        listDisplay.clear();
                        listDisplay.addAll(listOutCome);
                        break;
                    case 1:
                        listDisplay.clear();
                        listDisplay.addAll(listIncome);
                        break;
                    default:
                        break;
                }

                 choseCategoryAdapter.setList(listDisplay);
                 choseCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setFunction() {
        getData();
    }

    void getData() {
        FireStoreService.getCategory(FirebaseAuth.getInstance().getUid(), new CategoryListener() {
            @Override
            public void onCategoryLoaded(List<Category> categories) {
                listCats = categories;

                listIncome.clear();
                listOutCome.clear();
                for (Category cat : listCats) {
                    if (cat.getIsIncome() == 0) {
                        listOutCome.add(cat);
                    } else {
                        if (cat.getIsIncome() == 1) {
                            listIncome.add(cat);
                        } else {
                            listOutCome.add(cat);
                            listIncome.add(cat);
                        }
                    }
                }

                listDisplay.clear();
                listDisplay.addAll(listOutCome);
                choseCategoryAdapter.setList(listDisplay);
                choseCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

}