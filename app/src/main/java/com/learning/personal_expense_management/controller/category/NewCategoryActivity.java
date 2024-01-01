package com.learning.personal_expense_management.controller.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.R;

public class NewCategoryActivity extends AppCompatActivity {

    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseDatabase database;
    private DatabaseReference reference;
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
    private Button btnCreateNewCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

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

        btnCreateNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txTitle.getText()!= null && selectedColorBtn.getId()!=-1 && selectedIconBtn.getId()!=-1)
                {


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