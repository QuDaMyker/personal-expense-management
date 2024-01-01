package com.learning.personal_expense_management.controller.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.services.FireStoreService;

import java.lang.reflect.Field;
import java.text.ParseException;

public class NewCategoryActivity extends AppCompatActivity {

    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private  String userCurrent ;
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

        userCurrent = mAuth.getInstance().getUid();

        txTitle=findViewById(R.id.tvTitleNewCat);


        grColor=findViewById(R.id.grColor);
        grColor1=findViewById(R.id.grColor1);
        grColor2=findViewById(R.id.grColor2);
        preCheckedGrColorID =-1;
        preCheckedGrIconID =-1;


        grIcon=findViewById(R.id.grIcon);
        grIcon1=findViewById(R.id.grIcon1);
        grIcon2=findViewById(R.id.grIcon2);
        grIcon3=findViewById(R.id.grIcon3);
        grIcon4=findViewById(R.id.grIcon4);
        grIcon5=findViewById(R.id.grIcon5);
        grIcon6= findViewById(R.id.grIcon6);
        grIcon7= findViewById(R.id.grIcon7);

        btnColo1= findViewById(R.id.clItem1);
        btnColo2= findViewById(R.id.clItem2);
        btnColo3= findViewById(R.id.clItem3);
        btnColo4 = findViewById(R.id.clItem4);
        btnColo5= findViewById(R.id.clItem5);
        btnColo6= findViewById(R.id.clItem6);
        btnColo7= findViewById(R.id.clItem7);
        btnColo8= findViewById(R.id.clItem8);

        grIsIncome = findViewById(R.id.grIsIncome);

        btnCreateNewCat= findViewById(R.id.btnCreateNewCat);

        setupRadioButtonGRColor(grColor1);
        setupRadioButtonGRColor(grColor2);

        setupRadioButtonGRIcon(grIcon1);
        setupRadioButtonGRIcon(grIcon2);
        setupRadioButtonGRIcon(grIcon3);
        setupRadioButtonGRIcon(grIcon4);
        setupRadioButtonGRIcon(grIcon5);
        setupRadioButtonGRIcon(grIcon6);
        setupRadioButtonGRIcon(grIcon7);

        grIsIncome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnIsIncome = findViewById(checkedId);
            }
        });
        btnCreateNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txTitle.getText()!= null && selectedColorBtn.getId()!=-1 && selectedIconBtn.getId()!=-1)
                {
                    Category newCat = new Category();

                    try {
                        switch (selectedColorBtn.getId())
                        {
                            case R.id.clItem1:
                                newCat.setBackGround(R.color.colorItem1);
                                newCat.setColorIcon(R.color.colorIcon1);
                                break;
                            case R.id.clItem2:
                                newCat.setBackGround(R.color.colorItem2);
                                newCat.setColorIcon(R.color.colorIcon2);
                                break;
                            case R.id.clItem3:
                                newCat.setBackGround(R.color.colorItem3);
                                newCat.setColorIcon(R.color.colorIcon3);
                                break;
                            case R.id.clItem4:
                                newCat.setBackGround(R.color.colorItem4);
                                newCat.setColorIcon(R.color.colorIcon4);
                                break;
                            case R.id.clItem5:
                                newCat.setBackGround(R.color.colorItem5);
                                newCat.setColorIcon(R.color.colorIcon5);
                                break;
                            case R.id.clItem6:
                                newCat.setBackGround(R.color.colorItem6);
                                newCat.setColorIcon(R.color.colorIcon6);
                                break;
                            case R.id.clItem7:
                                newCat.setBackGround(R.color.colorItem7);
                                newCat.setColorIcon(R.color.colorIcon7);
                                break;
                            case R.id.clItem8:
                                newCat.setBackGround(R.color.colorItem8);
                                newCat.setColorIcon(R.color.green);
                                break;
                        }

                        int icon  = 0;
                        switch (selectedIconBtn.getId())
                        {
                            case  R.id.icMoney:
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
                            case  R.id.icCaseTravel:
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
                            case  R.id.icGear:
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
                            case  R.id.icClock:
                                icon = R.drawable.ic_pending_time_wait_transaction_clock;
                                break;
                            case R.id.icWater:
                                icon = R.drawable.ic_water_tap;
                                break;
                            case R.id.icCash:
                                icon = R.drawable.ic_interest;
                                break;
                            case R.id.icTag:
                                icon = R.drawable.ic_label_price;
                                break;
                            case R.id.icKid:
                                icon = R.drawable.ic_kid_care_stroller;
                                break;
                            case  R.id.icSheld:
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
                            case  R.id.icGift:
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
                            case  R.id.icHeartCalender:
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

                        switch (btnIsIncome.getId())
                        {
                            case 0:
                                newCat.setIncome(0);
                                break;
                            case 1 :
                                newCat.setIncome(1);
                                break;
                            case 2:
                                newCat.setIncome(2);
                                break;
                            default:
                                break;
                        }

                        newCat.setOwnerId(userCurrent);
                        newCat.setName(txTitle.getText().toString());
                        newCat.setIcon(icon);

                        //đổi thành json ròi gửi lên firebase
                        String result = FireStoreService.addCategory(newCat);

                        if (result == "success")
                        {
                            Intent intent = new Intent(NewCategoryActivity.this, CategoriesActivity.class);
                            startActivity(intent);
                        }

                    }
                    catch (Exception  e){
                        Log.d("er", e.getMessage());
                }
                }
            }
        });

    }
    private void setupRadioButtonGRColor(RadioGroup grChecked) {
        grChecked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedColorBtn= findViewById(checkedId);

                if (preCheckedGrColorID != -1 && group.getId() != preCheckedGrColorID) {
                    // Uncheck ở `preCheckedGrID`
                    RadioGroup preCheckedGr= findViewById(preCheckedGrColorID);
                    preCheckedGr.clearCheck();
                }

                preCheckedGrColorID = group.getId();

                // Xử lý
                if (selectedColorBtn != null) {
                    Toast.makeText(NewCategoryActivity.this, "Selected: " + selectedColorBtn.getBackgroundTintList(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void setupRadioButtonGRIcon(RadioGroup grChecked) {
        grChecked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedIconBtn= findViewById(checkedId);

                //k chọn lại gr hoặc k phải lần đầu
                if (preCheckedGrIconID != -1 && group.getId() != preCheckedGrIconID) {
                    // Uncheck ở `preCheckedGrID`
                    RadioGroup preCheckedGr = findViewById(preCheckedGrIconID);
                    preCheckedGr.clearCheck();
                }

                preCheckedGrIconID = group.getId();

                // Xử lý
                if (selectedIconBtn != null) {
                    Toast.makeText(NewCategoryActivity.this, "Selected: " + selectedIconBtn.getDrawableState(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}