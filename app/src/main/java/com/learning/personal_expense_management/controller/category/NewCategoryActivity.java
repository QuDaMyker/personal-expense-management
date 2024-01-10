package com.learning.personal_expense_management.controller.category;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.services.CategoryListener;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.FirestoreCallback;
import com.learning.personal_expense_management.utilities.Constants;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NewCategoryActivity extends AppCompatActivity {

    private boolean isEdit = false;
    private Category updateCat;
    private String userCurrent;
    private boolean isDuplicate = false;
    private ImageButton btnBack;
    private TextInputEditText txTitle;
    private RadioGroup grColor;
    private RadioGroup grColor1;

    private RadioGroup grColor2;
    private RadioButton selectedColorBtn;
    private int preCheckedGrColorID;

    private RadioGroup grIcon;
    private RadioGroup grIcon1;

    private RadioGroup grIcon2;
    private RadioGroup grIcon3;
    private RadioGroup grIcon4;
    private RadioGroup grIcon5;
    private RadioGroup grIcon6;
    private RadioGroup grIcon7;

    private RadioButton selectedIconBtn;
    private int preCheckedGrIconID;


    private RadioButton btnColo1;
    private RadioButton btnColo2;
    private RadioButton btnColo3;
    private RadioButton btnColo4;
    private RadioButton btnColo5;
    private RadioButton btnColo6;
    private RadioButton btnColo7;
    private RadioButton btnColo8;

    private RadioGroup grIsIncome;
    private RadioButton btnIsIncome;
    private Button btnCreateNewCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        userCurrent = FirebaseAuth.getInstance().getUid();
        
        btnBack = findViewById(R.id.btn_Back);
        txTitle = findViewById(R.id.tvTitleNewCat);


        grColor = findViewById(R.id.grColor);
        grColor1 = findViewById(R.id.grColor1);
        grColor2 = findViewById(R.id.grColor2);
        preCheckedGrColorID = -1;
        preCheckedGrIconID = -1;

        grIcon = findViewById(R.id.grIcon);
        grIcon1 = findViewById(R.id.grIcon1);
        grIcon2 = findViewById(R.id.grIcon2);
        grIcon3 = findViewById(R.id.grIcon3);
        grIcon4 = findViewById(R.id.grIcon4);
        grIcon5 = findViewById(R.id.grIcon5);
        grIcon6 = findViewById(R.id.grIcon6);
        grIcon7 = findViewById(R.id.grIcon7);

        btnColo1 = findViewById(R.id.clItem1);
        btnColo2 = findViewById(R.id.clItem2);
        btnColo3 = findViewById(R.id.clItem3);
        btnColo4 = findViewById(R.id.clItem4);
        btnColo5 = findViewById(R.id.clItem5);
        btnColo6 = findViewById(R.id.clItem6);
        btnColo7 = findViewById(R.id.clItem7);
        btnColo8 = findViewById(R.id.clItem8);

        grIsIncome = findViewById(R.id.grIsIncome);
        btnCreateNewCat = findViewById(R.id.btnCreateNewCat);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setupRadioButtonGRIcon(grIcon1);
        setupRadioButtonGRIcon(grIcon2);
        setupRadioButtonGRIcon(grIcon3);
        setupRadioButtonGRIcon(grIcon4);
        setupRadioButtonGRIcon(grIcon5);
        setupRadioButtonGRIcon(grIcon6);
        setupRadioButtonGRIcon(grIcon7);
        
        selectedColorBtn = findViewById(R.id.clItem1);

        Intent intent = getIntent();
        if (intent != null) {
            String userCat = intent.getStringExtra("userCat");
            String catId = intent.getStringExtra("catId");
            int backgroundColor = intent.getIntExtra("backgroundColor", -1); // Giá trị mặc định là 0 nếu không tìm thấy
            int colorIcon = intent.getIntExtra("colorIcon", -1);
            int icon = intent.getIntExtra("icon", -1);
            String catName = intent.getStringExtra("catName");
            int catIsIncome = intent.getIntExtra("catIsInCome", -1);

            updateCat = new Category();
            updateCat.setId(catId);
            updateCat.setOwnerId(userCat);
            updateCat.setBackGround(backgroundColor);
            updateCat.setColorIcon(colorIcon);
            updateCat.setIcon(icon);
            updateCat.setIncome(catIsIncome);

            if (catName == null) {
                isEdit = false;
            }

            if (catName != null) {
                txTitle.setText(catName);
                isEdit = true;
            }
            if (catIsIncome != -1) {
                switch (catIsIncome) {
                    case 0:
                        grIsIncome.check(R.id.radioIsOutCome);
                        btnIsIncome = findViewById(R.id.radioIsOutCome);
                        break;
                    case 1:
                        grIsIncome.check(R.id.radioIsIcome);
                        btnIsIncome = findViewById(R.id.radioIsIcome);
                        break;
                    case 2:
                        grIsIncome.check(R.id.radioBoth);
                        btnIsIncome = findViewById(R.id.radioBoth);
                        break;
                    default:
                        break;

                }
            }

            if (backgroundColor != -1) {
                switch (backgroundColor) {
                    case R.color.colorItem1:
                        selectedColorBtn = findViewById(R.id.clItem1);
                        grColor1.check(selectedColorBtn.getId());

                        break;
                    case R.color.colorItem2:
                        selectedColorBtn = findViewById(R.id.clItem2);
                        grColor1.check(selectedColorBtn.getId());


                        break;
                    case R.color.colorItem3:
                        selectedColorBtn = findViewById(R.id.clItem3);
                        grColor1.check(selectedColorBtn.getId());


                        break;
                    case R.color.colorItem4:
                        selectedColorBtn = findViewById(R.id.clItem4);
                        grColor1.check(selectedColorBtn.getId());


                        break;
                    case R.color.colorItem5:
                        selectedColorBtn = findViewById(R.id.clItem5);
                        grColor2.check(selectedColorBtn.getId());


                        break;
                    case R.color.colorItem6:
                        selectedColorBtn = findViewById(R.id.clItem6);
                        grColor2.check(selectedColorBtn.getId());


                        break;
                    case R.color.colorItem7:
                        selectedColorBtn = findViewById(R.id.clItem7);
                        grColor2.check(selectedColorBtn.getId());


                        break;
                    case R.color.colorItem8:
                        selectedColorBtn = findViewById(R.id.clItem8);
                        grColor2.check(selectedColorBtn.getId());

                        break;
                    default:
                        break;
                }
            }

            switch (icon) {
                case R.drawable.ic_money:
                    selectedIconBtn = findViewById(R.id.icMoney);
                    grIcon1.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_beauty_heart:
                    selectedIconBtn = findViewById(R.id.icBeauty);
                    grIcon1.check(selectedIconBtn.getId());

                    break;
                case R.drawable.ic_airplane:
                    selectedIconBtn = findViewById(R.id.icPlane);
                    grIcon1.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_atm:
                    selectedIconBtn = findViewById(R.id.icAtm);
                    grIcon1.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_repair:
                    selectedIconBtn = findViewById(R.id.icRepair);
                    grIcon1.check(selectedIconBtn.getId());
                    break;

                case R.drawable.ic_case_travel:
                    grIcon2.check(selectedIconBtn.getId());
                    selectedIconBtn = findViewById(R.id.icCaseTravel);
                    break;
                case R.drawable.ic_train_metro:
                    selectedIconBtn = findViewById(R.id.icMetro);
                    grIcon2.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_fee_tax:
                    selectedIconBtn = findViewById(R.id.icFeeTax);
                    grIcon2.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_envelope:
                    selectedIconBtn = findViewById(R.id.icLetter);
                    grIcon2.check(selectedIconBtn.getId());
                    break;
                case R.drawable.icon_shopping:
                    selectedIconBtn = findViewById(R.id.icShopping);
                    grIcon2.check(selectedIconBtn.getId());
                    break;

                case R.drawable.ic_gear:
                    selectedIconBtn = findViewById(R.id.icGear);
                    grIcon3.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_safe_saving:
                    selectedIconBtn = findViewById(R.id.icSafeSaving);
                    grIcon3.check(selectedIconBtn.getId());
                    break;
                case R.drawable.icon_wallet_earnings:
                    selectedIconBtn = findViewById(R.id.icWallet);
                    grIcon3.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_restaurant_fork:
                    selectedIconBtn = findViewById(R.id.icFork);
                    grIcon3.check(selectedIconBtn.getId());
                    break;
                case R.drawable.icon_drugs:
                    selectedIconBtn = findViewById(R.id.icMedicine);
                    grIcon3.check(selectedIconBtn.getId());
                    break;

                case R.drawable.ic_pending_time_wait_transaction_clock:
                    selectedIconBtn = findViewById(R.id.icClock);
                    grIcon4.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_water_tap:
                    selectedIconBtn = findViewById(R.id.icWater);
                    grIcon4.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_interest:
                    selectedIconBtn = findViewById(R.id.icCash);
                    grIcon4.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_snow:
                    selectedIconBtn = findViewById(R.id.icSnow);
                    grIcon4.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_label_price:
                    selectedIconBtn = findViewById(R.id.icTag);
                    grIcon4.check(selectedIconBtn.getId());
                    break;

                case R.drawable.ic_kid_care_stroller:
                    selectedIconBtn = findViewById(R.id.icKid);
                    grIcon5.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_insurance_shield:
                    selectedIconBtn = findViewById(R.id.icSheld);
                    grIcon5.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_home:
                    selectedIconBtn = findViewById(R.id.icHome);
                    grIcon5.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_kite_hobby:
                    selectedIconBtn = findViewById(R.id.icKite);
                    grIcon5.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_hospital:
                    selectedIconBtn = findViewById(R.id.icHospotal);
                    grIcon5.check(selectedIconBtn.getId());
                    break;

                case R.drawable.ic_sport_gym:
                    selectedIconBtn = findViewById(R.id.icGym);
                    grIcon6.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_gift_box:
                    selectedIconBtn = findViewById(R.id.icGift);
                    grIcon6.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_station_fuel:
                    selectedIconBtn = findViewById(R.id.icFuel);
                    grIcon6.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_apple:
                    selectedIconBtn = findViewById(R.id.icApple);
                    grIcon6.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_mask:
                    selectedIconBtn = findViewById(R.id.icMask);
                    grIcon6.check(selectedIconBtn.getId());
                    break;

                case R.drawable.ic_computer:
                    selectedIconBtn = findViewById(R.id.icComputer);
                    grIcon7.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_social_heart_donation_care_calendar:
                    selectedIconBtn = findViewById(R.id.icHeartCalender);
                    grIcon7.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_tshirt:
                    selectedIconBtn = findViewById(R.id.icTshirt);
                    grIcon7.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_car_drive:
                    selectedIconBtn = findViewById(R.id.icCar);
                    grIcon7.check(selectedIconBtn.getId());
                    break;
                case R.drawable.ic_coffee_bistro:
                    selectedIconBtn = findViewById(R.id.icDrink);
                    grIcon7.check(selectedIconBtn.getId());
                    break;
                default:
                    break;
            }
        }
        
        grIsIncome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnIsIncome = findViewById(checkedId);
            }
        });
        btnCreateNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = txTitle.getText().toString();
                if (!test.isEmpty()
                        && selectedColorBtn != null
                        && selectedIconBtn != null
                        && btnIsIncome != null) {
                    if (!isEdit) {
                        Category newCat = new Category();
                        try {
                            
                            switch (btnIsIncome.getId()) {
                                case R.id.radioIsIcome:
                                    newCat.setIncome(1);
                                    break;
                                case R.id.radioIsOutCome:
                                    newCat.setIncome(0);
                                    break;
                                case R.id.radioBoth:
                                    newCat.setIncome(2);
                                    break;
                                default:
                                    break;
                            }
                            newCat.setOwnerId(userCurrent);
                            newCat.setName(txTitle.getText().toString());
                            setUpColorForCat(newCat, selectedColorBtn, selectedIconBtn);
                            Log.d("newCat", newCat.toString());

                            //check trùng ở đêy
//                            try {
//                                FirebaseFirestore.getInstance().collection(Constants.KEY_CATEGORY).whereEqualTo("ownerId", newCat.getOwnerId())
//                                        .whereEqualTo("categoryName", newCat.getName())
//                                        .whereEqualTo("background", newCat.getBackGround())
//                                        .whereEqualTo("colorIcon", newCat.getColorIcon())
//                                        .whereEqualTo("icon", newCat.getIcon())
//                                        .whereEqualTo("isIncome", newCat.getIsIncome())
//                                        .get()
//                                        .addOnCompleteListener(task -> {
//                                            if (task.isSuccessful()) {
//                                                isDuplicate = true;
//                                            }
//                                        })
//                                         .addOnFailureListener(e -> {
//                                            Log.e("TAG", "Error in onComplete: " + e.getMessage());
//                                        });
//                            } catch (Exception ex)
//                            {
//                                Log.d("TAG", "onClick: lỗi");
//                            }

                            if(!isDuplicate)
                            {
                                //đổi thành json ròi gửi lên firebase
                                FireStoreService.addCategory(newCat, new FirestoreCallback() {
                                    @Override
                                    public void onCallback(String result) {
                                        Log.d("result-push", result);
                                        if ("success".equals(result)) {
                                            Intent intent = new Intent(NewCategoryActivity.this, CategoriesActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(NewCategoryActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(NewCategoryActivity.this, "Danh mục đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("er", e.getMessage());
                        }
                    } else {
                        try {
                            updateCat.setName(txTitle.getText().toString());
                            setUpColorForCat(updateCat, selectedColorBtn, selectedIconBtn);

                            switch (btnIsIncome.getId()) {
                                case R.id.radioIsIcome:
                                    updateCat.setIncome(1);
                                    break;
                                case R.id.radioIsOutCome:
                                    updateCat.setIncome(0);
                                    break;
                                case R.id.radioBoth:
                                    updateCat.setIncome(2);
                                    break;
                                default:
                                    break;
                            }


                            if (!isDuplicate)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewCategoryActivity.this);
                                builder.setTitle("Xác nhận cập nhật");
                                builder.setMessage("Việc cập nhật sẽ ảnh hưởng lịch sử giao dịch.\nBạn có chắc muốn cập nhật danh mục không?");

                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Người dùng chọn Có, tiến hành cập nhật danh mục
                                        if (!isDuplicate) {
                                            FireStoreService.updateCategory(updateCat, new FirestoreCallback() {
                                                @Override
                                                public void onCallback(String result) {
                                                    Log.d("isUpdate", "true");
                                                    if ("success".equals(result)) {
                                                        Intent intent = new Intent(NewCategoryActivity.this, CategoriesActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(NewCategoryActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Người dùng chọn Không, không thực hiện cập nhật
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

//                                FireStoreService.updateCategory(updateCat, new FirestoreCallback() {
//                                    @Override
//                                    public void onCallback(String result) {
//                                        Log.d("isUpdate", "true");
//                                        if ("success".equals(result)) {
//                                            Intent intent = new Intent(NewCategoryActivity.this, CategoriesActivity.class);
//                                            startActivity(intent);
//                                        } else {
//                                            Toast.makeText(NewCategoryActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
                            }
                            else
                            {
                                Toast.makeText(NewCategoryActivity.this, "Danh mục đã tồn tại", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("er", e.getMessage());
                        }
                    }
                } else {
                    Toast.makeText(NewCategoryActivity.this, "Bạn hãy điền đủ tên, màu nền, icon và loại danh thu nhé", Toast.LENGTH_SHORT).show();
                }
            }
        });
        grColor1.setOnCheckedChangeListener(listener1);
        grColor2.setOnCheckedChangeListener(listener2);
    }


    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                grColor2.setOnCheckedChangeListener(null);
                grColor2.clearCheck();
                grColor2.setOnCheckedChangeListener(listener2);
                selectedColorBtn = findViewById(checkedId);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                grColor1.setOnCheckedChangeListener(null);
                grColor1.clearCheck();
                grColor1.setOnCheckedChangeListener(listener1);
                selectedColorBtn = findViewById(checkedId);
            }
        }
    };
    
    private int needClearId;
    private void setupRadioButtonGRIcon(RadioGroup grChecked) {
        grChecked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (group.getId() != needClearId)
                {
                    Log.d("test", "checkedID" + checkedId + " gr " + group.getId());

                    //chọn gr khác => preCheckedGrId đã có
                    if (preCheckedGrIconID != -1 && group.getId() != preCheckedGrIconID) {
                        // Uncheck ở `preCheckedGrID`
                        needClearId = preCheckedGrIconID; //có

                        Log.d("test", "idNeedClear: " + needClearId);

                        preCheckedGrIconID = group.getId(); // thay bằng gr mới
                        Log.d("test", "idPreGR: " + preCheckedGrIconID);

                        if (checkedId != -1) {

                            Log.d("test", "giờ clear: " + needClearId);
                            RadioGroup needClear = findViewById(needClearId);
                            needClear.clearCheck();

                            selectedIconBtn = findViewById(checkedId);
                            RadioGroup preCheckedGr = findViewById(preCheckedGrIconID);
                            preCheckedGr.check(selectedIconBtn.getId());
                            Log.d("test", "đã set chọn:" + selectedIconBtn.getId() + "gr" + preCheckedGrIconID);

                            //fromClearCheck = true;

                        }
                    }
                    else {
                        //chọn lần đầu
                        if (preCheckedGrIconID == -1)
                        {
                            preCheckedGrIconID = group.getId();
                            selectedIconBtn = findViewById(checkedId);
                        }
                    }
                }
                else
                {
                    return;
                }
                
            }
        });
    }
    
    private void setUpColorForCat (Category cat, RadioButton selectedColorBtn, RadioButton selectedIconBtn )
    {
        switch (selectedColorBtn.getId()) {
            case R.id.clItem1:
                cat.setBackGround(R.color.colorItem1);
                cat.setColorIcon(R.color.colorIcon1);
                break;
            case R.id.clItem2:
                cat.setBackGround(R.color.colorItem2);
                cat.setColorIcon(R.color.colorIcon2);
                break;
            case R.id.clItem3:
                cat.setBackGround(R.color.colorItem3);
                cat.setColorIcon(R.color.colorIcon3);
                break;
            case R.id.clItem4:
                cat.setBackGround(R.color.colorItem4);
                cat.setColorIcon(R.color.colorIcon4);
                break;
            case R.id.clItem5:
                cat.setBackGround(R.color.colorItem5);
                cat.setColorIcon(R.color.colorIcon5);
                break;
            case R.id.clItem6:
                cat.setBackGround(R.color.colorItem6);
                cat.setColorIcon(R.color.colorIcon6);
                break;
            case R.id.clItem7:
                cat.setBackGround(R.color.colorItem7);
                cat.setColorIcon(R.color.colorIcon7);
                break;
            case R.id.clItem8:
                cat.setBackGround(R.color.colorItem8);
                cat.setColorIcon(R.color.green);
                break;
        }

        int icon = 0;
        switch (selectedIconBtn.getId()) {
            case R.id.icMoney:
                icon = R.drawable.ic_money;
                break;
            case R.id.icBeauty:
                icon = R.drawable.ic_beauty_heart;
                break;
            case R.id.icPlane:
                icon = R.drawable.ic_airplane;
                break;
            case R.id.icAtm:
                icon = R.drawable.ic_atm;
                break;
            case R.id.icRepair:
                icon = R.drawable.ic_repair;
                break;
            case R.id.icCaseTravel:
                icon = R.drawable.ic_case_travel;
                break;
            case R.id.icMetro:
                icon = R.drawable.ic_train_metro;
                break;
            case R.id.icFeeTax:
                icon = R.drawable.ic_fee_tax;
                break;
            case R.id.icLetter:
                icon = R.drawable.ic_envelope;
                break;
            case R.id.icShopping:
                icon = R.drawable.icon_shopping;
                break;
            case R.id.icGear:
                icon = R.drawable.ic_gear;
                break;
            case R.id.icSafeSaving:
                icon = R.drawable.ic_safe_saving;
                break;
            case R.id.icWallet:
                icon = R.drawable.icon_wallet_earnings;
                break;
            case R.id.icFork:
                icon = R.drawable.ic_restaurant_fork;
                break;
            case R.id.icMedicine:
                icon = R.drawable.icon_drugs;
                break;
            case R.id.icClock:
                icon = R.drawable.ic_pending_time_wait_transaction_clock;
                break;
            case R.id.icWater:
                icon = R.drawable.ic_water_tap;
                break;
            case R.id.icCash:
                icon = R.drawable.ic_interest;
                break;
            case R.id.icSnow:
                icon = R.drawable.ic_snow;
                break;
            case R.id.icTag:
                icon = R.drawable.ic_label_price;
                break;
            case R.id.icKid:
                icon = R.drawable.ic_kid_care_stroller;
                break;
            case R.id.icSheld:
                icon = R.drawable.ic_insurance_shield;
                break;
            case R.id.icHome:
                icon = R.drawable.ic_home;
                break;
            case R.id.icKite:
                icon = R.drawable.ic_kite_hobby;
                break;
            case R.id.icHospotal:
                icon = R.drawable.ic_hospital;
                break;
            case R.id.icGym:
                icon = R.drawable.ic_sport_gym;
                break;
            case R.id.icGift:
                icon = R.drawable.ic_gift_box;
                break;
            case R.id.icFuel:
                icon = R.drawable.ic_station_fuel;
                break;
            case R.id.icApple:
                icon = R.drawable.ic_apple;
                break;
            case R.id.icMask:
                icon = R.drawable.ic_mask;
                break;
            case R.id.icComputer:
                icon = R.drawable.ic_computer;
                break;
            case R.id.icHeartCalender:
                icon = R.drawable.ic_social_heart_donation_care_calendar;
                break;
            case R.id.icTshirt:
                icon = R.drawable.ic_tshirt;
                break;
            case R.id.icCar:
                icon = R.drawable.ic_car_drive;
                break;
            case R.id.icDrink:
                icon = R.drawable.ic_coffee_bistro;
                break;
            default:
                break;
        }
        
        cat.setIcon(icon);
    }

    public CompletableFuture<Boolean> categoryIsDuplicate(Category cat)
    {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        try
        {
            FireStoreService.checkCategoryIsDuplicate(cat, new CategoryListener() {
                @Override
                public void onCategoryLoaded(List<Category> categories) {
                    if(!categories.isEmpty())
                    {
                        isDuplicate = true;
                        future.complete(isDuplicate);
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    future.completeExceptionally(new RuntimeException(errorMessage));
                }
            });
        }
        catch (Exception ex)
        {
            Log.d("er", "categoryIsDuplicate: "+ ex.getMessage().toString());
            future.completeExceptionally(ex);
        }
        return future;
    }



}