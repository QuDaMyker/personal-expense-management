package com.learning.personal_expense_management.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.learning.personal_expense_management.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText txTitle;
    private RadioGroup grColor;
    private RadioGroup grColor1;

    private RadioGroup grColor2;
    private RadioButton selectedColorBtn;

    private RadioGroup grIcon;
    private RadioGroup grIcon1;

    private RadioGroup grIcon2;
    private RadioGroup grIcon3;
    private RadioGroup grIcon4;
    private RadioGroup grIcon5;
    private RadioGroup grIcon6;
    private RadioGroup grIcon7;

    private RadioButton selectedIconBtn;

    private RadioButton btnColo1;
    private RadioButton btnColo2;
    private RadioButton btnColo3;
    private RadioButton btnColo4;
    private RadioButton btnColo5;
    private RadioButton btnColo6;
    private RadioButton btnColo7;
    private RadioButton btnColo8;

//    private RadioGroup grIcon;
//    private RadioButton btnIcon1;
//    private RadioButton btnIcon2;
//    private RadioButton btnIcon3;
//    private RadioButton btnIcon4;
//    private RadioButton btnIcon5;
//    private RadioButton btnIcon6;
//    private RadioButton btnIcon7;
//    private RadioButton btnIcon8;
//    private RadioButton btnIcon9;
//    private RadioButton btnIcon10;
//    private RadioButton btnIcon11;
//    private RadioButton btnIcon12;
//    private RadioButton btnIcon13;
//    private RadioButton btnIcon14;
//    private RadioButton btnIcon15;
//    private RadioButton btnIcon16;
//    private RadioButton btnIcon17;
//    private RadioButton btnIcon18;
//    private RadioButton btnIcon19;
//    private RadioButton btnIcon20;
//    private RadioButton btnIcon21;

    public NewCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCategoryFragment newInstance(String param1, String param2) {
        NewCategoryFragment fragment = new NewCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_new_category, container, false);

        grColor = rootView.findViewById(R.id.grColor);
        grColor1 = rootView.findViewById(R.id.grColor1);
        grColor2 = rootView.findViewById(R.id.grColor2);

        grIcon = rootView.findViewById(R.id.grIcon);
        grIcon1 = rootView.findViewById(R.id.grIcon1);
        grIcon2 = rootView.findViewById(R.id.grIcon2);
        grIcon3 = rootView.findViewById(R.id.grIcon3);
        grIcon4 = rootView.findViewById(R.id.grIcon4);
        grIcon5 = rootView.findViewById(R.id.grIcon5);
        grIcon6 = rootView.findViewById(R.id.grIcon6);
        grIcon7 = rootView.findViewById(R.id.grIcon7);

        btnColo1 = rootView.findViewById(R.id.clItem1);
        btnColo2 = rootView.findViewById(R.id.clItem2);
        btnColo3 = rootView.findViewById(R.id.clItem3);
        btnColo4 = rootView.findViewById(R.id.clItem4);
        btnColo5 = rootView.findViewById(R.id.clItem5);
        btnColo6 = rootView.findViewById(R.id.clItem6);
        btnColo7 = rootView.findViewById(R.id.clItem7);
        btnColo8 = rootView.findViewById(R.id.clItem8);

        grColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        setupRadioButtonGRColor (rootView, grColor1);
        setupRadioButtonGRColor (rootView, grColor2);

        setupRadioButtonGRIcon(rootView, grIcon1);
        setupRadioButtonGRIcon(rootView, grIcon2);
        setupRadioButtonGRIcon(rootView, grIcon3);
        setupRadioButtonGRIcon(rootView, grIcon4);
        setupRadioButtonGRIcon(rootView, grIcon5);
        setupRadioButtonGRIcon(rootView, grIcon6);
        setupRadioButtonGRIcon(rootView, grIcon7);


        return rootView;
    }
    private void uncheckInOtherRadioGr(RadioGroup parent ,RadioGroup grChild) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            RadioGroup radioGroup = (RadioGroup) parent.getChildAt(i);
            if (radioGroup.getId() != grChild.getId()) {
                radioGroup.setTag(true);
                radioGroup.clearCheck();
            }
        }
    }

    //setup nếu gr nào được chọn thì uncheck ở các gr còn lại
    private void setupRadioButtonGRColor(View view, RadioGroup grChecked) {
        grChecked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedColorBtn = view.findViewById(checkedId);

                if (group.getTag() != null && (boolean) group.getTag()) {
                    // Nếu là từ hàm clearCheck thì không thực hiện xử lý
                    group.setTag(false);
                    return;
                }

                //xử lý
                if (selectedColorBtn != null) {
                    Toast.makeText(requireContext(), "Selected: " + selectedColorBtn.getId(), Toast.LENGTH_SHORT).show();

                    //uncheck ở cacgr khác
                    RadioGroup parent = (RadioGroup) grChecked.getParent();
                    uncheckInOtherRadioGr(parent, grChecked);
                }
            }
        });
    }
    private void setupRadioButtonGRIcon(View view, RadioGroup grChecked){
            grChecked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    selectedIconBtn = view.findViewById(checkedId);

                    if (group.getTag() != null && (boolean) group.getTag()) {
                        // Nếu là từ hàm clearCheck thì không thực hiện xử lý
                        group.setTag(false);
                        return;
                    }

                    //xử lý
                    if (selectedIconBtn != null) {
                        Toast.makeText(requireContext(), "Selected: " + selectedIconBtn.getId(), Toast.LENGTH_SHORT).show();

                        //uncheck ở cacgr khác
                        RadioGroup parent = (RadioGroup) grChecked.getParent();
                        uncheckInOtherRadioGr(parent, grChecked);
                    }
                }
            });
        }

}
