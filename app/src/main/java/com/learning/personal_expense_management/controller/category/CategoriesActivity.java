package com.learning.personal_expense_management.controller.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.CategoryAdapter;
import com.learning.personal_expense_management.controller.home.fragment.HomeFragment;
import com.learning.personal_expense_management.controller.login.LoginActivity;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.services.CategoryListener;
import com.learning.personal_expense_management.services.FireStoreService;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ImageButton btnBack;
    private List<Category> listDisplay;
    private List<Category> listCats;
    private List<Category> listIncome;
    private List<Category> listOutCome;

    private CategoryAdapter categoryAdapter;
    private String currentUser;
    private RecyclerView rcvCats;

    private TabLayout tabInOut;
    private Button btnNewCat;

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        btnBack = findViewById(R.id.btn_Back);

        tabInOut = findViewById(R.id.InOut);

        currentUser = mAuth.getInstance().getUid();

        rcvCats = findViewById(R.id.rcvCategories);

        btnNewCat = findViewById(R.id.btnCreateNewCat);

        if (rcvCats.getLayoutManager() == null) {
            rcvCats.setLayoutManager(new GridLayoutManager(this, 4));
        }

        categoryAdapter = new CategoryAdapter(this);

        listCats = new ArrayList<Category>();
        listDisplay = new ArrayList<Category>();
        listIncome = new ArrayList<Category>();
        listOutCome = new ArrayList<Category>();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rcvCats.setAdapter(categoryAdapter);

        getData();
        tabInOut.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    //chi tieu
                    case 0:
                        listDisplay.clear();
                        listDisplay.addAll(listOutCome);
                        break;
                    case 1:
                        //thu nhap
                        listDisplay.clear();
                        listDisplay.addAll(listIncome);
                        break;
                    default:
                        break;
                }

                categoryAdapter.setData(listDisplay);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, NewCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    void getData() {
        FireStoreService.getCategory(currentUser, new CategoryListener() {
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
                categoryAdapter.setData(listDisplay);
                categoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }


    private void createDefaultLisCategories(String userID) {
        listCats = new ArrayList<Category>();
        listCats.add(new Category(userID, "Thưởng", R.color.colorItem1, R.drawable.ic_money, R.color.colorIcon1, 1));
        listCats.add(new Category(userID, "Làm đẹp", R.color.colorItem2, R.drawable.ic_beauty_heart, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Máy bay", R.color.colorItem1, R.drawable.ic_airplane, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Sửa chữa", R.color.colorItem3, R.drawable.ic_repair, R.color.colorIcon3, 0));
        listCats.add(new Category(userID, "Du lịch", R.color.colorItem6, R.drawable.ic_case_travel, R.color.colorIcon4, 0));
        listCats.add(new Category(userID, "Tàu điện", R.color.colorItem3, R.drawable.ic_train_metro, R.color.colorIcon3, 0));
        listCats.add(new Category(userID, "Hóa đơn", R.color.colorItem5, R.drawable.ic_fee_tax, R.color.colorIcon5, 0));
        listCats.add(new Category(userID, "Thư", R.color.colorItem4, R.drawable.ic_envelope, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Mua sắm", R.color.colorItem1, R.drawable.icon_shopping, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Két sắt", R.color.colorItem5, R.drawable.ic_safe_saving, R.color.colorIcon5, 1));
        listCats.add(new Category(userID, "Lương", R.color.colorItem8, R.drawable.icon_wallet_earnings, R.color.green, 1));
        listCats.add(new Category(userID, "Thức ăn", R.color.colorItem7, R.drawable.ic_restaurant_fork, R.color.colorIcon7, 0));
        listCats.add(new Category(userID, "Thuốc", R.color.colorItem2, R.drawable.icon_drugs, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Nước uống", R.color.colorItem6, R.drawable.ic_coffee_bistro, R.color.colorIcon4, 0));
        listCats.add(new Category(userID, "Thời gian chờ", R.color.colorItem3, R.drawable.ic_pending_time_wait_transaction_clock, R.color.colorIcon3, 0));
        listCats.add(new Category(userID, "Nước", R.color.colorItem1, R.drawable.ic_water_tap, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Khoản vay", R.color.colorItem8, R.drawable.ic_interest, R.color.green, 1));
        listCats.add(new Category(userID, "Em bé", R.color.colorItem7, R.drawable.ic_kid_care_stroller, R.color.colorIcon7, 0));
        listCats.add(new Category(userID, "Bảo hiểm", R.color.colorItem5, R.drawable.ic_insurance_shield, R.color.colorIcon5, 0));
        listCats.add(new Category(userID, "Nhà", R.color.colorItem4, R.drawable.ic_home, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Sở thích", R.color.colorItem4, R.drawable.ic_kite_hobby, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Y tế", R.color.colorItem2, R.drawable.ic_hospital, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Thể thao", R.color.colorItem1, R.drawable.ic_sport_gym, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Quần áo", R.color.colorItem1, R.drawable.ic_tshirt, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Quà tặng", R.color.colorItem2, R.drawable.ic_gift_box, R.color.colorIcon2, 1));
        listCats.add(new Category(userID, "Trái cây", R.color.colorItem8, R.drawable.ic_apple, R.color.green, 0));
        listCats.add(new Category(userID, "Giải trí", R.color.colorItem6, R.drawable.ic_mask, R.color.colorIcon4, 0));
        listCats.add(new Category(userID, "Di chuyển", R.color.colorItem1, R.drawable.ic_car_drive, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Xăng dầu", R.color.colorItem4, R.drawable.ic_station_fuel, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Thiết bi", R.color.colorItem1, R.drawable.ic_computer, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Từ thiện", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Testtttttttttttttttttttttttttttttttttttttttttttttttttttt", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Testtttttttttttttttttttttttttttttttttttttttttttttttttttt", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Từ thiện", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));

    }

}